package com.bangkit.rentalbiz.ui.screen.history

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.rememberNavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.remote.response.TransactionsItem
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.card.HistoryCard
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.*

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        viewModel.getHistory()

        onDispose {
        }
    }

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
                when (uiState) {
                    is UiState.Loading -> item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1F),
                            contentAlignment = Alignment.Center
                        ) {
                            ScreenState(isLoading = true)
                        }
                    }
                    is UiState.Error -> item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1F),
                            contentAlignment = Alignment.Center
                        ) {
                            ScreenState(isLoading = false)
                        }
                    }
                    is UiState.Success -> items((uiState as UiState.Success<List<TransactionsItem>>).data) { item ->
                        HistoryCard(
                            title = item.namaBarang.toString(),
                            quantity = item.jumlah.toString(),
                            finalPrice = item.totalHargaSewa.toString(),
                            returnDate = item.tanggalKembali.toString(),
                            status = item.status ?: 0,
                            imageUrl = if (item.imageUrl == null) "https://placehold.co/600x400@2x.png" else item.imageUrl.toString(),
                            onClick = { /*TODO*/ },
                        )
                    }
                    else -> {}
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
                title = stringResource(R.string.history_empty),
                type = HeadingType.H5,
                textAlign = TextAlign.Center,
                color = Neutral500,
            )
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