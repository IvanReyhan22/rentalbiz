package com.bangkit.rentalbiz.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCredentials(
    var userId: String? = null,
    var name: String? = null,
    var email: String? = null,
    var token: String? = null,
    var isFirstTime:Boolean = true
) : Parcelable