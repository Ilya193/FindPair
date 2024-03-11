package ru.kraz.findpair.domain

interface Repository {
    suspend fun saveData(game: GameDomain)
    suspend fun getAllGames(): List<GameDomain>
}