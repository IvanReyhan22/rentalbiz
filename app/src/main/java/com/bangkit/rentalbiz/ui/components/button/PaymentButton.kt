package com.bangkit.rentalbiz.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral100
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.ui.theme.Shades0

@Composable
fun PaymentButton(
    leadingImage: Painter,
    onClick: () -> Unit,
    size: ButtonSize = ButtonSize.LARGE,
    modifier: Modifier = Modifier
) {

    val buttonSize = when (size) {
        ButtonSize.LARGE -> 48.dp
        ButtonSize.MEDIUM -> 40.dp
        ButtonSize.SMALL -> 30.dp
    }

    val contentPadding = when (size) {
        ButtonSize.SMALL -> PaddingValues(
            vertical = AppTheme.dimens.spacing_6,
            horizontal = AppTheme.dimens.spacing_16
        )
        else -> PaddingValues(
            vertical = AppTheme.dimens.spacing_12,
            horizontal = AppTheme.dimens.spacing_24
        )
    }

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(Shades0),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Neutral100),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = contentPadding,
        modifier = modifier.height(buttonSize)
    ) {
        Image(
            painter = leadingImage,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(48.dp)
        )
        Box(modifier = Modifier.weight(1F)) {}
        Image(
            painter = painterResource(id = R.drawable.ic_outline_chevron_right),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun PaymentButtonPreview() {
    RentalBizTheme {
        PaymentButton(leadingImage = painterResource(id = R.drawable.ovo), onClick = { /*TODO*/ })
    }
}
