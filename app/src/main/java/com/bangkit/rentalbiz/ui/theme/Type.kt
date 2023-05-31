package com.bangkit.rentalbiz.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bangkit.rentalbiz.R

val Satoshi = FontFamily(
    Font(R.font.satoshi_regular),
    Font(R.font.satoshi_medium, FontWeight.Medium),
    Font(R.font.satoshi_bold, FontWeight.Bold),
    Font(R.font.satoshi_black, FontWeight.Black)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Satoshi,
)