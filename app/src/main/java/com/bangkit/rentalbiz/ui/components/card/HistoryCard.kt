package com.bangkit.rentalbiz.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*
import com.bangkit.rentalbiz.utils.Helper.calculateDaysLeftFromDate
import com.bangkit.rentalbiz.utils.Helper.dateServerToReadable

@Composable
fun HistoryCard(
    title: String,
    quantity: String,
    finalPrice: String,
    returnDate: String,
    imageUrl: String,
    status: Int,
    onClick: () -> Unit,
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
            .padding(AppTheme.dimens.spacing_8)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.Top,
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
            HistoryDetail(
                title = title,
                returnDate = returnDate,
                quantity = quantity,
                finalPrice = finalPrice,
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_8))
            Box(modifier = Modifier.size(AppTheme.dimens.spacing_36)) {
                CircleIconButton(
                    icon = when (status) {
                        1 -> painterResource(id = R.drawable.ic_outline_clock)
                        2 -> painterResource(id = R.drawable.ic_outline_archive)
                        3 -> painterResource(id = R.drawable.ic_x)
                        4 -> painterResource(id = R.drawable.ic_check)
                        else -> painterResource(id = R.drawable.ic_outline_clock)
                    },
                    size = ButtonSize.MEDIUM,
                    type = when (status) {
                        1 -> ButtonType.SECONDARY
                        2 -> ButtonType.WARNING
                        3 -> ButtonType.ERROR
                        4 -> ButtonType.SUCCESS
                        else -> ButtonType.SECONDARY
                    },
                    onClick = { /*TODO*/ },
                )
            }
        }
    }
}

@Composable
private fun HistoryDetail(
    title: String,
    returnDate: String,
    quantity: String,
    finalPrice: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Paragraph(title = title, type = ParagraphType.MEDIUM, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
        Paragraph(
            title = "Total Sewa $quantity",
            type = ParagraphType.SMALL,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
        DueDate(returnDate = returnDate)
    }
}

@Composable
fun DueDate(
    returnDate: String,
    modifier: Modifier = Modifier
) {
    Column() {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_outline_calendar),
                contentDescription = "Clock icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(AppTheme.dimens.spacing_18)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_4))
            Paragraph(
                title = "${dateServerToReadable(returnDate)}",
                type = ParagraphType.SMALL,
                color = Neutral500
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_outline_secondary_clock),
                contentDescription = "Clock icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(AppTheme.dimens.spacing_18)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_4))
            Paragraph(
                title = "${calculateDaysLeftFromDate(returnDate)} hari",
                type = ParagraphType.SMALL,
                color = Neutral500
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreview() {
    RentalBizTheme() {
        HistoryCard(
            title = "CANNON 5D MARK IV",
            quantity = "2",
            finalPrice = "100000",
            returnDate = "2023-06-14T00:00:00.000Z",
            imageUrl = "",
            status = 1,
            onClick = { /*TODO*/ },
        )
    }
}