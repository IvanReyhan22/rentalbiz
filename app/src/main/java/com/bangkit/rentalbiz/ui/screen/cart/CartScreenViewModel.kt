package com.bangkit.rentalbiz.ui.screen.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<CartItem>>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<List<CartItem>>> get() = _uiState

    init {
        getAllCartItem()
    }

    private fun getAllCartItem() {
        setLoading()
        viewModelScope.launch {
            productRepository.getAllCartItems().collect { result ->
                _uiState.value = if (result != null && result.isNotEmpty()) {
                    UiState.Success(result)
                } else {
                    UiState.Error(context.getString(R.string.cart_empty))
                }
            }
        }
    }

    fun deleteCartItem(id: String) {
        setLoading()
        viewModelScope.launch {
            productRepository.deleteCartItem(id)
            getAllCartItem()
        }
    }

    private fun setLoading() {
        _uiState.value = UiState.Loading
    }

}