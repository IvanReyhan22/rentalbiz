package com.bangkit.rentalbiz.ui.components.input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
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
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    label: String? = null,
    hint: String? = null,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    isTextArea: Boolean = false,
    singleLine: Boolean = true,
    isPassword: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column(
        modifier = modifier
            .heightIn(min = 48.dp, max = 104.dp),

    ) {
//        if (!label.isNullOrEmpty()) {
//            Paragraph(
//                title = label,
//                type = ParagraphType.MEDIUM,
//                fontWeight = FontWeight.Medium,
//                color = Shades90,
//                modifier = modifier
//                    .fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
//        }
        OutlinedTextField(
            label = {
                Text(text = if (label.isNullOrEmpty()) "" else label)
            },
            value = "",
            onValueChange = {onValueChange(it) },
            modifier= Modifier
                .widthIn(min = 1.dp)
                .fillMaxWidth()
                .heightIn(AppTheme.dimens.spacing_24),
        )
//        if (!hint.isNullOrEmpty()) {
//            Paragraph(
//                title = hint,
//                type = ParagraphType.MEDIUM,
//                fontWeight = FontWeight.Medium,
//                color = Neutral400,
//                modifier = modifier.padding(top = AppTheme.dimens.spacing_4)
//            )
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextPreview(
) {
    RentalBizTheme {
        Column(
            modifier = Modifier.padding(all = 32.dp)
        ) {
            CustomTextField(value = "", onValueChange = {})
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
            CustomTextField(value = "", onValueChange = {}, label = "Email")
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
            CustomTextField(
                value = "",
                onValueChange = {},
                label = "Password",
                hint = "*Kombinasi antara huruf besar dan angka"
            )
        }
    }
}