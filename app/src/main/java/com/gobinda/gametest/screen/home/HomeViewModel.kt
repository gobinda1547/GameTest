package com.gobinda.gametest.screen.home

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private var screenWidthInDp: Int = 0
    private var screenHeightInDp: Int = 0

    private val _state = MutableStateFlow<List<DiceObj>>(emptyList())
    val state: Flow<List<DiceObj>> = callbackFlow {
        Log.i("Gopal", "started flow collecting")

        diceMovingJob?.cancel()
        diceMovingJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(1000 / 10)
                Log.i("Gopal", "Updating")
                _state.update { previousList ->
                    previousList.map {
                        moveDice(it)
                    }
                }
            }
        }

        val collector = viewModelScope.launch {
            _state.collect {
                trySend(it)
            }
        }
        awaitClose {
            collector.cancel()
            diceMovingJob?.cancel()
            Log.i("Gopal", "trying to close the dice moving job")
        }
    }

    private var diceMovingJob: Job? = null

    fun setScreenWidthHeight(width: Int, height: Int) {
        Log.i("Gopal", "invoked at last")
        screenWidthInDp = width
        screenHeightInDp = height

        val myList = mutableListOf<DiceObj>()
        repeat(50) {
            myList.add(
                DiceObj(
                    x = Random.nextInt(width - 50) + 1,
                    y = Random.nextInt(height - 50) + 1,
                    xMovingRight = Random.nextInt(10) < 5,
                    yMovingDown = Random.nextInt(10) < 5,
                    color = when (Random.nextInt(5)) {
                        0 -> Color.Red
                        1 -> Color.Magenta
                        2 -> Color.Green
                        3 -> Color.Blue
                        else -> Color.DarkGray
                    }
                )
            )
        }
        _state.update { myList }
    }

    private fun moveDice(dice: DiceObj): DiceObj {
        val currentX = dice.x + (if (dice.xMovingRight) 1 else -1)
        val currentY = dice.y + (if (dice.yMovingDown) 1 else -1)
        val xDirectionCng = currentX < 0 || screenWidthInDp - 50 < currentX
        val yDirectionCng = currentY < 0 || screenHeightInDp - 50 < currentY
        return dice.copy(
            x = currentX,
            y = currentY,
            xMovingRight = if (xDirectionCng) !dice.xMovingRight else dice.xMovingRight,
            yMovingDown = if (yDirectionCng) !dice.yMovingDown else dice.yMovingDown
        )
    }
}