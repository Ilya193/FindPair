package ru.kraz.findpair

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.elveum.elementadapter.simpleAdapter
import ru.kraz.findpair.databinding.FragmentGameBinding
import ru.kraz.findpair.databinding.ItemBinding

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    private var cacheTime = 0
    private var cacheMoney = 100

    private val adapter = simpleAdapter<ItemUi, ItemBinding> {
        areContentsSame = { oldItem, newItem ->
            oldItem.visible == newItem.visible
        }

        areContentsSame = { oldItem, newItem ->
            oldItem == newItem
        }

        bind {
            image.setImageResource(it.value)
            image.visibility = it.visible
            root.isClickable = it.visible != View.VISIBLE
        }

        listeners {
            root.onClick {
                viewModel.setVisible(index(), it)
            }
        }
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentGameBinding =
        FragmentGameBinding.inflate(inflater, container, false)

    override fun onResume() {
        super.onResume()
        if (cacheTime != 0)
            viewModel.initGame(cacheTime, cacheMoney)
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelTimer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.items.adapter = adapter

        if (savedInstanceState != null) {
            cacheTime = savedInstanceState.getInt(CACHE_TIME, 0)
            cacheMoney = savedInstanceState.getInt(CACHE_MONEY, 0)
        }
        else viewModel.initGame()

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.game.observe(viewLifecycleOwner) {
            it.getContentOrNot {
                when (it) {
                    is GameUi.Tick -> {
                        cacheTime = it.sec
                        cacheMoney = it.money
                        binding.time.text = it.time
                        binding.money.text = cacheMoney.toString()
                    }

                    is GameUi.Finish -> {
                        val sharedPreferences = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
                        var coins = sharedPreferences?.getInt("coins", -1) ?: -1
                        if (coins != -1) {
                            coins += it.money.toInt()
                            sharedPreferences?.edit()?.putInt("coins", coins)?.apply()
                        }
                        else sharedPreferences?.edit()?.putInt("coins", it.money.toInt())?.apply()
                        launchFragment(EndGameFragment.newInstance(it.money))
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CACHE_TIME, cacheTime)
        outState.putInt(CACHE_MONEY, cacheMoney)
    }

    companion object {
        private const val CACHE_TIME = "CACHE_TIME"
        private const val CACHE_MONEY = "CACHE_MONEY"

        fun newInstance() =
            GameFragment()
    }
}