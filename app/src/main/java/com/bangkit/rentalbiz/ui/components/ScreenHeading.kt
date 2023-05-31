package com.bangkit.rentalbiz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral500

@Composable
fun ScreenHeading(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Heading(title = title, type = HeadingType.H4)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
        Paragraph(
            title = subTitle,
            type = ParagraphType.MEDIUM,
            fontWeight = FontWeight.Medium,
            color = Neutral500
        )
    }
}