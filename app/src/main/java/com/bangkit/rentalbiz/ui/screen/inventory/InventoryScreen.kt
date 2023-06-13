package com.bangkit.rentalbiz.ui.screen.inventory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.card.HorizontalProductCard
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InventoryScreen(
    navController: NavController,
    viewModel: InventoryScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query

    DisposableEffect(Unit) {
        viewModel.getProducts()
        onDispose {}
    }

    Scaffold(
        topBar = {
            TopBar(onBackClick = { navController.popBackStack() })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.CreateProduct.route)
                },
                contentColor = Primary400,
                backgroundColor = Shades0
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "floating button add",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(AppTheme.dimens.spacing_24)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            InventoryContent(
                query = query,
                uiState = uiState,
                onQueryChanged = { viewModel.updateQuery(it) },
                onItemClick = {
                    navController.navigate(
                        Screen.ManageProduct.createRoute(it.toString())
                    )
                },
            )
        }


    }
}

@Composable
fun InventoryContent(
    query: String,
    uiState: UiState<List<Product>>,
    onItemClick: (Int) -> Unit,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.dimens.spacing_24), modifier = modifier,
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppTheme.dimens.spacing_24)
            ) {
                MyTextField(
                    value = query,
                    onValueChange = onQueryChanged,
                    placeholder = stringResource(R.string.name_finding),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        when (uiState) {
            is UiState.Loading -> item {
                Box(
                    modifier = Modifier
                        .padding(bottom = AppTheme.dimens.spacing_24)
                        .fillMaxWidth()
                        .aspectRatio(1F)
                ) {
                    ScreenState(isLoading = true)
                }
            }
            is UiState.Error -> item {
                Box(
                    modifier = Modifier
                        .padding(bottom = AppTheme.dimens.spacing_24)
                        .fillMaxWidth()
                        .aspectRatio(1F)
                ) {
                    ScreenState(isLoading = false)
                }
            }
            is UiState.Success ->
                if (uiState.data.isNotEmpty()) {
                    items(uiState.data) { product ->
                        HorizontalProductCard(
                            imageUrl = product.imageUrl.toString(),
                            title = product.nama.toString(),
                            price = product.harga.toString(),
                            location = product.city.toString(),
                            rating = 4.5,
                            rented = product.totalSewa.toString(),
                            isFavorite = true,
                            onClick = {
                                onItemClick(product.id ?: 0)
                            },
                            onFavoriteClick = {},
                            modifier = Modifier.padding(bottom = AppTheme.dimens.spacing_16)
                        )
                    }
                } else {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(bottom = AppTheme.dimens.spacing_24)
                                .fillMaxWidth()
                                .aspectRatio(1F)
                        ) {
                            ScreenState(isLoading = false)
                        }
                    }
                }
            else -> {}
        }
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
                title = stringResource(R.string.no_available_item),
                type = HeadingType.H5,
                textAlign = TextAlign.Center,
                color = Neutral500,
            )
        }
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(elevation = AppTheme.dimens.spacing_2) {
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
            RoundedIconButton(
                icon = painterResource(id = R.drawable.ic_arrow_left),
                type = ButtonType.SECONDARY,
                size = ButtonSize.MEDIUM,
                onClick = { onBackClick() })
            Heading(title = stringResource(R.string.shop_inventory), type = HeadingType.H5)
            Box(modifier = Modifier.width(40.dp)) {}
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun InventoryPreview() {
    RentalBizTheme {
        InventoryContent(
            query = "",
            uiState = UiState.Loading,
            onItemClick = {},
            onQueryChanged = {})
    }
}