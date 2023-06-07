package com.bangkit.rentalbiz.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.UserCredentials
import com.bangkit.rentalbiz.ui.common.*
import com.bangkit.rentalbiz.ui.components.button.MyButton
import com.bangkit.rentalbiz.ui.components.text.Heading
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.theme.AppTheme
import com.bangkit.rentalbiz.ui.theme.Neutral500
import com.bangkit.rentalbiz.ui.theme.Primary400
import com.bangkit.rentalbiz.ui.theme.RentalBizTheme

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    ProfileContent(
        uiState = uiState,
        onInventoryClick = { /*TODO*/ },
        onCartClick = { navController.navigate(Screen.Cart.route) },
        onFavoriteClick = {
            navController.navigate(Screen.Favorite.route)
        },
        onSignOutClick = {
            viewModel.singOut(onSignOutComplete = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            })
        },
    )

}

@Composable
fun ProfileContent(
    uiState: UiState<UserCredentials> = UiState.Idle,
    onInventoryClick: () -> Unit,
    onCartClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onSignOutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            Surface(elevation = AppTheme.dimens.spacing_2, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = modifier.padding(
                        horizontal = AppTheme.dimens.spacing_24,
                        vertical = AppTheme.dimens.spacing_18
                    )
                ) {
                    Heading(title = stringResource(R.string.my_profile), type = HeadingType.H5)
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = modifier.padding(AppTheme.dimens.spacing_24)) {
                if (uiState is UiState.Success) {
                    uiState.data.let {
                        Profile(
                            name = it.name.toString(),
                            email = it.email.toString()
                        )
                    }
                } else {
                    ProfileLoading()
                }
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_32))
                Paragraph(
                    title = stringResource(R.string.setting),
                    type = ParagraphType.LARGE,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyButton(
                    title = "Inventori",
                    type = ButtonType.SECONDARY,
                    size = ButtonSize.LARGE,
                    isStartAlignment = true,
                    modifier = Modifier.fillMaxWidth(),
                    leadingImage = painterResource(id = R.drawable.ic_outline_cube),
                    onClick = { onInventoryClick() },
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyButton(
                    title = "Keranjang",
                    type = ButtonType.SECONDARY,
                    size = ButtonSize.LARGE,
                    isStartAlignment = true,
                    modifier = Modifier.fillMaxWidth(),
                    leadingImage = painterResource(id = R.drawable.ic_shopping_cart),
                    onClick = { onCartClick() },
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyButton(
                    title = "Favorit",
                    type = ButtonType.SECONDARY,
                    size = ButtonSize.LARGE,
                    isStartAlignment = true,
                    modifier = Modifier.fillMaxWidth(),
                    leadingImage = painterResource(id = R.drawable.ic_outline_heart),
                    onClick = { onFavoriteClick() },
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
                MyButton(
                    title = "Sign Out",
                    type = ButtonType.SECONDARY,
                    size = ButtonSize.LARGE,
                    isStartAlignment = true,
                    modifier = Modifier.fillMaxWidth(),
                    leadingImage = painterResource(id = R.drawable.ic_signout_red),
                    onClick = { onSignOutClick() },
                )
            }
        }
    }
}

@Composable
fun Profile(
    name: String,
    email: String,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.dummy_profile_image),
            contentDescription = stringResource(
                R.string.profile_image
            ),
            modifier = Modifier
                .size(100.dp)
                .clip(
                    RoundedCornerShape(
                        AppTheme.dimens.radius_12
                    )
                )
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_16))
        Heading(title = name, type = HeadingType.H5)
        Spacer(modifier = Modifier.height(AppTheme.dimens.spacing_8))
        Paragraph(
            title = email,
            type = ParagraphType.MEDIUM,
            color = Neutral500
        )
    }
}

@Composable
fun ProfileLoading(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        CircularProgressIndicator(
            color = Primary400,
            strokeWidth = AppTheme.dimens.spacing_4,
            modifier = Modifier.size(AppTheme.dimens.spacing_32)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun ProfileScreen() {
    RentalBizTheme {
        ProfileContent(
            onInventoryClick = { /*TODO*/ },
            onCartClick = { /*TODO*/ },
            onFavoriteClick = { /*TODO*/ },
            onSignOutClick = { /*TODO*/ })
    }
}