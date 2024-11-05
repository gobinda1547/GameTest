package com.gobinda.gametest.nav

sealed class AppScreenDetails(val route: String) {
    object HomeScreen : AppScreenDetails("home")
}