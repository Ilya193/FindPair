package ru.kraz.findpair.data

import ru.kraz.findpair.domain.GameDomain
import ru.kraz.findpair.domain.Mapper

class ToGameDbMapper : Mapper<GameDomain, GameDb> {
    override fun map(data: GameDomain): GameDb =
        GameDb(id = data.id, date = data.date, timespent = data.timespent, profit = data.profit)
}