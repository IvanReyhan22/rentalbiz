package com.bangkit.rentalbiz.ui.screen.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.local.entity.FavoriteItem
import com.bangkit.rentalbiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteItem>>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<List<FavoriteItem>>> get() = _uiState

    init {
        getAllFavorite()
    }

    private fun getAllFavorite() {
        setLoading()
        viewModelScope.launch {
            productRepository.getAllFavorite().collect { result ->
                if (result != null && result.isNotEmpty()) {
                    _uiState.value = UiState.Success(result)
                } else {
                    _uiState.value = UiState.Error(context.getString(R.string.favorite_empty))
                }
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            productRepository.deleteFavorite(productId).collect {
                getAllFavorite()
            }
        }
    }

    private fun setLoading() {
        _uiState.value = UiState.Loading
    }

}