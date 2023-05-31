package com.bangkit.rentalbiz.ui.screen.register

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.UserRepository
import com.bangkit.rentalbiz.data.remote.RegisterRequest
import com.bangkit.rentalbiz.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterForm(
    val name: String,
    val phoneNumber: String,
    var email: String,
    var password: String,
    var address: String,
    var city: String,
)

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val context: Context,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<String>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<String>> get() = _uiState

    private val _registerForm = mutableStateOf(RegisterForm("", "", "", "", "", ""))
    val registerForm: State<RegisterForm> get() = _registerForm

    fun updateForm(
        name: String = _registerForm.value.name,
        phoneNumber: String = _registerForm.value.phoneNumber,
        email: String = _registerForm.value.email,
        password: String = _registerForm.value.password,
        city: String = _registerForm.value.city,
        address: String = _registerForm.value.address,
    ) {
        _registerForm.value = RegisterForm(name, phoneNumber, email, password, address, city)
    }

    fun register() {
        setLoading()

        if (hasEmptyValue()) {
            _uiState.value = UiState.Error(context.getString(R.string.error_empty_field))
            return
        }

        val registerRequest = with(_registerForm.value) {
            RegisterRequest(
                name = name,
                phone = phoneNumber,
                address = address,
                city = city,
                email = email,
                password = password
            )
        }

        viewModelScope.launch {
            userRepository.register(registerRequest).collect { response ->
                _uiState.value = when {
                    response?.error != null -> { // with error message
                        UiState.Error(response.error)
                    }
                    response != null -> { // not null
                        UiState.Success(response.message.toString())
                    }
                    else -> { // other error message
                        UiState.Error(context.getString(R.string.connection_error))
                    }
                }
            }
        }
    }

    private fun setLoading() {
        _uiState.value = UiState.Loading
    }

    private fun hasEmptyValue(): Boolean {
        return with(_registerForm.value) {
            name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || city.isEmpty()
        }
    }

}