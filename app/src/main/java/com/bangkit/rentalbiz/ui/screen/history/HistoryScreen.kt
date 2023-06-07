package com.bangkit.rentalbiz.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.dummy.DummyProductData
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.card.HorizontalProductCard
import com.bangkit.rentalbiz.ui.components.card.ProductCardType
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.ui.theme.Shades0

@Composable
fun HistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Surface(elevation = AppTheme.dimens.spacing_2) {
                Box(
                    modifier = Modifier
                        .background(color = Shades0)
                ) {

                    TopBar(onFavoriteClick = {
                        navController.navigate(Screen.Favorite.route)
                    })
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = AppTheme.dimens.spacing_24,
                    end = AppTheme.dimens.spacing_24,
                    top = AppTheme.dimens.spacing_16
                ),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_16),
                modifier = modifier.fillMaxSize()
            ) {
                items(DummyProductData.productList) { product ->
                    HorizontalProductCard(
                        imageUrl = product.imageUrl.toString(),
                        title = product.nama.toString(),
                        price = product.harga.toString(),
                        location = product.city.toString(),
                        rating = 4.5,
                        status = "Selesai",
                        type = ProductCardType.HISTORY,
                        onClick = {
                            navController.navigate(Screen.DetailProduct.route)
                        },
                        onFavoriteClick = {},
                        rented = "10"
                    )
                }
            }
        }

    }

}


@Composable
private fun TopBar(
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = AppTheme.dimens.spacing_24, vertical = AppTheme.dimens.spacing_12)
            .fillMaxWidth()
    ) {
        Heading(title = stringResource(id = R.string.history_tab), type = HeadingType.H5)
        CircleIconButton(
            icon = painterResource(id = R.drawable.ic_outline_heart),
            size = ButtonSize.MEDIUM,
            type = ButtonType.SECONDARY,
            onClick = { onFavoriteClick() })
    }

}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun HistoryScreenPreview() {
    RentalBizTheme {
        HistoryScreen(navController = rememberNavController())
    }
}