package com.bangkit.rentalbiz.ui.screen.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.FilterData
import com.bangkit.rentalbiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Product>>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<List<Product>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    init {
        getAllProducts()
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
        getProductByName()
    }

    private fun getAllProducts() {
        setLoading()

        viewModelScope.launch {
            productRepository.getAllProducts().collect { response ->
                _uiState.value = when {
                    response != null && response.success == true -> {
                        val products: List<Product> = response.items ?: emptyList()
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

    private fun getProductByName() {
        setLoading()

        viewModelScope.launch {
            productRepository.getProductByName(_query.value).collect { response ->
                _uiState.value = when {
                    response != null && response.success == true -> {
                        val products: List<Product> = response.items ?: emptyList()
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

    fun filterProduct(filterData: FilterData) {
        setLoading()
        viewModelScope.launch {
            productRepository.filterProduct(
                name = _query.value,
                category = filterData.category,
                minPrice = filterData.minPrice,
                maxPrice = filterData.maxPrice,
                city = filterData.location
            ).collect { response ->
                _uiState.value = when {
                    response != null && response.success == true -> {
                        val products: List<Product> = response.items ?: emptyList()
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

    private fun setLoading() {
        _uiState.value = UiState.Loading
    }
}