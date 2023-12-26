package ru.kraz.findpair

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class MainViewModel : ViewModel() {
    private val list = mutableListOf<ItemUi>()
    private val _items = MutableLiveData<List<ItemUi>>()
    val items: LiveData<List<ItemUi>> get() = _items

    private var timer = Timer()
    private var sec = 0
    private var moneyReceived = 100
    private val _game = MutableLiveData<EventWrapper<GameUi>>()
    val game: LiveData<EventWrapper<GameUi>> get() = _game

    private var _coins = 0
    private val _liveDataCoins = MutableLiveData<Int>()
    val coins: LiveData<Int> get() = _liveDataCoins

    fun initGame() {
        list.clear()
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (sec > 20 && moneyReceived > 10) moneyReceived -= 5
                if (sec < 10) _game.postValue(EventWrapper.Single(GameUi.Tick("00:0$sec", moneyReceived.toString())))
                else if (sec in 10..59) _game.postValue(EventWrapper.Single(GameUi.Tick("00:$sec", moneyReceived.toString())))
                else {
                    val min = sec / 60
                    val newSec = sec % 60
                    if (min < 10) {
                        if (newSec < 10) _game.postValue(EventWrapper.Single(GameUi.Tick("0$min:0$newSec", moneyReceived.toString())))
                        else if (newSec in 10..59) _game.postValue(EventWrapper.Single(GameUi.Tick("0$min:$newSec", moneyReceived.toString())))
                    }
                    else {
                        if (newSec < 10) _game.postValue(EventWrapper.Single(GameUi.Tick("$min:0$newSec", moneyReceived.toString())))
                        else if (newSec in 10..59) _game.postValue(EventWrapper.Single(GameUi.Tick("$min:$newSec", moneyReceived.toString())))
                    }
                }
                sec++
            }
        }, 0, 1000)

        list.addAll(
            listOf(
                ItemUi.Fifth(),
                ItemUi.Tenth(),
                ItemUi.First(),
                ItemUi.Second(),
                ItemUi.Third(),
                ItemUi.Fourth(),
                ItemUi.Seventh(),
                ItemUi.Fourth(),
                ItemUi.Fifth(),
                ItemUi.Sixth(),
                ItemUi.Second(),
                ItemUi.Sixth(),
                ItemUi.Seventh(),
                ItemUi.Eighth(),
                ItemUi.First(),
                ItemUi.Eighth(),
                ItemUi.Ninth(),
                ItemUi.Third(),
                ItemUi.Ninth(),
                ItemUi.Tenth(),
            )
        )
        _items.value = list.toList()
    }

    fun init() {
        _liveDataCoins.value = _coins
    }

    fun setVisible(index: Int, itemUi: ItemUi) = viewModelScope.launch(Dispatchers.IO) {
        itemUi.visible(list, index)
        val pairsFound = list.all {
            it.visible == View.VISIBLE
        }
        if (pairsFound) {
            timer.cancel()
            _game.postValue(EventWrapper.Single(GameUi.Finish(moneyReceived.toString())))
            _coins += moneyReceived
            moneyReceived = 100
            sec = 0
        }
        else {
            _items.postValue(list.toList())
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    val visibleItems = list.filter { it.visible == View.VISIBLE }
                    val result = hasDuplicates(visibleItems, itemUi)
                    if (!result) {
                        itemUi.visible(list, index, View.INVISIBLE)
                        _items.postValue(list.toList())
                    }
                }
            }, 1500)
        }
    }

    private fun hasDuplicates(list: List<ItemUi>, item: ItemUi): Boolean {
        var result = 0
        var count = 0
        list.forEach {
            if (it.value == item.value) {
                count++
                if (count == 2) result++
            }
        }
        return result > 0
    }
}

