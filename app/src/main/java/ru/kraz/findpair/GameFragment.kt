package ru.kraz.findpair

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elveum.elementadapter.simpleAdapter
import ru.kraz.findpair.databinding.FragmentGameBinding
import ru.kraz.findpair.databinding.ItemBinding

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel: GameViewModel by viewModels()

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


    override fun onPause() {
        super.onPause()
        launchFragment(MenuFragment.newInstance())
        viewModel.cancelTimer()
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancelTimer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.items.adapter = adapter

        viewModel.initGame()

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.gameState.observe(viewLifecycleOwner) {
            it.getContentOrNot { state ->
                when (state) {
                    is GameUiState.Tick -> {
                        binding.time.text = state.time
                        binding.money.text = state.money.toString()
                    }

                    is GameUiState.Finish -> {
                        val sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                        var coins = sharedPreferences.getInt("coins", 0)
                        coins += state.money
                        sharedPreferences.edit().putInt("coins", coins).apply()
                        launchFragment(EndGameFragment.newInstance(state.money))
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