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
import androidx.compose.foundation.shape.CornerSize
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
import com.bangkit.rentalbiz.data.Category
import com.bangkit.rentalbiz.data.CategoryData
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.*
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.card.VerticalProductCard
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.modal.FilterBottomSheetModal
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral500
import com.bangkit.rentalbiz.ui.theme.Primary400
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    toggleBottomNav: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val query by viewModel.query
    val uiState by viewModel.uiState.collectAsState()
    val emptyList: List<Product> = emptyList()
    val coroutineScope = rememberCoroutineScope()
    val skipHalfExpanded by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded,
        confirmStateChange = { it != ModalBottomSheetValue.Expanded },
    )
    var selectedCategory by remember { mutableStateOf(CategoryData.categoryList[0].name) }
    if (sheetState.isVisible) toggleBottomNav(false) else toggleBottomNav(true)

    HomeContent(
        modifier = modifier,
        sheetState = sheetState,
        query = query,
        uiState = uiState,
        selectedCategory = selectedCategory,
        onCartClick = {
            navController.navigate(Screen.Cart.route)
        },
        productList = when (val currentState = uiState) {
            is UiState.Success -> currentState.data
            else -> emptyList
        },
        onProductClick = {
            navController.navigate(Screen.DetailProduct.createRoute(productId = it.id.toString()))
        },
        onQueryChange = { viewModel.updateQuery(it) },
        onFilterSubmit = {
            coroutineScope.launch {
                sheetState.hide()
            }
            viewModel.filterProduct(it)
        },
        onFilterClick = {
            coroutineScope.launch {
                if (sheetState.isVisible) {
                    sheetState.hide()
                } else {
                    sheetState.show()
                }
            }
        },
        onCategoryClick = {
            selectedCategory = it.name
        }
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    query: String,
    onFilterSubmit: (FilterData) -> Unit,
    sheetState: ModalBottomSheetState,
    uiState: UiState<Any> = UiState.Idle,
    productList: List<Product>,
    onProductClick: (Product) -> Unit,
    onQueryChange: (String) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onFilterClick: () -> Unit,
    onCartClick: () -> Unit,
    selectedCategory: String,
    modifier: Modifier = Modifier
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = MaterialTheme.shapes.large.copy(
            topStart = CornerSize(AppTheme.dimens.radius_24),
            topEnd = CornerSize(AppTheme.dimens.radius_24)
        ),
        sheetContent = { FilterBottomSheetModal(onFilterSubmit = onFilterSubmit) },
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
                    TopBar(onLocationClick = {}, onCartClick = { onCartClick() })
                    Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                    FilteringFunction(
                        query = query,
                        onQueryChange = { onQueryChange(it) },
                        onFilterClick = { onFilterClick() },
                    )
                }
            }
            item(span = { GridItemSpan(this.maxLineSpan) }) {
                CategoryList(selectedCategory = selectedCategory, onCategoryClick = onCategoryClick)
            }
            when (uiState) {
                is UiState.Loading -> {
                    item(span = { GridItemSpan(this.maxLineSpan) }) {
                        LoadingState()
                    }
                }
                is UiState.Success -> {
                    if (productList.isEmpty()) {
                        item(span = { GridItemSpan(this.maxLineSpan) }) {
                            NoDataIndicator()
                        }
                    } else {
                        items(productList) { product ->
                            VerticalProductCard(
                                imageUrl = product.imageUrl!!,
                                title = product.nama!!,
                                price = product.harga!!.toString(),
                                location = product.city!!,
                                rating = product.totalSewa!!.toDouble(),
                                onClick = {
                                    onProductClick(product)
                                }
                            )
                        }
                    }
                }
                is UiState.Error -> {}
                else -> {}
            }
            item(span = { GridItemSpan(this.maxLineSpan) }) {}
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

            Paragraph(
                title = "Apa yang kamu cari hari ini?",
                type = ParagraphType.MEDIUM,
                color = Neutral500
            )
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
    selectedCategory: String,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        items(CategoryData.categoryList) { item ->
            MyButton(
                title = item.name,
                onClick = { onCategoryClick(item) },
                size = ButtonSize.SMALL,
                type = if (item.name == selectedCategory) ButtonType.ACCENT else ButtonType.SECONDARY,
                modifier = Modifier.padding(end = AppTheme.dimens.spacing_8)
            )
        }
    }
}

@Composable
fun LoadingState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1F),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Primary400,
            strokeWidth = AppTheme.dimens.spacing_4,
            modifier = Modifier.size(AppTheme.dimens.spacing_48)
        )
    }
}

@Composable
fun NoDataIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1F),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_outline_document_search),
                contentDescription = "No data icon",
                colorFilter = ColorFilter.tint(Neutral500),
                modifier = Modifier.size(AppTheme.dimens.spacing_48)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            Heading(
                title = stringResource(id = R.string.product_not_found),
                type = HeadingType.H5,
                textAlign = TextAlign.Center,
                color = Neutral500,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun HomeScreenPreview() {
    RentalBizTheme {
        HomeContent(
            query = "",
            sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
            ),
            productList = emptyList(),
            selectedCategory = "Populer",
            onProductClick = { /*TODO*/ },
            onQueryChange = {},
            onFilterClick = { /*TODO*/ },
            onFilterSubmit = {},
            onCartClick = {},
            onCategoryClick = {}
        )
    }
}