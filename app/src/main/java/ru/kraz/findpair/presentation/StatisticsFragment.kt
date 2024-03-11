package ru.kraz.findpair.presentation

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.elveum.elementadapter.simpleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.findpair.R
import ru.kraz.findpair.databinding.FragmentStatisticsBinding
import ru.kraz.findpair.databinding.GameItemLayoutBinding
import ru.kraz.findpair.presentation.common.BaseFragment

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    private val viewModel: StatisticsViewModel by viewModel()

    private val adapter = simpleAdapter<GameUi, GameItemLayoutBinding> {
        areContentsSame = { oldItem, newItem ->
            oldItem.id == newItem.id
        }

        areContentsSame = { oldItem, newItem ->
            oldItem == newItem
        }

        bind {
            tvDate.text = textFromHtml(resources.getString(R.string.date), it.date)
            tvTimespent.text =
                textFromHtml(resources.getString(R.string.timespent), it.timespent.toString())
            tvProfit.text = textFromHtml(resources.getString(R.string.profit), it.profit.toString())
        }
    }

    private fun textFromHtml(text: String, value: String): Spanned =
        HtmlCompat.fromHtml(
            String.format(
                text,
                value
            ), HtmlCompat.FROM_HTML_MODE_LEGACY
        )


    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentStatisticsBinding =
        FragmentStatisticsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvGames.adapter = adapter
        binding.rvGames.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.tvInfo.visibility = if (it is GamesUiState.Empty) View.VISIBLE else View.GONE
            binding.rvGames.visibility = if (it is GamesUiState.Success) View.VISIBLE else View.GONE
            if (it is GamesUiState.Success) {
                binding.tvTotalGames.text =
                    textFromHtml(resources.getString(R.string.total_games), it.data.size.toString())
                adapter.submitList(it.data)
            }
        }

        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance() =
            StatisticsFragment()
    }
}