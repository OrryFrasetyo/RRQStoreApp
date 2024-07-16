package com.orryfrasetyo.rrqstoreapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailShop : Screen("home/{shopId}") {
        fun createRoute(priceId: Long) = "home/$priceId"
    }
}