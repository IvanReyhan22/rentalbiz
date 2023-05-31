package com.bangkit.rentalbiz.ui.components.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.card.StoreCard
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral600
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun ContactBottomSheetModal(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(AppTheme.dimens.spacing_24)) {
        Heading(title = stringResource(R.string.contact), type = HeadingType.H4)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        StoreCard(name = "ngalamstore", location = "Jl. Chocolate 12,  Malang",isChat = false, onChatClick = {})
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Row(modifier = Modifier) {
            SocialInfo(title = title, image = painterResource(id = R.drawable.whatsapp))
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_24))
            SocialInfo(title = title, image = painterResource(id = R.drawable.instagram))
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_24))
            SocialInfo(title = title, image = painterResource(id = R.drawable.telegram))
        }
    }
}

@Composable
fun SocialInfo(
    title: String,
    image: Painter,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Image(
            painter = image,
            contentDescription = title,
            modifier = Modifier.size(42.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
        Paragraph(
            title = title,
            type = ParagraphType.XSMALL,
            fontWeight = FontWeight.Medium,
            color = Neutral600
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun ModalContentPreview(
) {
    RentalBizTheme {
        ContactBottomSheetModal(title = "Whatsapp")
    }
}