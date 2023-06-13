package com.bangkit.rentalbiz.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.*
import com.bangkit.rentalbiz.ui.components.button.CircleIconButton
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.card.StoreCard
import com.bangkit.rentalbiz.ui.components.modal.ContactBottomSheetModal
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.theme.*
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailProductScreen(
    productId: String,
    navController: NavController,
    viewModel: DetailProductScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var openDialogSuccess by remember { mutableStateOf(false) }
    var openDialogError by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    DisposableEffect(Unit){
        viewModel.getProduct(productId = productId)
        viewModel.checkIsFavorite(productId = productId)

        onDispose {
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            ScreenState(onBackClick = { }, isLoading = true)
        }
        is UiState.Error -> {
            ScreenState(onBackClick = { }, isLoading = false)
        }
        is UiState.Success -> {
            val data = (uiState as UiState.Success<Product>).data
            DetailContent(
                modifier = modifier,
                product = data,
                sheetState = sheetState,
                isFavorite = isFavorite,
                onLoveClick = { viewModel.toggleFavorite(product = data) },
                onBackClick = { navController.popBackStack() },
                onCartClick = {
                    viewModel.addToCart(it, onSuccess = {
                        openDialogSuccess = true
                    }, onFailed = {
                        openDialogError = true
                    })
                },
                onBuyClick = {}
            )
        }
        else -> {
            /* Do Nothing */
        }
    }

    if (openDialogSuccess) {
        SweetSuccess(
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
        SweetError(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailContent(
    product: Product,
    sheetState: ModalBottomSheetState,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onLoveClick: () -> Unit,
    onCartClick: (Product) -> Unit,
    onBuyClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
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
            bottomBar = {
                DetailBottomBar(
                    onCartClick = { onCartClick(product) },
                    onBuyClick = { onBuyClick(product) })
            }
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(bottom = AppTheme.dimens.spacing_56)
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                DetailHead(
                    image = product.imageUrl.toString(),
                    isFavorite = isFavorite,
                    onBackClick = { onBackClick() },
                    onLoveClick = { onLoveClick() }
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                ProductInfo(
                    title = product.nama.toString(),
                    price = product.harga.toString(),
                    rating = 4.5
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                Box(modifier = Modifier.padding(horizontal = AppTheme.dimens.spacing_24)) {
                    StoreCard(
                        name = "ngalamstore",
                        isChat = false,
                        location = product.city.toString(),
                        onChatClick = {},
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                ProductDetailDescription(
                    description = product.deskripsi.toString(),
                    requirenment = product.persyaratan.toString()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                CheckAvailability()
            }
        }

    }
}

@Composable
fun DetailHead(
    image: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onLoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(image).crossfade(true)
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
                type = if (isFavorite) ButtonType.ERROR else ButtonType.SECONDARY,
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
fun ProductDetailDescription(
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
private fun DetailBottomBar(
    onCartClick: () -> Unit,
    onBuyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(elevation = AppTheme.dimens.spacing_8) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .background(Shades0)
                .padding(
                    horizontal = AppTheme.dimens.spacing_24,
                    vertical = AppTheme.dimens.spacing_12
                )
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

@Composable
fun ScreenState(
    isLoading: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Neutral100)
                ) {}
            }
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
                    onClick = { })
            }
        }
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_outline_warning),
                    contentDescription = "Warning Icon",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Neutral500),
                    modifier = Modifier.size(AppTheme.dimens.spacing_48)
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                Heading(
                    title = stringResource(id = R.string.connection_error),
                    type = HeadingType.H5,
                    textAlign = TextAlign.Center,
                    color = Neutral500,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun DetailProductPreview(
) {
    RentalBizTheme {
        val data = Product("", 0, "", "", "", 0)
        val sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        )

        DetailContent(
            product = data,
            sheetState = sheetState,
            isFavorite = false,
            onLoveClick = { /*TODO*/ },
            onBackClick = { /*TODO*/ },
            onCartClick = {},
            onBuyClick = {}
        )
    }
}