package com.bangkit.rentalbiz.ui.screen.camera

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraScreenViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {
    fun saveImage(image: Uri, onCompletePrompt: () -> Unit) {
        viewModelScope.launch {
            val userPreference = UserPreference(context)
            userPreference.saveImage(image)
            onCompletePrompt()
        }
    }
}