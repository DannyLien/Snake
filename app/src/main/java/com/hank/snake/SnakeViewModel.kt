package com.hank.snake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SnakeViewModel : ViewModel() {
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    var score = MutableLiveData<Int>()

    fun start() {}
    fun reset() {}
    fun move(dir: Direction) {}

}

data class Position(var x: Int, var y: Int)

enum class Direction {
    TOP, DOWN, LEFT, RIGHT
}