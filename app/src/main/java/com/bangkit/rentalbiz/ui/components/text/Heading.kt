package com.bangkit.rentalbiz.ui.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.ui.theme.Shades90

@Composable
fun Heading(
    title: String,
    type: Enum<HeadingType>,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Shades90,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        textAlign = textAlign,
        fontSize = when (type) {
            HeadingType.D1 -> 44.sp
            HeadingType.D2 -> 40.sp
            HeadingType.H1 -> 36.sp
            HeadingType.H2 -> 32.sp
            HeadingType.H3 -> 28.sp
            HeadingType.H4 -> 24.sp
            HeadingType.H5 -> 20.sp
            HeadingType.H6 -> 18.sp
            else -> 18.sp
        },
        overflow = overflow,
        maxLines = maxLines,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HeadingPreview() {
    RentalBizTheme {
        Column {
            Heading("Hello World", HeadingType.H1)
            Heading("Hello World", HeadingType.H6, fontWeight = FontWeight.Black)
        }
    }
}