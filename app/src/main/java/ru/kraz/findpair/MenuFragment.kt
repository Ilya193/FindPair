package ru.kraz.findpair

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.kraz.findpair.databinding.FragmentMenuBinding

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentMenuBinding =
        FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val coins = sharedPreferences?.getInt("coins", -1) ?: 0

        binding.tvMoney.text = coins.toString()

        binding.btnPlay.setOnClickListener {
            launchFragment(GameFragment.newInstance(), true)
        }
    }


    companion object {

        fun newInstance() = MenuFragment()
    }
}