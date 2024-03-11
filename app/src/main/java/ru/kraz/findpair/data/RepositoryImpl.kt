package ru.kraz.findpair.data

import ru.kraz.findpair.domain.GameDomain
import ru.kraz.findpair.domain.Repository

class RepositoryImpl(
    private val dao: GamesDao,
    private val mapper: ToGameDbMapper
) : Repository {
    override suspend fun saveData(game: GameDomain) = dao.addGame(mapper.map(game))
    override suspend fun getAllGames(): List<GameDomain> = dao.getAllGames().map { it.toGameDomain() }
}