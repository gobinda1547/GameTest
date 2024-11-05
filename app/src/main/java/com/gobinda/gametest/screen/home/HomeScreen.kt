package com.gobinda.gametest.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gobinda.gametest.views.SimpleBox

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val screenWidthInDp = LocalConfiguration.current.screenWidthDp
    val screenHeightInDp = LocalConfiguration.current.screenHeightDp
    LaunchedEffect(screenWidthInDp, screenHeightInDp) {
        viewModel.setScreenWidthHeight(screenWidthInDp, screenHeightInDp)
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        for (item in state.value) {
            key(item) {
                HomeScreenContent(
                    x = item.x,
                    y = item.y,
                    color = item.color
                )
            }
        }
    }
}

@Composable
private fun HomeScreenContent(x: Int, y: Int, color: Color) {
    SimpleBox(x.dp, y.dp, color)
}