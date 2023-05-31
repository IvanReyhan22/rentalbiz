package com.bangkit.rentalbiz.ui.components.card

import androidx.compose.foundation.Image
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
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral500
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun StoreCard(
    name: String,
    location: String,
    isChat: Boolean = true,
    onChatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        RoundedIconButton(
            icon = painterResource(id = R.drawable.ic_outline_store),
            size = ButtonSize.LARGE,
            onClick = { /*TODO*/ },
        )
        Column(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.spacing_16)
                .fillMaxWidth()
                .weight(1F)
        ) {
            Paragraph(title = name, type = ParagraphType.MEDIUM, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
            StoreLocation(location = location)
        }
        if (isChat) {
            CircleIconButton(
                icon = painterResource(id = R.drawable.ic_outline_message),
                type = ButtonType.SECONDARY,
                size = ButtonSize.LARGE,
                onClick = { onChatClick() },
            )
        }
    }
}

@Composable
fun StoreLocation(location: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_location_red),
            contentDescription = "Rating Icon",
            modifier = Modifier.size(AppTheme.dimens.spacing_16)
        )
        Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_8))
        Paragraph(
            title = location,
            type = ParagraphType.XSMALL,
            color = Neutral500
        )
    }
}


@Preview(showBackground = true)
@Composable
fun StoreCardPreview() {
    RentalBizTheme {
        StoreCard(
            name = "ngalamstore",
            location = "Jl. Chocolate 12,  Malang",
            onChatClick = {}
        )
    }
}