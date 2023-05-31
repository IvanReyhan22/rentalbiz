package com.bangkit.rentalbiz.ui.components.card

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral500
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun DateCheckCard(
    date: String,
    day: Int,
    status: String,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        RoundedIconButton(
            icon = painterResource(id = R.drawable.ic_outline_calendar),
            size = ButtonSize.LARGE,
            onClick = { /*TODO*/ },
        )
        Column(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.spacing_16)
                .fillMaxWidth()
                .weight(1F)
        ) {
            Paragraph(title = date, type = ParagraphType.MEDIUM, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
            Paragraph(
                title = "$day hari",
                type = ParagraphType.XSMALL,
                color = Neutral500
            )
        }
        MyButton(
            title = status,
            type = ButtonType.SUCCESS,
            size = ButtonSize.SMALL,
            onClick = { /*TODO*/ })
    }
}

@Preview(showBackground = true)
@Composable
fun DateCheckCardPreview() {
    RentalBizTheme {
        DateCheckCard(
            date = "20 May 2023",
            day = 1,
            status = "available"
        )
    }
}