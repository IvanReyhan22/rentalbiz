package com.bangkit.rentalbiz.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.remote.response.TransactionsItem
import com.bangkit.rentalbiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<TransactionsItem>>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<List<TransactionsItem>>> get() = _uiState

    fun getHistory() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            productRepository.transactionList().collect { result ->
                _uiState.value = if (result?.success == true) {
                    UiState.Success(result.transactions ?: emptyList())
                } else {
                    UiState.Error(result?.message.toString())
                }
            }
        }
    }
}