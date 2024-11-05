package com.gobinda.gametest.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SimpleBox(xOffset: Dp, yOffset: Dp) {
    Box(
        modifier = Modifier
            .offset(x = xOffset, y = yOffset)
            .size(50.dp)
            .background(Color.Red)
    )
}