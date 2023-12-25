package ru.kraz.findpair

sealed interface GameUi {
    data class Tick(val time: String, val money: String): GameUi
    data class Finish(val money: String): GameUi
}