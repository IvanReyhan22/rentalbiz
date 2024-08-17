package com.bangkit.rentalbiz.ui.screen.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.naingaungluu.formconductor.annotations.EmailAddress
import me.naingaungluu.formconductor.annotations.Form
import me.naingaungluu.formconductor.annotations.MinLength
import javax.inject.Inject

@Form
data class RegisterForm (
    @EmailAddress
    val email: String,

    @MinLength(8)
    val password:String,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
): ViewModel() {

}