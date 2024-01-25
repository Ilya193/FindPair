package ru.kraz.findpair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kraz.findpair.databinding.FragmentEndGameBinding


class EndGameFragment : BaseFragment<FragmentEndGameBinding>() {
    private lateinit var coinsWon: String

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentEndGameBinding =
        FragmentEndGameBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinsWon = arguments?.getString(COINS_WON, "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.money.text = coinsWon
        binding.home.setOnClickListener {
            launchFragment(MenuFragment.newInstance())
        }
    }

    companion object {
        private const val COINS_WON = "COINS_WON"

        fun newInstance(coinsWon: String) =
            EndGameFragment().apply {
                arguments = Bundle().apply {
                    putString(COINS_WON, coinsWon)
                }
            }
    }
}