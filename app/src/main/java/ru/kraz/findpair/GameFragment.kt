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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.items.adapter = adapter

        viewModel.initGame()

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.game.observe(viewLifecycleOwner) {
            it.getContentOrNot {
                when (it) {
                    is GameUi.Tick -> {
                        binding.time.text = it.time
                        binding.money.text = it.money
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

    companion object {

        fun newInstance() =
            GameFragment()
    }
}