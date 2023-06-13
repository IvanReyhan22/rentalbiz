package com.bangkit.rentalbiz.ui.screen.recommendation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bangkit.rentalbiz.FundRangeData
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.CategoryData
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.components.button.ButtonState
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.utils.Helper.formatPrice

@Composable
fun RecommendationInputScreen(navController: NavController, modifier: Modifier = Modifier) {
    var page by remember { mutableStateOf(1) }
    var category by remember { mutableStateOf(CategoryData.categoryList[1].name) }
    var fund by remember { mutableStateOf(FundRangeData.fundRange[0].max) }

    RecommendationContent(
        selectedCategory = category,
        selectedFund=fund,
        page = page,
        onCategorySelect = { category = it },
        onFundSelect = { fund = it },
        nextPage = {
            if (page == 2) {
                navController.navigate(Screen.RecommendationResult.createRoute(category, fund))
            } else {
                page++
            }
        },
    )
}

@Composable
private fun RecommendationContent(
    selectedCategory: String,
    selectedFund:Int,
    onCategorySelect: (String) -> Unit,
    onFundSelect: (Int) -> Unit,
    nextPage: () -> Unit,
    page: Int = 1,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(AppTheme.dimens.spacing_24),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_16),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacing_12),
        modifier = modifier
            .fillMaxSize()
    ) {

        item(span = { GridItemSpan(this.maxLineSpan) }) {
            Column {
                Box(modifier = Modifier.padding(bottom = AppTheme.dimens.spacing_24)) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_dark),
                        contentDescription = stringResource(R.string.rental_biz_logo),
                        contentScale = ContentScale.Fit,
                        modifier = modifier.height(30.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.grape_illustration_7),
                    contentDescription = stringResource(
                        R.string.ilustration
                    ),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(380.dp)
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                Heading(
                    title = stringResource(R.string.choose_business_category),
                    type = HeadingType.H5
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
            }
        }

        /* Category/Fund */
        if (page == 1) {
            items(CategoryData.categoryList) { data ->
                MyButton(
                    title = data.name,
                    size = ButtonSize.SMALL,
                    type = if (selectedCategory == data.name) ButtonType.ACCENT else ButtonType.SECONDARY,
                    onClick = { onCategorySelect(data.name) },
                )
            }
        } else {
            items(FundRangeData.fundRange) { fund ->
                MyButton(
                    title = "${formatPrice(fund.min.toLong())} - ${formatPrice(fund.max.toLong())}",
                    size = ButtonSize.SMALL,
                    type = if (selectedFund == fund.max) ButtonType.ACCENT else ButtonType.SECONDARY,
                    onClick = { onFundSelect(fund.max) },
                )
            }
        }

        /* Button */
        item(span = { GridItemSpan(this.maxLineSpan) }) {
            Box(modifier = Modifier.padding(top = AppTheme.dimens.spacing_24)) {
                MyButton(
                    title = stringResource(R.string.next),
                    modifier = Modifier.fillMaxWidth(),
                    state = ButtonState.ACTIVE,
                    onClick = {
                        nextPage()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun RecommendationPreview() {
    RentalBizTheme {
        RecommendationContent(
            selectedCategory = "Dapur",
            selectedFund = 500000,
            onCategorySelect = {},
            onFundSelect = {},
            nextPage = { /*TODO*/ })
    }
}