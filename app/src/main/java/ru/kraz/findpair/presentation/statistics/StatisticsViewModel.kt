package ru.kraz.findpair.presentation.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kraz.findpair.domain.Repository
import ru.kraz.findpair.presentation.common.ToGameUiMapper

class StatisticsViewModel(
    private val repository: Repository,
    private val mapper: ToGameUiMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<GamesUiState>()
    val uiState: LiveData<GamesUiState> get() = _uiState

    init {
        viewModelScope.launch {
            val games = repository.getAllGames().map { mapper.map(it) }
            val state = if (games.isNotEmpty()) GamesUiState.Success(games) else GamesUiState.Empty
            _uiState.value = state
        }
    }
}