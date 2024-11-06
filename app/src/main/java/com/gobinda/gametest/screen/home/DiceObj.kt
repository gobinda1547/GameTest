package com.gobinda.gametest.screen.home

import androidx.compose.ui.graphics.Color

data class DiceObj(
    val x: Int,
    val y: Int,
    val xMovingRight: Boolean,
    val yMovingDown: Boolean,
    val color: Color
)