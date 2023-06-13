package com.bangkit.rentalbiz.ui.screen.recommendation

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationResultScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Product>>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<List<Product>>> get() = _uiState

    private val _totalPrice = mutableStateOf(0)
    val totalPrice: State<Int> get() = _totalPrice

    fun getRecommendation(category: String, fund: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            productRepository.filterProduct(
                name = "",
                category = category,
                minPrice = 0.toString(),
                maxPrice = fund.toString(),
                city = ""
            ).collect { response ->
                _uiState.value = when {
                    response != null && response.success == true -> {
                        val products: List<Product> = response.items ?: emptyList()
                        _totalPrice.value = products.sumOf { it.harga ?: 0 }
                        UiState.Success(products)
                    }
                    response?.success == false -> {
                        UiState.Error(response.message ?: "Error Occurred")
                    }
                    else -> {
                        UiState.Error(context.getString(R.string.product_not_found))
                    }
                }
            }
        }
    }

    fun addToCart(products: List<Product>, onSuccess: () -> Unit, onFailed: () -> Unit) {
        viewModelScope.launch {
            val carts: List<CartItem> = products.map { product ->
                CartItem(
                    id = product.id.toString(),
                    city = product.city.toString(),
                    tersedia = product.tersedia.toString(),
                    nama = product.nama.toString(),
                    harga = product.harga.toString(),
                    imageUrl = product.imageUrl.toString(),
                    jumlahSewa = 1,
                )
            }
            productRepository.addCarts(carts).collect { result ->
                if (result) onSuccess() else onFailed()
            }
        }
    }
}