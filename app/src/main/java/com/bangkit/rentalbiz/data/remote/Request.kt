package com.bangkit.rentalbiz.data.remote

data class LoginRequest(
    val email:String,
    val password:String
)

data class RegisterRequest(
    val email:String,
    val password:String,
    val name:String,
    val address:String,
    val city:String,
    val phone:String,
)