package com.gobinda.gametest.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private var screenWidthInDp: Int = 0
    private var screenHeightInDp: Int = 0

    private var xFactor: Int = 1
    private var yFactor: Int = 1

    private val _state = MutableStateFlow<Pair<Int, Int>>(Pair(100, 100))
    val state = _state.asStateFlow()

    private var diceMovingJob: Job? = null

    fun setScreenWidthHeight(width: Int, height: Int) {
        Log.i("Gopal", "invoked at last")
        screenWidthInDp = width
        screenHeightInDp = height

        diceMovingJob?.cancel()
        diceMovingJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(1000/200)
                moveDice()
            }
        }
    }

    private fun moveDice() {
        var currentX = _state.value.first + xFactor
        var currentY = _state.value.second + yFactor
        if (currentX < 0 || screenWidthInDp - 50 < currentX) {
            xFactor *= -1
        }
        if (currentY < 0 || screenHeightInDp - 50 < currentY) {
            yFactor *= -1
        }
        Log.i("Gopal", "$currentX, $currentY")
        _state.update { it.copy(first = currentX, second = currentY) }
    }
}