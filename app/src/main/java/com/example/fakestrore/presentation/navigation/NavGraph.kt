package com.example.fakestrore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fakestrore.presentation.screens.CartScreen
import com.example.fakestrore.presentation.screens.ProductScreen
import com.example.fakestrore.presentation.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Route.PRODUCT) {
        composable(Route.SPLASH) { SplashScreen(navController) }
        composable(Route.PRODUCT) { ProductScreen(navController) }
        composable(Route.CART){ CartScreen(navController) }
    }
}

data object Route {
    const val SPLASH = "splash"
    const val PRODUCT = "product"
    const val CART = "cart"
}