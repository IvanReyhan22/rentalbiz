package com.bangkit.rentalbiz.ui.screen.login


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val loginForm by viewModel.loginForm
    val uiState by viewModel.uiState.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn

    if (isLoggedIn) {
        navController.navigate(Screen.Home.route) {
            popUpTo(navController.graph.id) { inclusive = true }
        }
    } else {
        LoginContent(
            modifier = modifier,
            uiState = uiState,
            loginForm = loginForm,
            onEmailUpdate = { viewModel.updateForm(email = it) },
            onPasswordUpdate = { viewModel.updateForm(password = it) },
            onLoginClick = {
                viewModel.login(
                    onSignInComplete = {
                        navController.navigate(Screen.Greeting.route)
                    },
                )
            },
            onRegisterClick = {
                navController.navigate(Screen.Register.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            },
        )
    }


}

@Composable
fun LoginContent(
    loginForm: LoginForm,
    onEmailUpdate: (String) -> Unit,
    onPasswordUpdate: (String) -> Unit,
    uiState: UiState<Any> = UiState.Idle,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(AppTheme.dimens.spacing_24)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.logo_dark),
                contentDescription = stringResource(R.string.rental_biz_logo),
                contentScale = ContentScale.Fit,
                modifier = modifier.height(30.dp)
            )
        }
        ScreenHeading(
            title = stringResource(R.string.welcome),
            subTitle = stringResource(R.string.app_tagline)
        )
        Column {
            MyTextField(
                label = "Email",
                value = loginForm.email,
                onValueChange = { onEmailUpdate(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
            MyTextField(
                label = "Password",
                isPassword = true,
                value = loginForm.password,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        Column(modifier = Modifier) {
            MyButton(
                title = stringResource(R.string.login),
                modifier = Modifier.fillMaxWidth(),
                state = if (uiState == UiState.Loading) ButtonState.LOADING else ButtonState.ACTIVE,
                onClick = { onLoginClick() }
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            RegisterObject(
                onClick = { onRegisterClick() }
            )
        }
    }
}

@Composable
fun RegisterObject(
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
            title = stringResource(R.string.does_not_have_account),
            type = ParagraphType.MEDIUM,
        )
        Spacer(modifier = modifier.width(AppTheme.dimens.spacing_4))
        Paragraph(
            title = stringResource(R.string.register),
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
fun LoginScreenPreview(
) {
    RentalBizTheme {
        LoginContent(
            loginForm = LoginForm("", ""),
            onEmailUpdate = {},
            onPasswordUpdate = {},
            onLoginClick = { /*TODO*/ },
            onRegisterClick = { /*TODO*/ },
        )
    }
}