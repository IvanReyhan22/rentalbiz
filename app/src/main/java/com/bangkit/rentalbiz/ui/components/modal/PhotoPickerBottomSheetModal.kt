package com.bangkit.rentalbiz.ui.components.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun PhotoPickerBottomSheetModal(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
) {
    Column(modifier = modifier.padding(AppTheme.dimens.spacing_24)) {
        Heading(title = stringResource(R.string.choose_picture), type = HeadingType.H4)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_rounded_fill_camera),
                contentDescription = "Camera",
                modifier = Modifier
                    .size(AppTheme.dimens.spacing_56)
                    .clickable {
                        onCameraClick()
                    },
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_24))
            Image(
                painter = painterResource(id = R.drawable.ic_rounded_fill_gallery),
                contentDescription = "Gallery",
                modifier = Modifier
                    .size(AppTheme.dimens.spacing_56)
                    .clickable {
                        onGalleryClick()
                    },
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun PhotoPickerPreview() {
    RentalBizTheme {
        PhotoPickerBottomSheetModal(onGalleryClick = { /*TODO*/ }, onCameraClick = {})
    }
}