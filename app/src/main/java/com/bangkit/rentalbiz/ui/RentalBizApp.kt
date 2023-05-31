package com.bangkit.rentalbiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.ui.common.ParagraphType
import com.bangkit.rentalbiz.ui.components.text.Paragraph
import com.bangkit.rentalbiz.ui.navigation.NavigationItem
import com.bangkit.rentalbiz.ui.navigation.Screen
import com.bangkit.rentalbiz.ui.screen.Favorite.FavoriteScreen
import com.bangkit.rentalbiz.ui.screen.GreetingScreen
import com.bangkit.rentalbiz.ui.screen.OnBoardingScreen
import com.bangkit.rentalbiz.ui.screen.detail.DetailProductScreen
import com.bangkit.rentalbiz.ui.screen.history.HistoryScreen
import com.bangkit.rentalbiz.ui.screen.home.HomeScreen
import com.bangkit.rentalbiz.ui.screen.login.LoginScreen
import com.bangkit.rentalbiz.ui.screen.profile.ProfileScreen
import com.bangkit.rentalbiz.ui.screen.register.RegisterScreen
import com.bangkit.rentalbiz.ui.theme.Neutral500
import com.bangkit.rentalbiz.ui.theme.Neutral600
import com.bangkit.rentalbiz.ui.theme.Primary400
import com.bangkit.rentalbiz.ui.theme.Shades0


@Composable
fun RentalBizApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavScreen = listOf(
        Screen.Home.route,
        Screen.History.route,
        Screen.Favorite.route,
        Screen.Profile.route
    )
    Scaffold(
        bottomBar = {
            if (bottomNavScreen.contains(currentRoute)) BottomBar(navController)
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.OnBoarding.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.OnBoarding.route) {
                OnBoardingScreen(navController = navController)
            }
            composable(Screen.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(Screen.Register.route) {
                RegisterScreen(navController = navController)
            }
            composable(Screen.Greeting.route) {
                GreetingScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.DetailProduct.route) {
                DetailProductScreen(navController = navController)
            }
            composable(Screen.History.route) {
                HistoryScreen(navController = navController)
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(navController = navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.home_tab),
            icon = if (currentRoute == Screen.Home.route) R.drawable.ic_solid_home_purple else R.drawable.ic_outline_home,
            screen = Screen.Home,
            contentDescription = stringResource(R.string.home_tab)
        ),
        NavigationItem(
            title = stringResource(R.string.history_tab),
            icon = if (currentRoute == Screen.History.route) R.drawable.ic_solid_note_purple else R.drawable.ic_outline_note,
            screen = Screen.History,
            contentDescription = stringResource(R.string.history_tab),
        ),
        NavigationItem(
            title = stringResource(R.string.favorite_tab),
            icon = if (currentRoute == Screen.Favorite.route) R.drawable.ic_solid_heart_purple else R.drawable.ic_outline_heart,
            screen = Screen.Favorite,
            contentDescription = stringResource(R.string.favorite_tab),
        ),
        NavigationItem(
            title = stringResource(R.string.profile_tab),
            icon = if (currentRoute == Screen.Profile.route) R.drawable.ic_solid_user_purple else R.drawable.ic_outline_user,
            screen = Screen.Profile,
            contentDescription = stringResource(R.string.profile_tab)
        ),
    )

    BottomNavigation(modifier = modifier, backgroundColor = Shades0) {
        navigationItems.map { item ->
            BottomNavigationItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.contentDescription,
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = {
                    Paragraph(
                        title = item.title,
                        type = ParagraphType.XSMALL,
                        fontWeight = if (currentRoute == item.screen.route) FontWeight.Medium else FontWeight.Normal,
                        color = if (currentRoute == item.screen.route) Primary400 else Neutral500,
                    )
                },
                selectedContentColor = Primary400,
                unselectedContentColor = Neutral600,
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}