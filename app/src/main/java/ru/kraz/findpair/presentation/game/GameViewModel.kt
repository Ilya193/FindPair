package ru.kraz.findpair.presentation.game

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.findpair.domain.GameDomain
import ru.kraz.findpair.domain.Repository
import ru.kraz.findpair.presentation.common.EventWrapper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask


class GameViewModel(
    private val repository: Repository,
) : ViewModel() {
    private val list = mutableListOf<ItemUi>()
    private val _items = MutableLiveData<List<ItemUi>>()

    private val sdf = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
    val items: LiveData<List<ItemUi>> get() = _items

    private var timer = Timer()
    private var sec = 0
    private var coinsWon = 100
    private val _gameState = MutableLiveData<EventWrapper<GameUiState>>()
    val gameState: LiveData<EventWrapper<GameUiState>> get() = _gameState

    private fun saveData(coins: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val date = sdf.format(Date())
            repository.saveData(GameDomain(date = date, timespent = sec, profit = coinsWon))
            _gameState.postValue(EventWrapper.Single(GameUiState.Finish(coins)))
        }
    }

    private fun initTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                var showTime = ""
                val pickUpMoney = if (sec > 20 && coinsWon > 10) {
                    coinsWon -= 5
                    true
                } else false
                if (sec < 10) showTime = "00:0$sec"
                else if (sec in 10..59) showTime = "00:$sec"
                else {
                    val min = sec / 60
                    val newSec = sec % 60
                    if (min < 10) {
                        if (newSec < 10) showTime = "0$min:0$newSec"
                        else if (newSec in 10..59) showTime = "0$min:$newSec"
                    } else {
                        if (newSec < 10) showTime = "$min:0$newSec"
                        else if (newSec in 10..59) showTime = "$min:$newSec"
                    }
                }
                if (sec == 3600) {
                    saveData(1)
                    timer.cancel()
                } else {
                    _gameState.postValue(
                        EventWrapper.Single(
                            GameUiState.Tick(
                                sec,
                                showTime,
                                coinsWon,
                                pickUpMoney
                            )
                        )
                    )
                    sec++
                }
            }
        }, 0, 1000)
    }

    fun initTimer(timer: Int) {
        sec = timer
        initTimer()
    }

    fun cancelTimer() {
        timer.cancel()
    }

    fun initGame() {
        initTimer()
        list.addAll(
            listOf(
                ItemUi.Fifth(pair = 8),
                ItemUi.Tenth(pair = 19),
                ItemUi.First(pair = 14),
                ItemUi.Second(pair = 10),
                ItemUi.Third(pair = 17),
                ItemUi.Fourth(pair = 7),
                ItemUi.Seventh(pair = 12),
                ItemUi.Fourth(pair = 5),
                ItemUi.Fifth(pair = 0),
                ItemUi.Sixth(pair = 11),
                ItemUi.Second(pair = 3),
                ItemUi.Sixth(pair = 9),
                ItemUi.Seventh(pair = 6),
                ItemUi.Eighth(pair = 15),
                ItemUi.First(pair = 2),
                ItemUi.Eighth(pair = 13),
                ItemUi.Ninth(pair = 18),
                ItemUi.Third(pair = 4),
                ItemUi.Ninth(pair = 16),
                ItemUi.Tenth(pair = 1),
            )
        )
        _items.value = list.toList()
    }

    fun setVisible(index: Int, itemUi: ItemUi) = viewModelScope.launch(Dispatchers.IO) {
        itemUi.visible(list, index)
        if (list.all { it.visible == View.VISIBLE}) {
            saveData(coinsWon)
            timer.cancel()
        }
        else {
            _items.postValue(list.toList())
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    //val visibleItems = list.filter { it.visible == View.VISIBLE }
                    //val result = hasDuplicates(visibleItems, itemUi)
                    val result = list[index].checkStatePair(list)
                    if (!result) {
                        itemUi.visible(list, index, View.INVISIBLE)
                        _items.postValue(list.toList())
                    }
                }
            }, 1500)
        }
    }

    private fun hasDuplicates(list: List<ItemUi>, item: ItemUi): Boolean {
        var result = false
        var count = 0
        for (it in list) {
            if (it.value == item.value) {
                count++
                if (count == 2) {
                    result = true
                    break
                }
            }
        }
        return result
    }
}

