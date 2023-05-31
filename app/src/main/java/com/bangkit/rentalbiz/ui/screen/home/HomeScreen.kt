package com.bangkit.rentalbiz.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.dummy.DummyProductData
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.card.VerticalProductCard
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral500
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = AppTheme.dimens.spacing_24),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_16),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_12),
        modifier = modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            Column(
                modifier = modifier.padding(
                    top = AppTheme.dimens.spacing_24,
                )
            ) {
                TopBar(onLocationClick = {}, onCartClick = {})
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                FilteringFunction(
                    query = "searchQuery",
                    onQueryChange = {  },
                    onFilterClick = {},
                )
            }
        }
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            CategoryList()
        }
        items(DummyProductData.productList) { product ->
            VerticalProductCard(
                imageUrl = product.image,
                title = product.name,
                price = product.price,
                location = product.location,
                rating = product.rating,
                onClick = {
                    navController.navigate(Screen.DetailProduct.route)
                }
            )
        }

    }
}

@Composable
fun TopBar(
    onLocationClick: () -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier) {
            Heading(
                title = stringResource(R.string.good_morning),
                type = HeadingType.H6,
                modifier = Modifier.padding(bottom = AppTheme.dimens.spacing_4)
            )
            Row(modifier = Modifier.clickable { onLocationClick }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location_red),
                    contentDescription = "Marker Location Icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(end = AppTheme.dimens.spacing_8)
                        .size(24.dp)
                )
                Paragraph(
                    title = stringResource(R.string.dummy_location),
                    type = ParagraphType.MEDIUM,
                    color = Neutral500
                )
            }
        }
        Row(modifier = Modifier) {
            CircleIconButton(
                icon = painterResource(id = R.drawable.ic_shopping_cart),
                type = ButtonType.SECONDARY,
                onClick = onCartClick
            )
        }
    }
}

@Composable
fun FilteringFunction(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            MyTextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = stringResource(R.string.name_finding),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
        RoundedIconButton(
            icon = painterResource(id = R.drawable.ic_category),
            size = ButtonSize.LARGE,
            onClick = onFilterClick
        )
    }
}

@Composable
fun CategoryList(
    modifier: Modifier = Modifier
) {
    val tempItem = listOf("Populer", "Terbaru", "Komputer", "Dapur", "Alat Medis", "Fotografi")
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        items(tempItem) { item ->
            MyButton(
                title = item,
                onClick = { /*TODO*/ },
                size = ButtonSize.SMALL,
                type = ButtonType.SECONDARY,
                modifier = Modifier.padding(end = AppTheme.dimens.spacing_8)
            )
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun HomeScreenPreview() {
    RentalBizTheme {
        HomeScreen(navController = rememberNavController())
    }
}