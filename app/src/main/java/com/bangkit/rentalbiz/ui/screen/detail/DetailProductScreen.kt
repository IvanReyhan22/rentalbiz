package com.bangkit.rentalbiz.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.dummy.DummyProductData
import com.bangkit.rentalbiz.ui.common.ButtonSize
import com.bangkit.rentalbiz.ui.common.ButtonType
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.card.StoreCard
import com.bangkit.rentalbiz.ui.components.modal.ContactBottomSheetModal
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailProductScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val data = DummyProductData.productList[0]

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { ContactBottomSheetModal(title = "String") },
        sheetShape = MaterialTheme.shapes.large.copy(
            topStart = CornerSize(AppTheme.dimens.radius_16),
            topEnd = CornerSize(AppTheme.dimens.radius_16)
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Scaffold(
            bottomBar = { DetailBottomBar() }
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(bottom = AppTheme.dimens.spacing_56)
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                DetailHead(
                    onBackClick = {navController.popBackStack()},
                    onLoveClick = {}
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                ProductInfo(
                    title = "Cannon 5D Mark IV",
                    price = "200000",
                    rating = 4.5
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                Box(modifier = Modifier.padding(horizontal = AppTheme.dimens.spacing_24)) {
                    StoreCard(
                        name = "ngalamstore",
                        location = "Jl. Chocolate 12,  Malang",
                        onChatClick = {
                            coroutineScope.launch {
                                if (sheetState.isVisible) sheetState.hide()
                                else sheetState.show()
                            }
                        },
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                ProductDetialDescription(
                    description = data.description,
                    requirenment = data.requirenment
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                CheckAvailability()
            }
        }
//        ModalBottomSheetLayout(
//            sheetState = sheetState,
//            sheetContent = { BottomSheet() },
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = 24.dp)
//                    .padding(horizontal = 24.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = "Welcome to bottom sheet playground!",
//                    modifier = Modifier.fillMaxWidth(),
//                    style = MaterialTheme.typography.h4,
//                    textAlign = TextAlign.Center
//                )
//                Spacer(modifier = Modifier.height(32.dp))
//                Button(
//                    onClick = {
//                        coroutineScope.launch {
//                            if (sheetState.isVisible) sheetState.hide()
//                            else sheetState.show()
//                        }
//                    }
//                ) {
//                    Text(text = "Click to show bottom sheet")
//                }
//            }
//        }
    }

}

@Composable
fun DetailHead(
    onBackClick: () -> Unit,
    onLoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val data = DummyProductData.productList[0]
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(data.image).crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(AppTheme.dimens.spacing_24)
                .fillMaxWidth()
        ) {
            RoundedIconButton(
                icon = painterResource(id = R.drawable.ic_arrow_left),
                type = ButtonType.SECONDARY,
                size = ButtonSize.LARGE,
                onClick = { onBackClick() })

            CircleIconButton(
                icon = painterResource(id = R.drawable.ic_outline_heart),
                type = ButtonType.SECONDARY,
                size = ButtonSize.LARGE,
                onClick = { onLoveClick() })
        }
    }
}

@Composable
fun ProductInfo(
    title: String,
    price: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = AppTheme.dimens.spacing_24)
            .fillMaxWidth()
    ) {
        Heading(title = title, type = HeadingType.H5)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Paragraph(
                title = "Rp. $price / Hari",
                type = ParagraphType.SMALL,
                fontWeight = FontWeight.Bold
            )
            ProductRating(rating)
        }
    }
}

@Composable
fun ProductRating(rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = "Rating Icon",
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_4))
        Paragraph(
            title = rating.toString(),
            type = ParagraphType.XSMALL,
            fontWeight = FontWeight.Bold,
            color = Warning400
        )
    }
}

@Composable
fun ProductDetialDescription(
    description: String,
    requirenment: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = AppTheme.dimens.spacing_24)) {
        Paragraph(
            title = stringResource(R.string.description),
            type = ParagraphType.MEDIUM,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Paragraph(
            title = description,
            type = ParagraphType.SMALL,
            color = Neutral700,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
        Paragraph(
            title = stringResource(R.string.requirenment),
            type = ParagraphType.MEDIUM,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Paragraph(
            title = requirenment,
            type = ParagraphType.SMALL,
            color = Neutral700,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CheckAvailability(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = AppTheme.dimens.spacing_24)) {
        Paragraph(
            title = stringResource(R.string.check_availability),
            type = ParagraphType.MEDIUM,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DetailBottomBar(modifier: Modifier = Modifier) {
    Surface(elevation = AppTheme.dimens.spacing_8) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .background(Shades0)
                .padding(
                    horizontal = AppTheme.dimens.spacing_24,
                    vertical = AppTheme.dimens.spacing_8
                )
        ) {
            RoundedIconButton(
                icon = painterResource(id = R.drawable.ic_shopping_cart),
                size = ButtonSize.LARGE,
                type = ButtonType.SECONDARY,
                onClick = { /*TODO*/ },
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
            MyButton(
                title = stringResource(R.string.rent),
                size = ButtonSize.LARGE,
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1F)
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun DetailProductPreview(
) {
    RentalBizTheme {
        DetailProductScreen(navController = rememberNavController())
    }
}