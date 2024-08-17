package com.bangkit.rentalbiz.data

import com.bangkit.rentalbiz.data.remote.LoginRequest
import com.bangkit.rentalbiz.data.remote.RegisterRequest
import com.bangkit.rentalbiz.data.remote.response.LoginResponse
import com.bangkit.rentalbiz.data.remote.response.RegisterResponse
import com.bangkit.rentalbiz.data.remote.retrofit.ApiService
import com.bangkit.rentalbiz.utils.Helper.parseErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) {


    suspend fun login(loginRequest: LoginRequest): Flow<LoginResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(loginRequest).execute()
                if (response.isSuccessful) {
                    flowOf(response.body())
                } else {
                    val errorResponse = response.errorBody()!!.string()
                    val parseError = parseErrorMessage(errorResponse)
                    val errorLoginResponse = LoginResponse(token = null, error = parseError)
                    flowOf(errorLoginResponse)
                }
            } catch (e: Exception) {
                flowOf(null)
            }
        }

    suspend fun register(registerRequest: RegisterRequest): Flow<RegisterResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(registerRequest).execute()
                if (response.isSuccessful) {
                    flowOf(response.body())
                } else {
                    val errorResponse = response.errorBody()!!.string()
                    val parseError = parseErrorMessage(errorResponse)
                    val errorRegisterResponse = RegisterResponse(message = null, error = parseError)
                    flowOf(errorRegisterResponse)
                }
            } catch (e: Exception) {
                flowOf(RegisterResponse(message = null, error = e.message.toString()))
            }
        }
}