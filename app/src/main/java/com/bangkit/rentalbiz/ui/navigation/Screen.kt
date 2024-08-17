package com.bangkit.rentalbiz.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {

    object Splash : Screen("splash_page")
    object OnBoarding : Screen("onboarding_page")
    object Login : Screen("login_page")
    object Register : Screen("register_page")
    object Greeting : Screen("greeting_page")

    object RecommendationInput : Screen("recommendation_page")
    object RecommendationResult : Screen("recommendation_page/{category}/{fund}") {
        fun createRoute(category: String, fund: Int) = "recommendation_page/$category/$fund"
    }

    object Home : Screen("home_page")
    object DetailProduct : Screen("home_page/{productId}") {
        fun createRoute(productId: String) = "home_page/$productId"
    }

    object Cart : Screen("cart_page")
    object History : Screen("history_page")
    object Favorite : Screen("favorite_page")
    object Profile : Screen("profile_page")
    object CheckOut : Screen("checkout_page")
    object SuccessCheckout : Screen("success_page")

    object Inventory : Screen("inventory_page")
    object CreateProduct : Screen("create_product_page")
    object ManageProduct : Screen("inventory_page/{productId}") {
        fun createRoute(productId: String) = "inventory_page/$productId"
    }

    object Camera : Screen("camera_page/{productId}") {
        fun createRoute(productId: String) = "camera_page/$productId"
    }

    object Auth : Screen("auth")
}