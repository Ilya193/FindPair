package ru.kraz.findpair.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kraz.findpair.domain.GameDomain

@Entity(tableName = "games")
data class GameDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val timespent: Int,
    val profit: Int
) {
    fun toGameDomain(): GameDomain =
        GameDomain(id, date, timespent, profit)
}