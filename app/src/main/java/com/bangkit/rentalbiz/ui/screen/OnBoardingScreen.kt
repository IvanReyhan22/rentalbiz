package com.bangkit.rentalbiz.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Primary400
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.ui.theme.Shades90

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
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
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Heading(
                title = stringResource(R.string.onboarding_headline),
                type = HeadingType.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 280.dp)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            Image(
                painter = painterResource(id = R.drawable.grape_illustration_8),
                contentDescription = stringResource(
                    R.string.illustration_image
                ),
                contentScale = ContentScale.Fit,
                modifier = modifier.heightIn(320.dp)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
            Paragraph(
                title = stringResource(R.string.onboarding_sub_heading),
                type = ParagraphType.MEDIUM,
                fontWeight = FontWeight.Medium,
                color = Shades90,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(modifier = Modifier) {
            MyButton(
                title = stringResource(R.string.login),
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(Screen.Login.route) })
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            RegisterObject(
                onClick = {
                    navController.navigate(Screen.Register.route)
                }
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
fun OnBoardingScreenPreview() {
    RentalBizTheme {
        OnBoardingScreen(navController = rememberNavController())
    }
}