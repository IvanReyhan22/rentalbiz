package com.bangkit.rentalbiz.ui.screen.manage

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.*
import com.bangkit.rentalbiz.ui.components.button.ButtonState
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.button.RoundedIconButton
import com.bangkit.rentalbiz.ui.components.dialog.YesOrNoDialog
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.modal.CategoryBottomSheetModal
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral100
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.ui.theme.Shades90
import com.talhafaki.composablesweettoast.util.SweetToastUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ManageProductScreen(
    productId: String?,
    navController: NavController,
    viewModel: ManageProductScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val inventoryForm by viewModel.inventoryForm
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.Expanded },
    )
    val image by viewModel.image
    var openDialogSuccess by remember { mutableStateOf(false) }
    var openDialogError by remember { mutableStateOf(false) }
    var confirmationDialog by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        if (productId?.isNotEmpty() == true) {
            viewModel.getProduct(productId = productId)
        }
        viewModel.getImage()
        onDispose {}
    }

    Scaffold(topBar = {
        TopBar(
            isDeletable = productId?.isNotEmpty() == true,
            onBackClick = { navController.popBackStack() },
            onDeleteClick = {
                if (productId != null) {
                    confirmationDialog = true
                }
            })
    }) { paddingValues ->
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = MaterialTheme.shapes.large.copy(
                topStart = CornerSize(AppTheme.dimens.radius_24),
                topEnd = CornerSize(AppTheme.dimens.radius_24)
            ),
            sheetContent = {
                CategoryBottomSheetModal(
                    onCategoryChange = {
                        viewModel.updateForm(
                            category = it
                        )
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    },
                )
            },
        ) {
            Box(modifier = modifier.padding(paddingValues)) {
                ManageContent(
                    productId = productId ?: "",
                    image = image,
                    uiState = uiState,
                    inventoryForm = inventoryForm,
                    onNameChange = { viewModel.updateForm(name = it) },
                    onPriceChange = { viewModel.updateForm(price = it) },
                    onStockChange = { viewModel.updateForm(stock = it) },
                    onCategoryClick = {
                        coroutineScope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                            }
                        }
                    },
                    onImageClick = {
                        if (productId?.isNotEmpty() == true) {
                            navController.navigate(Screen.Camera.createRoute(productId))
                        } else {
                            navController.navigate(Screen.Camera.route)
                        }
                    },
                    onSubmit = {
                        if (productId?.isNotEmpty() == true) {
                            viewModel.updateProduct(
                                onSuccess = {
                                    openDialogSuccess = true
                                    navController.popBackStack()
                                },
                                onFailed = {
                                    openDialogError = true
                                },
                            )
                        } else {
                            viewModel.addProduct(
                                onSuccess = {
                                    openDialogSuccess = true
                                    navController.popBackStack()
                                },
                                onFailed = {
                                    openDialogError = true
                                }
                            )
                        }
                    },
                    onDescriptionChange = { viewModel.updateForm(description = it) },
                    onRequirementChange = { viewModel.updateForm(requirement = it) }
                )
            }
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

    if (confirmationDialog) {
        YesOrNoDialog(
            openDialog = { confirmationDialog = true },
            closeDialog = { confirmationDialog = false },
            title = stringResource(R.string.sure_want_to_delete),
            subTitle = stringResource(R.string.permanent_delete),
            image = painterResource(id = R.drawable.ic_outline_warning),
            onYesClick = {
                if (productId != null) {
                    viewModel.deleteProduct(
                        productId = productId,
                        onSuccess = {
                            openDialogSuccess = true
                            navController.popBackStack()
                        },
                        onFailed = {
                            openDialogError = true
                        },
                    )
                } else {
                    confirmationDialog = false
                }
            },
            onNoClick = { confirmationDialog = false },
        )
    }
}

@Composable
fun ManageContent(
    image: Uri,
    productId: String,
    uiState: UiState<Product>,
    inventoryForm: InventoryForm,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onStockChange: (String) -> Unit,
    onCategoryClick: () -> Unit,
    onImageClick: () -> Unit,
    onSubmit: () -> Unit,
    onDescriptionChange: (String) -> Unit,
    onRequirementChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(contentPadding = PaddingValues(AppTheme.dimens.spacing_24), modifier = modifier) {
        item {
            Column {
                if (uiState is UiState.Success) {
                    AsyncImage(
                        model = if (image != Uri.EMPTY) image else ImageRequest.Builder(
                            LocalContext.current
                        ).data(uiState.data.imageUrl).crossfade(true).build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(AppTheme.dimens.radius_12))
                            .clickable { onImageClick() }
                    )
                } else {
                    Image(
                        painter = if (image != Uri.EMPTY) rememberImagePainter(data = image.toString()) else painterResource(
                            id = R.drawable.template_image
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(AppTheme.dimens.radius_12))
                            .clickable { onImageClick() }
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
                MyTextField(
                    value = inventoryForm.name,
                    onValueChange = onNameChange,
                    label = "Nama Produk",
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    value = inventoryForm.price,
                    onValueChange = onPriceChange,
                    label = "Harga Produk",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    value = inventoryForm.stock,
                    onValueChange = onStockChange,
                    label = "Stok Produk",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                Column {
                    Paragraph(
                        title = "Kategori",
                        type = ParagraphType.MEDIUM,
                        fontWeight = FontWeight.Medium,
                        color = Shades90,
                        modifier = modifier.padding(bottom = AppTheme.dimens.spacing_8)
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(Neutral100),
                        elevation = ButtonDefaults.elevation(0.dp),
                        shape = RoundedCornerShape(AppTheme.dimens.radius_12),
                        onClick = { onCategoryClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Paragraph(title = inventoryForm.category, color = Shades90)
                        Box(modifier = Modifier.weight(1F))
                    }
                }
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    value = inventoryForm.description,
                    onValueChange = onDescriptionChange,
                    label = "Deskripsi",
                    isTextArea = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    value = inventoryForm.requirenment,
                    onValueChange = onRequirementChange,
                    label = "Persyaratan",
                    isTextArea = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyButton(
                    title = if (productId == "") "Publish" else "Update",
                    modifier = Modifier.fillMaxWidth(),
                    state = if (uiState == UiState.Loading) ButtonState.LOADING else ButtonState.ACTIVE,
                    onClick = { onSubmit() }
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    isDeletable: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
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
            if (isDeletable) {
                RoundedIconButton(
                    icon = painterResource(id = R.drawable.ic_x),
                    type = ButtonType.SECONDARY,
                    size = ButtonSize.MEDIUM,
                    onClick = { onDeleteClick() })
            } else {
                Box(modifier = Modifier.width(40.dp)) {}
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun ManageProductPreview() {
    RentalBizTheme {
        ManageContent(
            image = Uri.EMPTY,
            productId = "",
            uiState = UiState.Loading,
            inventoryForm = InventoryForm("", "", "", "", "", ""),
            onNameChange = {},
            onPriceChange = {},
            onStockChange = {},
            onCategoryClick = {},
            onImageClick = {},
            onSubmit = {},
            onDescriptionChange = {},
            onRequirementChange = {}
        )
    }
}