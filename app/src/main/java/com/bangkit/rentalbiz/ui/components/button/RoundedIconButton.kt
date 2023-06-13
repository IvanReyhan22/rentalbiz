package com.bangkit.rentalbiz.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun RoundedIconButton(
    icon: Painter,
    size: ButtonSize = ButtonSize.MEDIUM,
    type: ButtonType = ButtonType.PRIMARY,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonSize = when (size) {
        ButtonSize.LARGE -> 48.dp
        ButtonSize.MEDIUM -> 40.dp
        ButtonSize.SMALL -> 30.dp
    }
    val buttonColor =
        ButtonDefaults.buttonColors(if (type == ButtonType.SECONDARY) Shades0 else Shades90)
    val buttonBorder = if (type == ButtonType.SECONDARY) BorderStroke(2.dp, Neutral100) else null
    val colorFilter = ColorFilter.tint(if (type == ButtonType.SECONDARY) Shades90 else Shades0)
    val iconSize = when (size) {
        ButtonSize.LARGE -> 28.dp
        ButtonSize.MEDIUM -> 18.dp
        ButtonSize.SMALL -> 14.dp
    }

    Button(
        onClick = onClick,
        colors = buttonColor,
        shape = RoundedCornerShape(AppTheme.dimens.radius_12),
        border = buttonBorder,
        contentPadding = PaddingValues(AppTheme.dimens.spacing_8),
        modifier = modifier.size(buttonSize)
    ) {
        Image(
            painter = icon,
            contentDescription = stringResource(R.string.ic),
            contentScale = ContentScale.Fit,
            colorFilter = colorFilter,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview
@Composable
fun RoundedIconButtonPreview() {
    RentalBizTheme {
        Column {
            RoundedIconButton(
                icon = painterResource(id = R.drawable.ic_outline_heart),
                onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            RoundedIconButton(
                icon = painterResource(id = R.drawable.ic_outline_heart),
                type = ButtonType.SECONDARY,
                onClick = {}
            )
        }
    }
}