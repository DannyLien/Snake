package com.hank.snake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer

class SnakeViewModel : ViewModel() {
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    var score = MutableLiveData<Int>()

    val gameState = MutableLiveData<GameState>()

    private val snakeBody = mutableListOf<Position>()
    private var direction = Direction.LEFT
    fun start() {
        snakeBody.apply {
            add(Position(10, 10))
            add(Position(11, 10))
            add(Position(12, 10))
            add(Position(13, 10))
        }.also {
            body.value = it
        }
        fixedRateTimer("timer", true, 500, 500) {
            val pos = snakeBody.first().copy().apply {
                when (direction) {
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                    Direction.TOP -> y--
                    Direction.DOWN -> y++
                }
                if (x < 0 || x >= 20 || y < 0 || y >= 20) {
                    cancel()
                    gameState.postValue(GameState.GAME_OVER)
                }

            }
            snakeBody.add(0, pos)
            snakeBody.removeLast()
            body.postValue(snakeBody)
        }
    }

    fun reset() {}
    fun move(dir: Direction) {
        direction = dir
    }

}

data class Position(var x: Int, var y: Int)

enum class Direction {
    TOP, DOWN, LEFT, RIGHT
}

enum class GameState {
    ONGOING, GAME_OVER
}
