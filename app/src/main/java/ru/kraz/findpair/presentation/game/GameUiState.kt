package ru.kraz.findpair.presentation.game

sealed interface GameUiState {
    data class Tick(val sec: Int, val time: String, val money: Int): GameUiState
    data class Finish(val money: Int): GameUiState
}