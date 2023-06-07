package com.bangkit.rentalbiz.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.theme.*

@Composable
fun CircleIconButton(
    icon: Painter,
    size: ButtonSize = ButtonSize.LARGE,
    type: ButtonType = ButtonType.PRIMARY,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonSize = when (size) {
        ButtonSize.LARGE -> 48.dp
        ButtonSize.MEDIUM -> 36.dp
        ButtonSize.SMALL -> 20.dp
    }
//    val buttonColor =
//        ButtonDefaults.buttonColors(if (type == ButtonType.SECONDARY) Shades0 else Shades90)
    val buttonColor =
        ButtonDefaults.buttonColors(
            when (type) {
                ButtonType.PRIMARY -> Shades90
                ButtonType.SECONDARY -> Shades0
                ButtonType.SUCCESS -> Success400
                ButtonType.ERROR -> Error400
                ButtonType.ACCENT -> Primary400
            }
        )
    val buttonBorder = if (type == ButtonType.SECONDARY) BorderStroke(2.dp, Neutral100) else null
    val iconSize = when (size) {
        ButtonSize.LARGE -> 24.dp
        ButtonSize.MEDIUM -> 18.dp
        ButtonSize.SMALL -> 12.dp
    }

    Button(
        onClick = onClick,
        colors = buttonColor,
        shape = CircleShape,
        border = buttonBorder,
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.size(buttonSize)
    ) {
        Image(
            painter = icon,
            contentDescription = stringResource(R.string.ic),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                when (type) {
                    ButtonType.SECONDARY -> Shades90
                    else -> Shades0
                }
            ),
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview
@Composable
fun CircleIconButtonPreview() {
    RentalBizTheme {
        Column {
            CircleIconButton(icon = painterResource(id = R.drawable.ic_outline_heart), onClick = {})
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            CircleIconButton(
                icon = painterResource(id = R.drawable.ic_outline_heart),
                type = ButtonType.SECONDARY,
                onClick = {})
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            CircleIconButton(
                icon = painterResource(id = R.drawable.ic_outline_heart),
                type = ButtonType.ERROR,
                onClick = {})
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            CircleIconButton(
                icon = painterResource(id = R.drawable.ic_outline_heart),
                type = ButtonType.SUCCESS,
                onClick = {})
        }
    }
}