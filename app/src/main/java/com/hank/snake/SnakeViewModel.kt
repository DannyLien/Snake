package com.hank.snake

import android.graphics.PostProcessor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

class SnakeViewModel : ViewModel() {
    private lateinit var applePos: Position
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    var score = MutableLiveData<Int>()
    val gameState = MutableLiveData<GameState>()
    private val snakeBody = mutableListOf<Position>()
    private var direction = Direction.LEFT
    private var point: Int = 0

    fun start() {
        score.postValue(point)
        snakeBody.apply {
            add(Position(10, 10))
            add(Position(11, 10))
            add(Position(12, 10))
            add(Position(13, 10))
        }.also {
            body.value = it
        }
        generateApple()
        fixedRateTimer("timer", true, 500, 500) {
            val pos = snakeBody.first().copy().apply {
                when (direction) {
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                    Direction.TOP -> y--
                    Direction.DOWN -> y++
                }
                if (snakeBody.contains(this) || x < 0 || x >= 20 || y < 0 || y >= 20) {
                    cancel()
                    gameState.postValue(GameState.GAME_OVER)
                }

            }
            snakeBody.add(0, pos)
            if (pos != applePos) {
                snakeBody.removeLast()
            } else {
                point += 100
                score.postValue(point)
                generateApple()
            }
            body.postValue(snakeBody)
        }
    }

    fun generateApple() {
//        applePos = Position(
//            Random.nextInt(20),
//            Random.nextInt(20)
//        )
        val spots = mutableListOf<Position>().apply {
            for (i in 0..19) {
                for (j in 19 downTo 0 step 1) {
                    add(Position(j, i))
                }
            }
        }
        spots.removeAll(snakeBody)
        spots.shuffled()
        applePos = spots[0]
        apple.postValue(applePos)
    }

    fun reset() {
        point = 0
        snakeBody.clear()
        start()

    }

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
