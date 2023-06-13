package com.bangkit.rentalbiz.ui.screen.checkout

import androidx.compose.foundation.Image
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
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.HeadingType
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme
import com.bangkit.rentalbiz.ui.theme.Shades90

@Composable
fun SuccessConfirmationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(AppTheme.dimens.spacing_24)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.grape_illustration_10),
            contentDescription = stringResource(
                R.string.illustration_image
            ),
            contentScale = ContentScale.Fit,
            modifier = modifier
                .heightIn(320.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Heading(
            title = stringResource(R.string.yay),
            type = HeadingType.H2,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 280.dp)
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
        Paragraph(
            title = stringResource(R.string.checkout_confirmed),
            type = ParagraphType.MEDIUM,
            fontWeight = FontWeight.Medium,
            color = Shades90,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_36))
        MyButton(
            title = stringResource(R.string.proceed),
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.SuccessCheckout.route) { inclusive = true }
                }
            },
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun SuccessConfirmationScreen() {
    RentalBizTheme {
        SuccessConfirmationScreen()
    }
}