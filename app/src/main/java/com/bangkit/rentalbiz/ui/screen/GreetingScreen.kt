package com.bangkit.rentalbiz.ui.screen

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
import androidx.navigation.compose.rememberNavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.ButtonType
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
fun GreetingScreen(
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
            Image(
                painter = painterResource(id = R.drawable.grape_illustration_8),
                contentDescription = stringResource(
                    R.string.illustration_image
                ),
                contentScale = ContentScale.Fit,
                modifier = modifier.heightIn(320.dp)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
            Heading(
                title = stringResource(R.string.app_tagline_2),
                type = HeadingType.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 280.dp)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
            Paragraph(
                title = stringResource(R.string.rent_recomendation_subtitle),
                type = ParagraphType.MEDIUM,
                fontWeight = FontWeight.Medium,
                color = Shades90,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(modifier = Modifier) {
            MyButton(
                title = stringResource(R.string.later_on),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                type = ButtonType.SECONDARY,
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
            )
            Spacer(modifier = Modifier.width(AppTheme.dimens.spacing_24))
            MyButton(
                title = stringResource(R.string.proceed),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                onClick = {
                    navController.navigate(Screen.RecommendationInput.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun GreetingScreenPreview() {
    RentalBizTheme {
        GreetingScreen(navController = rememberNavController())
    }
}