package com.bangkit.rentalbiz.ui.navigation

import com.bangkit.rentalbiz.data.remote.response.Product

sealed class Screen(val route: String) {

    object Splash : Screen("splash_page")
    object OnBoarding : Screen("onboarding_page")
    object Login : Screen("login_page")
    object Register : Screen("register_page")
    object Greeting : Screen("greeting_page")

    object Recommendation : Screen("recommendation_page")

    object Home : Screen("home_page")
    object DetailProduct : Screen("home_page/{productId}") {
        fun createRoute(productId: String) = "home_page/$productId"
    }

    object Cart : Screen("cart_page")
    object History : Screen("history_page")
    object Favorite : Screen("favorite_page")
    object Profile : Screen("profile_page")
}