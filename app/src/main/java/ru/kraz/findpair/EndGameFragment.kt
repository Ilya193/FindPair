package ru.kraz.findpair

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kraz.findpair.databinding.FragmentEndGameBinding


class EndGameFragment : BaseFragment<FragmentEndGameBinding>() {
    private lateinit var moneyReceived: String

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentEndGameBinding =
        FragmentEndGameBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moneyReceived = arguments?.getString(MONEY_RECEIVED, "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.money.text = moneyReceived
        binding.home.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MenuFragment.newInstance())
                .commit()
        }
    }

    companion object {
        private const val MONEY_RECEIVED = "MONEY_RECEIVED"

        fun newInstance(moneyReceived: String) =
            EndGameFragment().apply {
                arguments = Bundle().apply {
                    putString(MONEY_RECEIVED, moneyReceived)
                }
            }
    }
}