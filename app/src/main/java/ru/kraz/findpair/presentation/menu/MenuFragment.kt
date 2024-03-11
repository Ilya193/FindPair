package ru.kraz.findpair.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kraz.findpair.R
import ru.kraz.findpair.databinding.FragmentMenuBinding
import ru.kraz.findpair.presentation.game.GameFragment
import ru.kraz.findpair.presentation.statistics.StatisticsFragment
import ru.kraz.findpair.presentation.common.BaseFragment

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentMenuBinding =
        FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        val coins = sharedPreferences.getInt("coins", 0)

        binding.tvMoney.text = coins.toString()

        binding.btnPlay.setOnClickListener {
            launchFragment(GameFragment.newInstance())
        }

        binding.statisticsBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.fragmentContainer, StatisticsFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {

        fun newInstance() = MenuFragment()
    }
}