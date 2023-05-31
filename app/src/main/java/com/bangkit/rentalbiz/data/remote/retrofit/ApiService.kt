package com.bangkit.rentalbiz.data.remote.retrofit

import com.bangkit.rentalbiz.data.remote.LoginRequest
import com.bangkit.rentalbiz.data.remote.RegisterRequest
import com.bangkit.rentalbiz.data.remote.response.LoginResponse
import com.bangkit.rentalbiz.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    /* Authentication */
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}