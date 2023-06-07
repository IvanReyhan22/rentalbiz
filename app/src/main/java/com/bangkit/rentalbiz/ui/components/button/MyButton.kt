package com.bangkit.rentalbiz.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*

enum class ButtonState {
    ACTIVE,
    DISABLE,
    LOADING
}

@Composable
fun MyButton(
    title: String,
    state: ButtonState = ButtonState.ACTIVE,
    leadingImage: Painter? = null,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    onClick: () -> Unit,
    isStartAlignment: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val buttonSize = when (size) {
        ButtonSize.LARGE -> 48.dp
        ButtonSize.MEDIUM -> 40.dp
        ButtonSize.SMALL -> 30.dp
    }
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


    val textColor = if (type == ButtonType.SECONDARY) Shades90 else Shades0
    val textSize = when (size) {
        ButtonSize.LARGE -> ParagraphType.LARGE
        ButtonSize.MEDIUM -> ParagraphType.SMALL
        ButtonSize.SMALL -> ParagraphType.SMALL
    }

    val iconSize = when (size) {
        ButtonSize.LARGE -> 24.dp
        ButtonSize.MEDIUM -> 18.dp
        ButtonSize.SMALL -> 16.dp
    }

    Button(
        onClick = {
            if (state == ButtonState.ACTIVE) onClick()
        },
        colors = buttonColor,
        shape = RoundedCornerShape(12.dp),
        border = buttonBorder,
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = contentPadding,
        modifier = modifier.height(buttonSize)
    ) {
        if (state != ButtonState.LOADING) {
            if (leadingImage != null) {
                Image(
                    painter = leadingImage,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )
                Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_8))
            }
            Paragraph(
                title = title,
                color = textColor,
                type = textSize,
                fontWeight = FontWeight.Medium
            )
            if (isStartAlignment) {
                Spacer(modifier = Modifier.weight(1F))
            }
        } else {
            CircularProgressIndicator(
                color = if (type == ButtonType.SECONDARY) Primary400 else Shades0,
                strokeWidth = AppTheme.dimens.spacing_2,
                modifier = Modifier.size(AppTheme.dimens.spacing_24)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyButtonPreview() {
    RentalBizTheme {
        Column {
            MyButton("Click Me", onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            MyButton("Click Me", type = ButtonType.SECONDARY, onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            MyButton("Click Me", type = ButtonType.SUCCESS, onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            MyButton("Click Me", type = ButtonType.ERROR, onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            MyButton("Click Me", type = ButtonType.ACCENT, onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            MyButton(
                "Click Me",
                type = ButtonType.PRIMARY,
                state = ButtonState.LOADING,
                onClick = {})
        }
    }
}