package ru.kraz.findpair

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.kraz.findpair.databinding.FragmentMenuBinding

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentMenuBinding =
        FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val coins = sharedPreferences?.getInt("coins", -1) ?: -1

        if (coins != -1)
            viewModel.init(coins)
        else viewModel.init()

        viewModel.coins.observe(viewLifecycleOwner) {
            binding.tvMoney.text = it.toString()
        }
        binding.btnPlay.setOnClickListener {
            launchFragment(GameFragment.newInstance())
        }
    }


    companion object {
        fun newInstance() =
            MenuFragment()
    }
}