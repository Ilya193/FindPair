package ru.kraz.findpair

sealed interface GameUi {
    data class Tick(val sec: Int, val time: String, val money: Int): GameUi
    data class Finish(val money: String): GameUi
}