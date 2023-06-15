package com.bangkit.rentalbiz.ui.screen.login

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.UserCredentials
import com.bangkit.rentalbiz.data.UserRepository
import com.bangkit.rentalbiz.data.remote.LoginRequest
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginForm(
    var email: String,
    var password: String
)

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val context: Context,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<String>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<String>> get() = _uiState

    private val _loginForm = mutableStateOf(LoginForm("", ""))
    val loginForm: State<LoginForm> get() = _loginForm

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> get() = _isLoggedIn

    fun updateForm(
        email: String = _loginForm.value.email,
        password: String = _loginForm.value.password
    ) {
        _loginForm.value = LoginForm(email, password)
    }

    init {
        getAuthKey()
    }

    private fun getAuthKey() {
        val userPreference = UserPreference(context)
        val key = userPreference.getAuthKey()
        if (key.token?.isNotEmpty() == true) {
            _isLoggedIn.value = true
        }
    }


    fun login(onSignInComplete: () -> Unit) {
        setLoading()

        val email = _loginForm.value.email
        val password = _loginForm.value.password

        if (email.isEmpty() && password.isEmpty()) {
            _uiState.value = UiState.Error(context.getString(R.string.error_empty_field))
            return
        }

        viewModelScope.launch {
            val loginRequest = LoginRequest(
                email = _loginForm.value.email,
                password = _loginForm.value.password
            )
            userRepository.login(loginRequest).collect { response ->
                when {
                    response?.error?.contains(
                        "invalid",
                        ignoreCase = true
                    ) == true -> { // with error message invalid credentials
                        _uiState.value =
                            UiState.Error(context.getString(R.string.invalid_credentials))
                    }
                    response?.error != null -> { // with error message
                        _uiState.value = UiState.Error(response.error)
                    }
                    response != null -> { // not null
                        { /*SAVE AUTH TODO*/ }
                        val user = response.user
                        val userCredentials = UserCredentials(
                            userId = user?.id.toString(),
                            name = user?.name,
                            email = user?.email,
                            token = response.token,
                            isFirstTime = false,
                        )
                        saveAuthKey(userCredentials)
                        _uiState.value = UiState.Success(response.token.toString())
                        onSignInComplete()
                    }
                    else -> { // other error message
                        _uiState.value = UiState.Error(context.getString(R.string.connection_error))
                    }
                }
            }

        }
    }

    private fun saveAuthKey(userCredentials: UserCredentials) {
        val userPreference = UserPreference(context)
        userPreference.setAuthKey(userCredentials)
    }

    private fun setLoading() {
        _uiState.value = UiState.Loading
    }

}