package com.bangkit.rentalbiz.ui.screen.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.card.HorizontalProductCard
import com.bangkit.rentalbiz.ui.components.card.ProductCardType
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.*

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var isCartEmpty by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(elevation = AppTheme.dimens.spacing_2) {
                Box(modifier = Modifier.background(color = Shades0)) {
                    TopBar(onLoveClick = {
                        navController.navigate(Screen.Favorite.route) {
                            popUpTo(Screen.Cart.route) { inclusive = true }
                        }
                    })
                }
            }
        },
        bottomBar = {
            if (!isCartEmpty) {
                BottomBar(
                    onRentClick = { navController.navigate(Screen.CheckOut.route) },
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {
            when (uiState) {
                is UiState.Loading -> {
                    isCartEmpty = true
                    ScreenState(isLoading = true)
                }
                is UiState.Error -> {
                    isCartEmpty = true
                    ScreenState(isLoading = false)
                }
                is UiState.Success -> {
                    isCartEmpty = false
                    CartContent(
                        data = (uiState as UiState.Success<List<CartItem>>).data,
                        deleteCartItem = { viewModel.deleteCartItem(it) },
                        onCartEmpty = { isCartEmpty = true },
                        onItemCountChange = { item, count -> viewModel.updateItem(item, count) }
                    )
                }
                else -> {
                    isCartEmpty = true
                }
            }
        }

    }

}

@Composable
fun CartContent(
    data: List<CartItem>,
    deleteCartItem: (String) -> Unit,
    onCartEmpty: () -> Unit,
    onItemCountChange: (CartItem, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = AppTheme.dimens.spacing_24,
            end = AppTheme.dimens.spacing_24,
            top = AppTheme.dimens.spacing_12
        ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_16),
        modifier = modifier.fillMaxSize()
    ) {
        if (data.isNotEmpty()) {
            items(data) { item ->
                HorizontalProductCard(
                    onClick = { /*TODO*/ },
                    onZeroCount = { deleteCartItem(item.id) },
                    imageUrl = item.imageUrl,
                    title = item.nama,
                    location = item.city,
                    rating = 4.5,
                    price = item.harga,
                    itemCount = item.jumlahSewa,
                    status = "",
                    rented = "",
                    onCountChange = { onItemCountChange(item, it) },
                    type = ProductCardType.COUNTER
                )
            }
        } else {
            onCartEmpty()
        }
    }
}

@Composable
private fun TopBar(
    onLoveClick: () -> Unit,
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
        Heading(title = stringResource(R.string.cart_tab), type = HeadingType.H5)
        CircleIconButton(
            icon = painterResource(id = R.drawable.ic_outline_heart),
            size = ButtonSize.MEDIUM,
            type = ButtonType.SECONDARY,
            onClick = { onLoveClick() })
    }

}

@Composable
private fun ScreenState(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(AppTheme.dimens.spacing_24)
            .fillMaxSize()
    ) {
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                CircularProgressIndicator(
                    color = Primary400,
                    strokeWidth = AppTheme.dimens.spacing_4,
                    modifier = Modifier.size(AppTheme.dimens.spacing_48)
                )
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_outline_document_search),
                contentDescription = "Warning Icon",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Neutral500),
                modifier = Modifier.size(AppTheme.dimens.spacing_48)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            Heading(
                title = stringResource(id = R.string.cart_empty),
                type = HeadingType.H5,
                textAlign = TextAlign.Center,
                color = Neutral500,
            )
        }
    }
}

@Composable
private fun BottomBar(
    onRentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(elevation = AppTheme.dimens.spacing_8) {
        Box(
            modifier = modifier.padding(
                horizontal = AppTheme.dimens.spacing_24,
                vertical = AppTheme.dimens.spacing_12
            )
        ) {
            MyButton(
                title = stringResource(R.string.rent),
                size = ButtonSize.LARGE,
                onClick = { onRentClick() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun CartPreview() {
    RentalBizTheme {
        CartContent(
            onCartEmpty = {},
            data = emptyList(),
            deleteCartItem = {},
            onItemCountChange = { _, _ -> }
        )
    }
}