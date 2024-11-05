package com.gobinda.gametest.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gobinda.gametest.screen.home.HomeScreen
import com.gobinda.gametest.ui.theme.GameTestTheme

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    GameTestTheme {
        NavHost(
            navController = navController,
            startDestination = AppScreenDetails.HomeScreen.route
        ) {
            composable(route = AppScreenDetails.HomeScreen.route) {
                HomeScreen(navController)
            }
        }
    }
}