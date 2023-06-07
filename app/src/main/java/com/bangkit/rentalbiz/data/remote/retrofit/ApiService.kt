package com.bangkit.rentalbiz.data.remote.retrofit

import com.bangkit.rentalbiz.data.remote.LoginRequest
import com.bangkit.rentalbiz.data.remote.RegisterRequest
import com.bangkit.rentalbiz.data.remote.response.LoginResponse
import com.bangkit.rentalbiz.data.remote.response.ProductResponse
import com.bangkit.rentalbiz.data.remote.response.ProductsResponse
import com.bangkit.rentalbiz.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /* Authentication */
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    /* Product */
    @GET("items")
    fun getProducts(
        @Query("name") name: String? = null,
        @Query("category") category: String? = null,
        @Query("minPrice") maxPrice: String? = null,
        @Query("maxPrice") minPrice: String? = null,
        @Query("city") city: String? = null,
        @Query("tersedia") available: Int = 1,
    ): Call<ProductsResponse>

    @GET("items/id/{id}")
    fun getProduct(
        @Path("id") id: String,
    ): Call<ProductResponse>
}