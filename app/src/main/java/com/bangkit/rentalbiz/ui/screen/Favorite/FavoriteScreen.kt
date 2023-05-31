package com.bangkit.rentalbiz.ui.screen.Favorite

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
fun FavoriteScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Surface(elevation = AppTheme.dimens.spacing_2) {
                Box(modifier = Modifier.background(color = Shades0)) {
                    TopBar(
                        onCartClick = {}
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = AppTheme.dimens.spacing_24,
                    end = AppTheme.dimens.spacing_24,
                    top = AppTheme.dimens.spacing_12
                ),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_16),
                modifier = modifier.fillMaxSize()
            ) {

                items(DummyProductData.productList) { product ->
                    HorizontalProductCard(
                        imageUrl = product.image,
                        title = product.name,
                        price = product.price,
                        location = product.location,
                        rating = product.rating,
                        status = "Selesai",
                        type = ProductCardType.FAVORITE,
                        isFavorite = true,
                        onClick = {
                            navController.navigate(Screen.DetailProduct.route)
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun TopBar(
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.spacing_24,
                vertical = AppTheme.dimens.spacing_12
            )
            .fillMaxWidth()
    ) {
        Heading(title = stringResource(id = R.string.history_tab), type = HeadingType.H5)
        CircleIconButton(
            icon = painterResource(id = R.drawable.ic_shopping_cart),
            size = ButtonSize.MEDIUM,
            type = ButtonType.SECONDARY,
            onClick = { onCartClick() })
    }

}


@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun FavoriteScreenPreview() {
    RentalBizTheme {
        FavoriteScreen(navController = rememberNavController())
    }
}