package com.bangkit.rentalbiz.ui

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RentalBizAppViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {
    private val _userData = mutableStateOf(false)
    val userData: State<Boolean> get() = _userData

    init {
        checkFirstTime()
    }

    private fun checkFirstTime() {
        val userPreference = UserPreference(context)
        _userData.value = userPreference.getIsFirstTime()

    }
}