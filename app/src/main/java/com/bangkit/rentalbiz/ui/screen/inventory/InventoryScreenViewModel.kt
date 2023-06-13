package com.bangkit.rentalbiz.ui.screen.inventory

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Product>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getProducts() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            productRepository.getAllProducts().collect { response ->
                _uiState.value = when {
                    response != null && response.success == true -> {
                        val products: List<Product> = response.items ?: emptyList()
                        UiState.Success(filterByProviderId(products))
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

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
        getProductByName()
    }

    private fun getProductByName() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            productRepository.getProductByName(_query.value).collect { response ->
                _uiState.value = when {
                    response != null && response.success == true -> {
                        val products: List<Product> = response.items ?: emptyList()
                        UiState.Success(filterByProviderId(products))
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

    private fun filterByProviderId(products: List<Product>): List<Product> {
        val userPreference = UserPreference(context).getAuthKey()
        val userId = userPreference.userId

        return products.filter { it.idPenyedia == userId?.toInt() }
    }
}