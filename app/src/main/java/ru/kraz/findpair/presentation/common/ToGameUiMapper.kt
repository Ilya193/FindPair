package ru.kraz.findpair.presentation.common

import ru.kraz.findpair.domain.GameDomain
import ru.kraz.findpair.domain.Mapper
import ru.kraz.findpair.presentation.statistics.GameUi

class ToGameUiMapper : Mapper<GameDomain, GameUi> {
    override fun map(data: GameDomain): GameUi =
        GameUi(data.id, data.date, data.timespent, data.profit)
}