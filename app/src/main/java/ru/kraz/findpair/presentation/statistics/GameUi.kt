package ru.kraz.findpair.presentation.statistics

data class GameUi(
    val id: Int,
    val date: String,
    val timespent: Int,
    val profit: Int
)

sealed interface GamesUiState {
    data class Success(val data: List<GameUi>): GamesUiState
    data object Empty : GamesUiState
}