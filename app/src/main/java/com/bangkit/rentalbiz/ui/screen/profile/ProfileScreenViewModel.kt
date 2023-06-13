package com.bangkit.rentalbiz.ui.screen.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.UserCredentials
import com.bangkit.rentalbiz.data.UserRepository
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val context: Context,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<UserCredentials>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<UserCredentials>> get() = _uiState

    init {
        getUserData()
    }

    private fun getUserData() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val userPreference = UserPreference(context)
            val user = userPreference.getAuthKey()
            if (user.userId != null) {
                _uiState.value = UiState.Success(user)
            } else {
                _uiState.value = UiState.Error(context.getString(R.string.user_not_found))
            }
        }
    }

    fun singOut(onSignOutComplete: () -> Unit) {
        val userPreference = UserPreference(context)
        userPreference.deleteAuthKey()
        onSignOutComplete()
    }
}