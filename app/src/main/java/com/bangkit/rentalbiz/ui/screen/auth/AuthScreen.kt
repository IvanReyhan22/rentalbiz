package com.bangkit.rentalbiz.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.components.input.CustomTextField
import com.bangkit.rentalbiz.ui.components.input.MyTextField
import com.bangkit.rentalbiz.ui.screen.login.LoginContent
import com.bangkit.rentalbiz.ui.screen.login.LoginForm
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@Composable
fun AuthScreen (
    modifier: Modifier = Modifier,
    navController: NavController,
){
    AuthContent(
        modifier=modifier
    )
}

@Composable
fun AuthContent(
    modifier:Modifier = Modifier,
){
    form(RegisterForm::class){
        field(fieldClass = RegisterForm::email) {
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = "",
                label = {
                    Text(text = "Email")
                },
                onValueChange = {setField(it)},
            )
        }
        field(fieldClass = RegisterForm::password) {
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = "",
                label = {
                    Text(text = "Email")
                },
                onValueChange = {setField(it)},
            )
        }
        val formResult = this.formState.value
        Button(
            modifier = modifier.width(width=200.dp),
            enabled = formResult is FormResult.Success,
            onClick = { /*TODO*/ }
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Submit Form",
            )
        }
    }
//    Column(
//        modifier = modifier
//            .padding(AppTheme.dimens.spacing_24)
//            .fillMaxSize()
//    ) {
//        CustomTextField(
//            label = "Nama",
//            value = "",
//            onValueChange = {  },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
//        CustomTextField(
//            label = "Email",
//            value = "",
//            onValueChange = {  },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_24))
//
//    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun AuthScreenPreview(
) {
    RentalBizTheme {
        AuthContent()
    }
}