package ru.kraz.findpair.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameDb::class], version = 1)
abstract class GamesDb : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
}