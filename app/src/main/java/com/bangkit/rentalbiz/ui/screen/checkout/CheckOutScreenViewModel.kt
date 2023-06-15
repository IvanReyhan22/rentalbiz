package com.bangkit.rentalbiz.ui.screen.checkout

import android.content.Context
import android.util.Log
import android.util.Range
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.data.remote.TransactionRequest
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.utils.AddressData
import com.bangkit.rentalbiz.utils.Helper.dateToServerFormat
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CheckOutScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<CartItem>>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<List<CartItem>>> get() = _uiState

    private val _totalItemPrice = mutableStateOf(0)
    val totalItemPrice: State<Int> get() = _totalItemPrice

    private val _deliveryFee = mutableStateOf(20000)
    val deliveryFee: State<Int> get() = _deliveryFee

    private val _dateRange = mutableStateOf(Range(LocalDate.now(), LocalDate.now().plusDays(1)))
    val dateRange: State<Range<LocalDate>> get() = _dateRange

    private val _transactionProgress = mutableStateOf(false)
    val transactionProgress: State<Boolean> get() = _transactionProgress

    private val _address = mutableStateOf(AddressData("--", "---"))
    val address: State<AddressData> get() = _address

    init {
        getAllCartItem()
        getAddress()
    }

    private fun getAllCartItem() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            productRepository.getAllCartItems().collect { result ->
                _uiState.value = if (result != null && result.isNotEmpty()) {
                    _totalItemPrice.value = result.sumOf { it.harga.toInt() * it.jumlahSewa }
                    UiState.Success(result)
                } else {
                    UiState.Error(context.getString(R.string.cart_empty))
                }
            }
        }
    }

    fun updateDate(date: Range<LocalDate>) {
        _dateRange.value = date
    }

    fun transactionProcess(onSuccess: () -> Unit, onFailed: () -> Unit) {
        _transactionProgress.value = true
        viewModelScope.launch {
            if (_uiState.value is UiState.Success) {
                val items = (_uiState.value as UiState.Success<List<CartItem>>).data
                val startDate = _dateRange.value.lower
                val endDate = _dateRange.value.upper

                items.forEach { item ->
                    val transactionRequest =
                        TransactionRequest(
                            id_barang = item.id.toInt(),
                            tanggal_kembali = dateToServerFormat(endDate.toString()),
                            tanggal_pinjam = dateToServerFormat(startDate.toString()),
                            jumlah = item.jumlahSewa
                        )
                    productRepository.transaction(transactionRequest).collect { result ->
                        if (result != null) {
                            if (result.status != null) {
                                _uiState.value = UiState.Idle
                                onSuccess()
                            } else {
                                _uiState.value =
                                    UiState.Error(context.getString(R.string.payment_failed))
                                onFailed()
                            }
                        } else {
                            _uiState.value =
                                UiState.Error(context.getString(R.string.payment_failed))
                            onFailed()
                        }
                    }
                }
            }
        }
    }

    fun saveAddress(title: String, address: String) {
        viewModelScope.launch {
            val userPreference = UserPreference(context)
            userPreference.saveAddress(title, address)
            getAddress()
        }
    }

    fun updateAddress(
        title: String = _address.value.title,
        address: String = _address.value.address
    ) {
        _address.value = AddressData(title, address)
    }

    fun getAddress() {
        viewModelScope.launch {
            val userPreference = UserPreference(context)
            val data = userPreference.getAddress()
            _address.value = AddressData(data.title, data.address)
        }
    }
}