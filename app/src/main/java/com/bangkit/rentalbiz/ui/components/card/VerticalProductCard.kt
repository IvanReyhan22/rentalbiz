package com.bangkit.rentalbiz.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*

@Composable
fun VerticalProductCard(
    imageUrl: String,
    title: String,
    price: String,
    rating: Double,
    location: String,
    onClick: ()->Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(AppTheme.dimens.radius_12),
                color = Shades0
            )
            .border(
                color = Neutral100,
                width = 2.dp,
                shape = RoundedCornerShape(AppTheme.dimens.radius_12)
            )
            .padding(AppTheme.dimens.spacing_8).clickable { onClick() }
    ) {
        Column(modifier = Modifier) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .clip(RoundedCornerShape(AppTheme.dimens.radius_8))
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
            Paragraph(title = title, type = ParagraphType.MEDIUM, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
            DetailInfo(location = location, rating = rating)
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
            Paragraph(
                title = stringResource(R.string.idr) + "$price / " + stringResource(R.string.day),
                type = ParagraphType.SMALL,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DetailInfo(location: String, rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Row(modifier = Modifier) {
            Image(
                painter = painterResource(id = R.drawable.ic_location_red),
                contentDescription = "location icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(AppTheme.dimens.spacing_14)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_4))
            Paragraph(title = location, type = ParagraphType.XSMALL, color = Neutral500)
        }
        Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
        Row(modifier = Modifier) {
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "rating icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(AppTheme.dimens.spacing_14)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_4))
            Paragraph(title = rating.toString(), type = ParagraphType.XSMALL, color = Warning400)
        }
    }
}

@Preview
@Composable
fun VerticalProductCardPreview() {
    RentalBizTheme {
        VerticalProductCard(
            imageUrl = "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
            title = "Cannon 5D Mark IV",
            price = "200000",
            location = "Bandung",
            rating = 4.5,
            onClick = {}
        )
    }
}
