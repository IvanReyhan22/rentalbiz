package com.bangkit.rentalbiz.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.ui.components.ScreenHeading
import com.bangkit.rentalbiz.ui.components.button.ButtonState
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Error400
import com.bangkit.rentalbiz.ui.theme.Primary400
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val registerForm by viewModel.registerForm
    val uiState by viewModel.uiState.collectAsState()

    RegisterContent(
        modifier = modifier,
        registerForm = registerForm,
        uiState = uiState,
        onNameUpdate = { viewModel.updateForm(name = it) },
        onPhoneUpdate = { viewModel.updateForm(phoneNumber = it) },
        onEmailUpdate = { viewModel.updateForm(email = it) },
        onPasswordUpdate = { viewModel.updateForm(password = it) },
        onCityUpdate = { viewModel.updateForm(city = it) },
        onAddressUpdate = { viewModel.updateForm(address = it) },
        onRegisterClick = {
            viewModel.register(onRegisterComplete = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            })
        },
        onLoginClick = { navController.popBackStack() },
    )

}

@Composable
fun RegisterContent(
    registerForm: RegisterForm,
    uiState: UiState<Any> = UiState.Idle,
    onNameUpdate: (String) -> Unit,
    onPhoneUpdate: (String) -> Unit,
    onEmailUpdate: (String) -> Unit,
    onPasswordUpdate: (String) -> Unit,
    onCityUpdate: (String) -> Unit,
    onAddressUpdate: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(
            start = AppTheme.dimens.spacing_24,
            top = AppTheme.dimens.spacing_24,
            end = AppTheme.dimens.spacing_24,
            bottom = AppTheme.dimens.spacing_24
        )
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.logo_dark),
                    contentDescription = stringResource(R.string.rental_biz_logo),
                    contentScale = ContentScale.Fit,
                    modifier = modifier.height(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_32))
            ScreenHeading(
                title = stringResource(R.string.welcome_aboard),
                subTitle = stringResource(R.string.app_tagline)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_36))
            Column {
                MyTextField(
                    label = stringResource(R.string.name),
                    value = registerForm.name,
                    onValueChange = { onNameUpdate(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    label = stringResource(R.string.phone_number),
                    value = registerForm.phoneNumber,
                    keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number),
                    onValueChange = { onPhoneUpdate(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    label = stringResource(R.string.address),
                    value = registerForm.address,
                    onValueChange = { onAddressUpdate(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    label = stringResource(R.string.city),
                    value = registerForm.city,
                    onValueChange = { onCityUpdate(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    label = stringResource(R.string.Email),
                    value = registerForm.email,
                    keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Email),
                    onValueChange = { onEmailUpdate(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyTextField(
                    label = stringResource(R.string.password),
                    value = registerForm.password,
                    isPassword = true,
                    onValueChange = { onPasswordUpdate(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                when (uiState) {
                    is UiState.Error -> {
                        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                        Paragraph(
                            title = uiState.errorMessage,
                            type = ParagraphType.MEDIUM,
                            fontWeight = FontWeight.Normal,
                            color = Error400
                        )
                    }
                    else -> {}
                }
            }
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_32))
            Column(modifier = Modifier) {
                MyButton(
                    title = stringResource(R.string.register),
                    state = if (uiState == UiState.Loading) ButtonState.LOADING else ButtonState.ACTIVE,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onRegisterClick() }
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                LoginObject(
                    onClick = { onLoginClick() }
                )
            }
        }
    }
}

@Composable
fun LoginObject(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Paragraph(
            title = stringResource(R.string.already_have_account),
            type = ParagraphType.MEDIUM,
        )
        Spacer(modifier = modifier.width(AppTheme.dimens.spacing_4))
        Paragraph(
            title = stringResource(R.string.login),
            type = ParagraphType.MEDIUM,
            fontWeight = FontWeight.Medium,
            color = Primary400,
            modifier = Modifier
                .clickable { onClick() }
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun RegisterScreenPreview() {
    RentalBizTheme {
        RegisterContent(
            registerForm = RegisterForm("", "", "", "", "", ""),
            onNameUpdate = {},
            onPhoneUpdate = {},
            onEmailUpdate = {},
            onPasswordUpdate = {},
            onCityUpdate = {},
            onAddressUpdate = {},
            onRegisterClick = { /*TODO*/ },
            onLoginClick = { /*TODO*/ },
        )
    }
}