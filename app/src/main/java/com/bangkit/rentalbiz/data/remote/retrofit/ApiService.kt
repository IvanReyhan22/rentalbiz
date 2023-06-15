package com.bangkit.rentalbiz.data.remote.retrofit

import com.bangkit.rentalbiz.data.remote.LoginRequest
import com.bangkit.rentalbiz.data.remote.ProductRequest
import com.bangkit.rentalbiz.data.remote.RegisterRequest
import com.bangkit.rentalbiz.data.remote.TransactionRequest
import com.bangkit.rentalbiz.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Query("maxPrice") maxPrice: String? = null,
        @Query("minPrice") minPrice: String? = null,
        @Query("city") city: String? = null,
        @Query("tersedia") available: Int = 1,
    ): Call<ProductsResponse>

    @GET("items/id/{id}")
    fun getProduct(
        @Path("id") id: String,
    ): Call<ProductResponse>

    @Multipart
    @POST("items")
    fun postProduct(
        @Part image: MultipartBody.Part,
        @Part("nama") name: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("kategori") kategori: RequestBody,
        @Part("persyaratan") persyaratan: RequestBody,
        @Part("stok") stok: RequestBody,
    ): Call<ProductResponse>

    @PUT("items/{id}")
    fun updateProduct(@Path("id")id:String, @Body request: ProductRequest):Call<ProductResponse>

    @DELETE("items/{id}")
    fun deleteProduct(
        @Path("id") id: String,
    ): Call<ProductResponse>

    /* Transaction */
    @POST("transaction")
    fun transaction(@Body transactionRequest: TransactionRequest): Call<TransactionProcessResponse>

    @GET("transaction")
    fun getTransactionList(): Call<TransactionListResponse>

    @GET("transaction/{id}")
    fun getTransactionById(
        @Path("id") id: String,
    ): Call<TransactionByIdResponse>

}