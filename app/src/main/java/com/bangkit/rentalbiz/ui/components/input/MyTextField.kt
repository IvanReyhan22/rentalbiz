package com.bangkit.rentalbiz.ui.components.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*

@Composable
fun MyTextField(
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    label: String? = null,
    hint: String? = null,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .heightIn(min = 48.dp, max = 104.dp)
    ) {
        if (!label.isNullOrEmpty()) {
            Paragraph(
                title = label,
                type = ParagraphType.MEDIUM,
                fontWeight = FontWeight.Medium,
                color = Shades90,
                modifier = modifier.padding(bottom = AppTheme.dimens.spacing_8)
            )
        }
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            shape = RoundedCornerShape(AppTheme.dimens.radius_12),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Neutral100
            ),
            placeholder = {
                Paragraph(title = placeholder, type = ParagraphType.MEDIUM, color = Neutral500)
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            maxLines = maxLines,
            modifier = Modifier
                .widthIn(min = 1.dp)
                .fillMaxWidth()
                .heightIn(min = AppTheme.dimens.spacing_24)
        )
        if (!hint.isNullOrEmpty()) {
            Paragraph(
                title = hint,
                type = ParagraphType.MEDIUM,
                fontWeight = FontWeight.Medium,
                color = Neutral400,
                modifier = modifier.padding(top = AppTheme.dimens.spacing_4)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyTextFieldPreview(
) {
    RentalBizTheme {
        Column {
            MyTextField(value = "", onValueChange = {})
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
            MyTextField(value = "", onValueChange = {}, label = "Email")
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
            MyTextField(
                value = "",
                onValueChange = {},
                label = "Password",
                hint = "*Kombinasi antara huruf besar dan angka"
            )
        }
    }
}