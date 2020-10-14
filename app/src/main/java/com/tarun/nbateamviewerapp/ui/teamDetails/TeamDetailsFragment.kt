package com.tarun.nbateamviewerapp.ui.teamDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tarun.nbateamviewerapp.R
import com.tarun.nbateamviewerapp.data.model.PlayersListDataItem
import com.tarun.nbateamviewerapp.databinding.TeamDetailsFragmentBinding
import com.tarun.nbateamviewerapp.ui.TeamsSharedViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TeamDetailsFragment : Fragment() {
    private val playersListAdapter: PlayersListAdapter by inject()
    private lateinit var binding: TeamDetailsFragmentBinding

    private val viewModel: TeamsSharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = TeamDetailsFragmentBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeSelectedTeam()
    }

    /**
     * Sets up the recycler view for showing the NBA players roster.
     */
    private fun setupRecyclerView() {
        binding.playersRosterRecyclerView.apply {
            adapter = playersListAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    /**
     * Observes the selected team and updates the UI when changes are detected.
     */
    private fun observeSelectedTeam() {
        viewModel.getSelectedTeam().observe(viewLifecycleOwner, { it ->
            binding.apply {
                teamName.text = it.fullName
                teamWins.text = getString(R.string.wins_count, it.wins)
                teamLosses.text = getString(R.string.losses_count, it.losses)
            }

            val players = when (it.players.isEmpty()) {
                true -> listOf(PlayersListDataItem.HeaderItem)
                else -> listOf(PlayersListDataItem.HeaderItem) + it.players.map { PlayersListDataItem.PlayerItem(it) }
            }
            playersListAdapter.submitList(players)
        })
    }
}