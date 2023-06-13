package com.bangkit.rentalbiz.ui.screen.camera

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bangkit.rentalbiz.ui.screen.manage.CameraView

@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxSize()
) {
    CameraView(
        onImageCaptured = { uri, fromGallery ->
            viewModel.saveImage(uri, onCompletePrompt = {
                navController.popBackStack()
            })
        },
        onError = { _ ->
            Log.e("ERR SCREEN", "ERROR")
        },
    )
}