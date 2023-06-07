package com.bangkit.rentalbiz.ui.screen.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bangkit.rentalbiz.data.UserCredentials
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<UserCredentials>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<UserCredentials>> get() = _uiState

    init {
        getAuthKey()
    }

    private fun getAuthKey() {
        val userPreference = UserPreference(context)
        val key = userPreference.getAuthKey()
        if (key.token?.isNotEmpty() == true) {
            _uiState.value = UiState.Success(key)
        } else {
            _uiState.value = UiState.Error("Credential Not Found")
        }
    }

}