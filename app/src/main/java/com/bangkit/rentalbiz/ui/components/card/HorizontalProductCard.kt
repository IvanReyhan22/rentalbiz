package com.bangkit.rentalbiz.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*

enum class ProductCardType {
    NORMAL,
    HISTORY,
    FAVORITE,
    COUNTER,
}

@Composable
fun HorizontalProductCard(
    onClick: () -> Unit,
    imageUrl: String,
    title: String,
    location: String,
    rating: Double,
    price: String,
    status: String,
    isFavorite:Boolean = false,
    type: Enum<ProductCardType> = ProductCardType.NORMAL,
    modifier: Modifier = Modifier
) {
    var itemCount by remember { mutableStateOf(1) }
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
            .padding(AppTheme.dimens.spacing_8)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(AppTheme.dimens.radius_8))
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_8))
            ProducDetail(
                title = title,
                location = location,
                price = price,
                rating = rating,
                type = type,
                isFavorite = isFavorite,
                modifier = Modifier.weight(1F)
            )

            if (type == ProductCardType.HISTORY) {
                Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_8))
                MyButton(
                    title = status,
                    size = ButtonSize.SMALL,
                    type = ButtonType.SUCCESS,
                    onClick = { /*TODO*/ })
            }

            if (type == ProductCardType.COUNTER) {
                Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_8))
                Counter(
                    itemCount = itemCount,
                    onClickPlus = { itemCount++ },
                    onClickMinus = { itemCount-- })
            }
        }
    }
}

@Composable
fun ProducDetail(
    title: String,
    location: String,
    price: String,
    rating: Double,
    isFavorite:Boolean = false,
    type: Enum<ProductCardType>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Paragraph(title = title, type = ParagraphType.MEDIUM, fontWeight = FontWeight.Bold)
            if (type == ProductCardType.FAVORITE) {
                CircleIconButton(
                    icon = painterResource(id = R.drawable.ic_outline_heart),
                    size = ButtonSize.MEDIUM,
                    type = if(isFavorite) ButtonType.ERROR else ButtonType.SECONDARY,
                    onClick = { /*TODO*/ },
                )
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        LocationAndRating(location = location, rating = rating)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier) {
            Paragraph(
                title = stringResource(R.string.idr) + "$price / " + stringResource(R.string.day),
                type = ParagraphType.SMALL,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1F)
            )
            if (type != ProductCardType.HISTORY && type != ProductCardType.COUNTER) {
                Paragraph(
                    title = stringResource(R.string.rented_20),
                    type = ParagraphType.SMALL,
                    fontWeight = FontWeight.Medium,
                    color = Neutral500
                )
            }
        }
    }
}

@Composable
private fun LocationAndRating(location: String, rating: Double, modifier: Modifier = Modifier) {
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

@Composable
private fun Counter(
    itemCount: Int,
    onClickPlus: () -> Unit,
    onClickMinus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        RoundedIconButton(
            icon = painterResource(id = R.drawable.ic_plus),
            type = ButtonType.SECONDARY,
            size = ButtonSize.SMALL,
            onClick = { /*TODO*/ })
        Paragraph(
            title = itemCount.toString(),
            type = ParagraphType.MEDIUM,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = AppTheme.dimens.spacing_8)
        )
        RoundedIconButton(
            icon = painterResource(id = R.drawable.ic_minus),
            type = ButtonType.SECONDARY,
            size = ButtonSize.SMALL,
            onClick = { /*TODO*/ })
    }
}

@Preview
@Composable
fun HorizontalProductCardPreview() {
    RentalBizTheme {
        Column() {
            HorizontalProductCard(
                imageUrl = "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                title = "Cannon 5D Mark IV",
                price = "200000",
                location = "Bandung",
                rating = 4.5,
                type = ProductCardType.NORMAL,
                status = "Selesai",
                onClick = {},
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            HorizontalProductCard(
                imageUrl =
                "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                title = "Cannon 5D Mark IV",
                price = "200000",
                location = "Bandung",
                rating = 4.5,
                type = ProductCardType.FAVORITE,
                status = "Selesai",
                onClick = {},
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            HorizontalProductCard(
                imageUrl = "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                title = "Cannon 5D Mark IV",
                price = "200000",
                location = "Bandung",
                rating = 4.5,
                type = ProductCardType.HISTORY,
                status = "Selesai",
                onClick = {},
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            HorizontalProductCard(
                imageUrl = "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                title = "Cannon 5D Mark IV",
                price = "200000",
                location = "Bandung",
                rating = 4.5,
                type = ProductCardType.COUNTER,
                status = "Selesai",
                onClick = {},
            )
        }
    }
}