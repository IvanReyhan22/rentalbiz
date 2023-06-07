package com.bangkit.rentalbiz.ui.screen.detail

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.data.local.entity.FavoriteItem
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.dummy.DummyProductData
import com.bangkit.rentalbiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Product>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<Product>> get() = _uiState

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> get() = _isFavorite

    fun getProduct(productId: String) {
        setLoading()
        if (productId.isNotEmpty()) {
            viewModelScope.launch {
                productRepository.getProduct(productId = productId).collect { response ->
                    _uiState.value = when {
                        response?.item != null -> {
                            val product = response.item
                            checkIsFavorite(productId = product.id.toString())
                            UiState.Success(product)
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
        } else {
            _uiState.value = UiState.Error(context.getString(R.string.product_not_found))
        }
    }

    fun checkIsFavorite(productId: String) {
        viewModelScope.launch {
            productRepository.getFavoriteById(productId).collect { result ->
                if (result != null) {
                    _isFavorite.value = result.isNotEmpty()
                } else {
                    _isFavorite.value = false
                }
            }
        }
    }

    private fun saveFavorite(product: Product): Boolean {
        var isSuccess = false
        viewModelScope.launch {
            val favoriteItem = with(product) {
                FavoriteItem(
                    id.toString(),
                    persyaratan.toString(),
                    city.toString(),
                    tersedia.toString(),
                    kategori.toString(),
                    idPenyedia.toString(),
                    nama.toString(),
                    harga.toString(),
                    totalSewa.toString(),
                    imageUrl.toString()
                )
            }
            productRepository.saveFavorite(favoriteItem).collect { result ->
                isSuccess = result
                _isFavorite.value = true
            }
        }
        return isSuccess
    }

    private fun deleteProduct(productId: String): Boolean {
        var isSuccess = false
        viewModelScope.launch {
            productRepository.deleteFavorite(productId).collect { result ->
                isSuccess = result
                _isFavorite.value = false
            }
        }
        return isSuccess
    }

    fun toggleFavorite(product: Product) {
        if (_isFavorite.value) {
            deleteProduct(productId = product.id.toString())
        } else {
            saveFavorite(product = product)
        }
    }

    fun addToCart(product: Product, onSuccess: () -> Unit, onFailed: () -> Unit) {
        viewModelScope.launch {
            val data = with(product) {
                CartItem(
                    id = id.toString(),
                    city = city.toString(),
                    tersedia = tersedia.toString(),
                    nama = nama.toString(),
                    harga = harga.toString(),
                    imageUrl = imageUrl.toString(),
                    jumlahSewa = 1,
                )
            }
            productRepository.addToCart(data).collect { result ->
                if (result) onSuccess() else onFailed()
            }
        }
    }

    private fun setLoading() {
        _uiState.value = UiState.Loading
    }
}