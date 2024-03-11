package ru.kraz.findpair.presentation

import ru.kraz.findpair.domain.GameDomain
import ru.kraz.findpair.domain.Mapper

class ToGameUiMapper : Mapper<GameDomain, GameUi> {
    override fun map(data: GameDomain): GameUi =
        GameUi(data.id, data.date, data.timespent, data.profit)
}