package ru.kraz.findpair

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
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, EndGameFragment.newInstance(it.money))
                            .commit()
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