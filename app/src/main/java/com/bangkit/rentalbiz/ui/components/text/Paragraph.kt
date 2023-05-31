package com.bangkit.rentalbiz.ui.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.ui.theme.Shades90

@Composable
fun Paragraph(
    title: String,
    textAlign: TextAlign = TextAlign.Start,
    type: Enum<ParagraphType> = ParagraphType.MEDIUM,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Shades90,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        fontSize = when (type) {
            ParagraphType.LARGE -> 18.sp
            ParagraphType.MEDIUM -> 16.sp
            ParagraphType.SMALL -> 14.sp
            ParagraphType.XSMALL -> 12.sp
            else -> 18.sp
        },
        textAlign = textAlign,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ParagraphPreview() {
    RentalBizTheme {
        Column {
            Paragraph("Hello World")
        }
    }
}