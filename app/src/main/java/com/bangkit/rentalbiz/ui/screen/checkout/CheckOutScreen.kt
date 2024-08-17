@file:OptIn(ExperimentalMaterial3Api::class)

package com.bangkit.rentalbiz.ui.screen.checkout

import android.util.Range
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.ui.common.*
import com.bangkit.rentalbiz.ui.components.button.ButtonState
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.PaymentButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.card.HorizontalProductCard
import com.bangkit.rentalbiz.ui.components.card.ProductCardType
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.modal.SetAddressBottomSheetModal
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.*
import com.bangkit.rentalbiz.utils.AddressData
import com.bangkit.rentalbiz.utils.Helper.dateToReadable
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.talhafaki.composablesweettoast.util.SweetToastUtil
import kotlinx.coroutines.launch
import java.time.LocalDate

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun CheckOutScreen(
    navController: NavController,
    viewModel: CheckOutScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val address by viewModel.address
    val totalItemPrice by viewModel.totalItemPrice
    val deliveryFee by viewModel.deliveryFee
    val calendarState = rememberUseCaseState()
    val coroutineScope = rememberCoroutineScope()
    val selectedDateRange by viewModel.dateRange
    val transactionProcess by viewModel.transactionProgress
    var openDialogError by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    Scaffold(bottomBar = {
        if (!sheetState.isVisible) {
            BottomBar(
                transactionProcess = transactionProcess,
                onRentClick = {
                    viewModel.transactionProcess(
                        onSuccess = {
                            navController.navigate(Screen.SuccessCheckout.route) {
                                popUpTo(Screen.CheckOut.route) { inclusive = true }
                            }
                        },
                        onFailed = {
                            openDialogError = true
                        }
                    )
                },
            )
        }
    }) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues)) {
            ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetContent = {
                    SetAddressBottomSheetModal(
                        title = address.title,
                        address = address.address,
                        onTitleChange = { viewModel.updateAddress(title = it) },
                        onAddressChange = { viewModel.updateAddress(address = it) },
                        submit = { _, _ ->
                            coroutineScope.launch {
                                viewModel.saveAddress(
                                    viewModel.address.value.title,
                                    viewModel.address.value.address
                                )
                                sheetState.hide()
                            }
                        },
                    )
                },
                sheetShape = MaterialTheme.shapes.large.copy(
                    topStart = CornerSize(AppTheme.dimens.radius_16),
                    topEnd = CornerSize(AppTheme.dimens.radius_16)
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CheckOutContent(
                    uiState = uiState,
                    address = address,
                    totalItemPrice = totalItemPrice,
                    deliveryFee = deliveryFee,
                    selectedDateRange = selectedDateRange,
                    onDateClick = { calendarState.show() },
                    onItemClick = {},
                    deleteCartItem = {},
                    onAddressEditClick = {
                        coroutineScope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                            }
                        }
                    }
                )
            }
            CalendarDialog(
                state = calendarState,
                config = CalendarConfig(
                    style = CalendarStyle.MONTH,
                ),
                selection = CalendarSelection.Period(
                    selectedRange = Range(selectedDateRange.lower, selectedDateRange.upper)
                ) { startDate, endDate ->
                    viewModel.updateDate(Range(startDate, endDate))
                },
            )
        }
    }

    if (openDialogError) {
        SweetToastUtil.SweetError(
            message = stringResource(R.string.payment_failed),
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
fun CheckOutContent(
    uiState: UiState<List<CartItem>>,
    address: AddressData,
    totalItemPrice: Int,
    deliveryFee: Int,
    selectedDateRange: Range<LocalDate>,
    onDateClick: () -> Unit,
    onItemClick: (String) -> Unit,
    onAddressEditClick: () -> Unit,
    deleteCartItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(AppTheme.dimens.spacing_24),
        modifier = modifier
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(bottom = AppTheme.dimens.spacing_24)
                    .fillMaxWidth()
            ) {
                Address(address = address, onEditClick = { onAddressEditClick() })
            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(bottom = AppTheme.dimens.spacing_24)
                    .fillMaxWidth()
            ) {
                DatePicker(
                    startDate = selectedDateRange.lower.toString(),
                    returnDate = selectedDateRange.upper.toString(),
                    onDateClick = { onDateClick() })
            }
        }
        when (uiState) {
            is UiState.Loading -> item {
                Box(
                    modifier = Modifier
                        .padding(bottom = AppTheme.dimens.spacing_24)
                        .fillMaxWidth()
                ) {
                    ScreenState(isLoading = true)
                }
            }
            is UiState.Error -> item {
                Box(
                    modifier = Modifier
                        .padding(bottom = AppTheme.dimens.spacing_24)
                        .fillMaxWidth()
                ) {
                    ScreenState(isLoading = true)
                }
            }
            is UiState.Success -> {
                item {
                    Box(
                        modifier = Modifier
                            .padding(bottom = AppTheme.dimens.spacing_24)
                            .fillMaxWidth()
                    ) {
                        Paragraph(
                            title = stringResource(R.string.item_in_cart),
                            type = ParagraphType.LARGE,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                items(uiState.data) { item ->
                    HorizontalProductCard(
                        onClick = { onItemClick(item.id) },
                        onZeroCount = { deleteCartItem(item.id) },
                        imageUrl = item.imageUrl,
                        title = item.nama,
                        location = item.city,
                        rating = 4.5,
                        price = item.harga,
                        itemCount = item.jumlahSewa,
                        type = ProductCardType.NORMAL,
                        modifier = Modifier.padding(bottom = AppTheme.dimens.spacing_16)
                    )
                }
            }
            else -> {}
        }
        item {
            Column(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.spacing_8)
                    .fillMaxWidth()
            ) {
                PriceDetail(
                    total = totalItemPrice,
                    deliveryFee = deliveryFee,
                    finalPrice = totalItemPrice + deliveryFee
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                PaymentMethod()
            }
        }
    }
}

@Composable
fun DatePicker(
    startDate: String,
    returnDate: String,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Paragraph(
            title = stringResource(R.string.detail),
            type = ParagraphType.LARGE,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = AppTheme.dimens.spacing_16)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.dimens.spacing_16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1F)) {
                Paragraph(
                    title = stringResource(id = R.string.rent_start),
                    type = ParagraphType.MEDIUM,
                    fontWeight = FontWeight.Medium,
                    color = Shades90,
                    modifier = modifier
                        .padding(bottom = AppTheme.dimens.spacing_4)
                        .fillMaxWidth()
                )
                MyTextField(
                    value = dateToReadable(startDate),
                    readOnly = true,
                    onValueChange = { }
                )
            }
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_16))
            Column(modifier = Modifier.weight(1F)) {
                Paragraph(
                    title = stringResource(id = R.string.item_return),
                    type = ParagraphType.MEDIUM,
                    fontWeight = FontWeight.Medium,
                    color = Shades90,
                    modifier = modifier
                        .padding(bottom = AppTheme.dimens.spacing_4)
                        .fillMaxWidth()
                )
                MyTextField(
                    value = dateToReadable(returnDate),
                    readOnly = true,
                    onValueChange = { },
                )
            }
        }
        MyButton(
            title = stringResource(R.string.pick_date),
            size = ButtonSize.LARGE,
            type = ButtonType.SECONDARY,
            onClick = { onDateClick() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Address(
    address: AddressData,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Paragraph(
                title = address.title,
                fontWeight = FontWeight.Bold,
                type = ParagraphType.LARGE
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_4))
            Paragraph(
                title = address.address,
                type = ParagraphType.MEDIUM,
                color = Neutral500
            )
        }
        RoundedIconButton(
            icon = painterResource(id = R.drawable.ic_outline_pen),
            type = ButtonType.SECONDARY,
            size = ButtonSize.MEDIUM,
            onClick = { onEditClick() },
        )
    }
}

@Composable
fun PriceDetail(
    total: Int,
    deliveryFee: Int,
    finalPrice: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Paragraph(
            title = stringResource(R.string.detail),
            type = ParagraphType.LARGE,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Paragraph(
                title = stringResource(R.string.total),
                type = ParagraphType.MEDIUM,
            )
            Paragraph(
                title = total.toString(),
                type = ParagraphType.MEDIUM,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Paragraph(
                title = stringResource(R.string.delivery),
                type = ParagraphType.MEDIUM,
            )
            Paragraph(
                title = deliveryFee.toString(),
                type = ParagraphType.MEDIUM,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Paragraph(
                title = stringResource(R.string.total),
                type = ParagraphType.LARGE,
                fontWeight = FontWeight.Bold
            )
            Paragraph(
                title = finalPrice.toString(),
                type = ParagraphType.LARGE,
                fontWeight = FontWeight.Bold,
                color = Primary400,
            )
        }
    }
}


@Composable
fun PaymentMethod(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Paragraph(
            title = stringResource(R.string.payment_method),
            type = ParagraphType.LARGE,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        PaymentButton(leadingImage = painterResource(id = R.drawable.ovo), onClick = { /*TODO*/ })
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
                title = stringResource(id = R.string.connection_error),
                type = HeadingType.H5,
                textAlign = TextAlign.Center,
                color = Neutral500,
            )
        }
    }
}

@Composable
private fun Divider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = AppTheme.dimens.spacing_24)
            .fillMaxWidth()
            .height(AppTheme.dimens.spacing_2)
            .background(color = Neutral100)
    ) {}
}

@Composable
private fun BottomBar(
    transactionProcess: Boolean,
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
                title = stringResource(R.string.pay),
                size = ButtonSize.LARGE,
                onClick = { onRentClick() },
                state = if (transactionProcess) ButtonState.LOADING else ButtonState.ACTIVE,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun CheckOutPreview() {
    RentalBizTheme {
        CheckOutContent(
            uiState = UiState.Loading,
            address = AddressData("", ""),
            totalItemPrice = 10000,
            selectedDateRange = Range(LocalDate.now(), LocalDate.now()),
            onDateClick = {},
            onAddressEditClick = {},
            deliveryFee = 0,
            onItemClick = {},
            deleteCartItem = {}
        )
    }
}