package ru.kraz.findpair.presentation.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.elveum.elementadapter.simpleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.findpair.databinding.FragmentGameBinding
import ru.kraz.findpair.databinding.ItemBinding
import ru.kraz.findpair.presentation.end.EndGameFragment
import ru.kraz.findpair.presentation.common.BaseFragment
import ru.kraz.findpair.presentation.menu.MenuFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel: GameViewModel by viewModel()
    private var cacheTime = 0

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            launchFragment(MenuFragment.newInstance())
        }
    }

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

        if (savedInstanceState != null) launchFragment(MenuFragment.newInstance())
        else {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
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
                            cacheTime = state.sec
                        }

                        is GameUiState.Finish -> {
                            val sharedPreferences =
                                requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                            var coins = sharedPreferences.getInt("coins", 0)
                            coins += state.money
                            sharedPreferences.edit().putInt("coins", coins).apply()
                            launchFragment(EndGameFragment.newInstance(state.money))
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (cacheTime != 0)
            viewModel.initTimer(cacheTime)
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
    }

    companion object {
        fun newInstance() =
            GameFragment()
    }
}