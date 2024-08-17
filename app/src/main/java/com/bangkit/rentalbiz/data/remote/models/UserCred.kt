@file:Suppress("MemberVisibilityCanBePrivate")
package com.bangkit.rentalbiz.data.remote.models


import androidx.compose.runtime.Composable
import com.bangkit.rentalbiz.data.remote.models.UserCred.AuthenticationType


class UserCred {
    class LoginPayload {

    }
    enum class AuthenticationType {
        EMAIL,
        GOOGLE,
    }

    companion object {
        private const val prefName = "user_pref"
    }

    fun calculatePrice(){

    }

    /**
     * Handle Authentication based on [AuthenticationType]
     * @author: Antono <antono@gmail.com>
     *
     * Call authentication function based on [AuthenticationType]
     * [AuthenticationType] = EMAIL will call [emailAuth]
     * [AuthenticationType] = GOOGLE will call [googleAuth]
     *
     * @param payload, required data payload
     * @param isLoading, if true function will not be execute
     * @param type, decide what function to execute for authentication
     */
    fun authenticate(
        payload:LoginPayload,
        isLoading: Boolean = true,
        type:AuthenticationType = AuthenticationType.EMAIL,
    ){
        // ...
    }

    fun onBtnLoginClick(){
        authenticate(
            payload = LoginPayload(/*...*/),
            isLoading = false,
            type = AuthenticationType.EMAIL,
        );
    }

    fun emailAuth(){}
    fun googleAuth(){}



    fun main(){
        onBtnLoginClick();
    }
    /*
    Calculate price based on raw value and discount
    make sure price is not null
     */

}







