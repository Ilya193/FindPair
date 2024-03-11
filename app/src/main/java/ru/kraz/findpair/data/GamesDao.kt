package ru.kraz.findpair.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GamesDao {
    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGame(game: GameDb)
}