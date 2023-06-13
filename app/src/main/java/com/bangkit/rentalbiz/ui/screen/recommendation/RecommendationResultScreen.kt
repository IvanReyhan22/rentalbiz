package com.bangkit.rentalbiz.ui.screen.recommendation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
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
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.ui.components.ScreenHeading
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.card.HorizontalProductCard
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral500
import com.bangkit.rentalbiz.ui.theme.Primary400
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.utils.Helper.formatPrice
import com.talhafaki.composablesweettoast.util.SweetToastUtil

@Composable
fun RecommendationResultScreen(
    category: String,
    fund: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RecommendationResultScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var itemNull by remember { mutableStateOf(false) }
    val totalPrice by viewModel.totalPrice

    var openDialogSuccess by remember { mutableStateOf(false) }
    var openDialogError by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        viewModel.getRecommendation(category, fund)
        onDispose {
        }
    }

    Scaffold(bottomBar = {
        BottomBar(
            total = totalPrice,
            onCartClick = {
                if (uiState is UiState.Success) viewModel.addToCart(
                    (uiState as UiState.Success<List<Product>>).data,
                    onSuccess = {
                        openDialogSuccess = true
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    onFailed = {
                        openDialogError = true
                    })
            },
            onBuyClick = { /*TODO*/ })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            RecommendationContent(
                uiState = uiState,
                isItemNull = itemNull,
                onItemNull = { itemNull = it },
                onProductClick = {
                    navController.navigate(Screen.DetailProduct.createRoute(productId = it.toString()))
                },
                onSkipClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
        }
    }


    if (openDialogSuccess) {
        SweetToastUtil.SweetSuccess(
            message = stringResource(id = R.string.item_added),
            duration = Toast.LENGTH_SHORT,
            padding = PaddingValues(
                vertical = AppTheme.dimens.spacing_4,
                horizontal = AppTheme.dimens.spacing_16
            ),
            contentAlignment = Alignment.BottomCenter
        )
        openDialogSuccess = false
    }

    if (openDialogError) {
        SweetToastUtil.SweetError(
            message = stringResource(R.string.add_item_failed),
            duration = Toast.LENGTH_SHORT,
            padding = PaddingValues(
                vertical = AppTheme.dimens.spacing_4,
                horizontal = AppTheme.dimens.spacing_16
            ),
            contentAlignment = Alignment.BottomCenter
        )
        openDialogError = false
    }
}

@Composable
private fun RecommendationContent(
    uiState: UiState<List<Product>>,
    onProductClick: (Int) -> Unit,
    onItemNull: (Boolean) -> Unit,
    onSkipClick: () -> Unit,
    isItemNull: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.dimens.spacing_24),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier=Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(bottom = AppTheme.dimens.spacing_24)) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_dark),
                        contentDescription = stringResource(R.string.rental_biz_logo),
                        contentScale = ContentScale.Fit,
                        modifier = modifier.height(30.dp)
                    )
                }
                Paragraph(
                    title = stringResource(R.string.skip),
                    color = Primary400,
                    fontWeight = FontWeight.Medium,
                    type = ParagraphType.MEDIUM,
                    modifier = Modifier.clickable {
                        onSkipClick()
                    }
                )
            }
            ScreenHeading(
                title = if (isItemNull) stringResource(R.string.not_found) else stringResource(R.string.recommendation_title),
                subTitle = if (isItemNull) stringResource(R.string.no_recommendation) else stringResource(
                    R.string.recommendation_subtitle
                )
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
        }
        when (uiState) {
            is UiState.Loading -> item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1F), contentAlignment = Alignment.Center
                ) {
                    ScreenState(isLoading = true)
                }
            }
            is UiState.Success -> {
                val data = uiState.data
                if (data.isEmpty()) {
                    onItemNull(true)
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1F),
                            contentAlignment = Alignment.Center
                        ) {
                            ScreenState(isLoading = false)
                        }
                    }
                } else {
                    onItemNull(false)
                    items(items = data) { product ->
                        HorizontalProductCard(
                            imageUrl = product.imageUrl.toString(),
                            title = product.nama.toString(),
                            location = product.city.toString(),
                            rating = 4.5,
                            price = product.harga.toString(),
                            status = "",
                            rented = product.totalSewa.toString(),
                            onClick = { onProductClick(product.id!!) },
                            modifier = Modifier.padding(bottom = AppTheme.dimens.spacing_12)
                        )
                    }
                }
            }
            is UiState.Error -> item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1F), contentAlignment = Alignment.Center
                ) {
                    ScreenState(isLoading = false)
                }
            }
            else -> item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1F), contentAlignment = Alignment.Center
                ) {
                    ScreenState(isLoading = false)
                }
            }
        }
    }
}

@Composable
private fun ScreenState(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
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
            Box(
                modifier
                    .fillMaxSize()
                    .aspectRatio(1F), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_document_search),
                    contentDescription = "Warning Icon",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Neutral500),
                    modifier = Modifier.size(120.dp)
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    total: Int,
    onCartClick: () -> Unit,
    onBuyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(elevation = AppTheme.dimens.spacing_8) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimens.spacing_24,
                    vertical = AppTheme.dimens.spacing_12
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Paragraph(
                    title = stringResource(R.string.total),
                    fontWeight = FontWeight.Bold,
                    type = ParagraphType.MEDIUM,
                    modifier = Modifier.weight(1F)
                )
                Paragraph(
                    title = formatPrice(total.toLong()),
                    fontWeight = FontWeight.Bold,
                    color = Primary400,
                    type = ParagraphType.MEDIUM,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1F)
                )
            }
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                RoundedIconButton(
                    icon = painterResource(id = R.drawable.ic_shopping_cart),
                    size = ButtonSize.LARGE,
                    type = ButtonType.SECONDARY,
                    onClick = { onCartClick() },
                )
                Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
                MyButton(
                    title = stringResource(R.string.rent),
                    size = ButtonSize.LARGE,
                    onClick = { onBuyClick() },
                    modifier = Modifier.weight(1F)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun RecommendationResultPreview() {
    RentalBizTheme {
        RecommendationResultPreview()
    }
}