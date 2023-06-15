package com.bangkit.rentalbiz.ui.components.modal

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun SetAddressBottomSheetModal(
    title: String,
    address: String,
    onTitleChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    submit: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.padding(AppTheme.dimens.spacing_24)) {
        Heading(title = stringResource(R.string.address_label), type = HeadingType.H4)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
        MyTextField(
            label = stringResource(R.string.address_name),
            value = title,
            onValueChange = { onTitleChange(it) }
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        MyTextField(
            label = stringResource(id = R.string.address_label),
            value = address,
            onValueChange = { onAddressChange(it) }
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        MyButton(
            title = stringResource(R.string.save),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.spacing_8),
            onClick = { submit(title, address) }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun SetAddressBottomSheetModalPreview() {
    RentalBizTheme() {
        SetAddressBottomSheetModal(
            title = "",
            address = "",
            onTitleChange = {},
            onAddressChange = {},
            submit = { _, _ -> }
        )
    }
}

