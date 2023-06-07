package com.bangkit.rentalbiz.ui.components.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.CategoryData
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.FilterData
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral400
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun FilterBottomSheetModal(
    onFilterSubmit: (FilterData) -> Unit,
    modifier: Modifier = Modifier
) {
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(AppTheme.dimens.spacing_24),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_16),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_12),
        modifier = modifier.fillMaxHeight(1F)
    ) {
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            Column {
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                Heading(title = stringResource(R.string.filter), type = HeadingType.H4)
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                PriceRange(
                    minPriceValue = minPrice,
                    onMinPriceChanged = {
                        minPrice = it
                    },
                    maxPriceValue = maxPrice,
                    onMaxPriceChanged = {
                        maxPrice = it
                    }
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                Location(
                    city = "Malang",
                    location = location,
                    onLocationChanged = { location = it },
                    onLocationClick = {
                    },
                )
            }
        }

        /* Category List */
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
            Heading(title = stringResource(R.string.Category), type = HeadingType.H6)
        }
        items(CategoryData.categoryList) { data ->
            MyButton(
                title = data.name,
                size = ButtonSize.SMALL,
                type = if (category == data.name) ButtonType.ACCENT else ButtonType.SECONDARY,
                onClick = { category = data.name },
            )
        }
        /* End Of Category List */

        item(span = { GridItemSpan(this.maxLineSpan) }) {
            MyButton(
                title = stringResource(R.string.search),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.spacing_8),
                onClick = {
                    val data = FilterData(minPrice, maxPrice, location, category)
                    onFilterSubmit(data)
                }
            )
        }
    }
}

@Composable
fun PriceRange(
    minPriceValue: String,
    onMinPriceChanged: (String) -> Unit,
    maxPriceValue: String,
    onMaxPriceChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Heading(title = stringResource(R.string.price_range), type = HeadingType.H6)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Row(verticalAlignment = Alignment.CenterVertically) {
            MyTextField(
                value = minPriceValue,
                onValueChange = onMinPriceChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = stringResource(
                    R.string.min
                ),
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
            MyTextField(
                value = maxPriceValue,
                onValueChange = onMaxPriceChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = stringResource(
                    R.string.max
                ),
                modifier = Modifier.weight(1F)
            )
        }
    }
}

@Composable
fun Location(
    city: String,
    location: String,
    onLocationChanged: (String) -> Unit,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Heading(title = stringResource(R.string.location), type = HeadingType.H6)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Row(modifier = Modifier.fillMaxWidth()) {
            MyTextField(
                value = location, onValueChange = onLocationChanged, placeholder = stringResource(
                    R.string.search_location
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(R.drawable.ic_outline_search),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            Neutral400
                        ),
                        modifier = Modifier.size(AppTheme.dimens.spacing_16)
                    )
                },
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
            RoundedIconButton(
                icon = painterResource(id = R.drawable.ic_outline_location),
                size = ButtonSize.LARGE,
                type = ButtonType.SECONDARY,
                onClick = { onLocationClick() },
            )
        }
//        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RoundedIconButton(
//                icon = painterResource(id = R.drawable.ic_outline_location),
//                size = ButtonSize.LARGE,
//                type = ButtonType.PRIMARY,
//                onClick = { },
//            )
//            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
//            Paragraph(
//                title = city,
//                type = ParagraphType.MEDIUM,
//                fontWeight = FontWeight.Bold,
//                color = Shades90
//            )
//        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun FilterBottomSheetModalPreview() {
    RentalBizTheme {
        FilterBottomSheetModal(onFilterSubmit = {})
    }
}