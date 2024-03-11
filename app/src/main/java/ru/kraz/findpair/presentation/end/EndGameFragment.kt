package ru.kraz.findpair.presentation.end

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kraz.findpair.databinding.FragmentEndGameBinding
import ru.kraz.findpair.presentation.common.BaseFragment
import ru.kraz.findpair.presentation.menu.MenuFragment


class EndGameFragment : BaseFragment<FragmentEndGameBinding>() {

    private var coinsWon = 0

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentEndGameBinding =
        FragmentEndGameBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinsWon = requireArguments().getInt(COINS_WON, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.money.text = coinsWon.toString()
        binding.home.setOnClickListener {
            launchFragment(MenuFragment.newInstance())
        }
    }

    companion object {
        private const val COINS_WON = "COINS_WON"

        fun newInstance(coinsWon: Int) =
            EndGameFragment().apply {
                arguments = Bundle().apply {
                    putInt(COINS_WON, coinsWon)
                }
            }
    }
}