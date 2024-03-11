package ru.kraz.findpair

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.kraz.findpair.data.GamesDao
import ru.kraz.findpair.data.GamesDb
import ru.kraz.findpair.data.RepositoryImpl
import ru.kraz.findpair.data.ToGameDbMapper
import ru.kraz.findpair.domain.Repository
import ru.kraz.findpair.presentation.GameViewModel
import ru.kraz.findpair.presentation.StatisticsViewModel
import ru.kraz.findpair.presentation.ToGameUiMapper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel<GameViewModel> {
        GameViewModel(get())
    }

    viewModel<StatisticsViewModel> {
        StatisticsViewModel(get(), get())
    }

    single<Repository> {
        RepositoryImpl(get(), get())
    }

    single<GamesDao> {
        Room.databaseBuilder(get(), GamesDb::class.java, "games_db").build().gamesDao()
    }

    factory<ToGameDbMapper> {
        ToGameDbMapper()
    }

    factory<ToGameUiMapper> {
        ToGameUiMapper()
    }
}