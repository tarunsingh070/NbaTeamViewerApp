package com.tarun.nbateamviewerapp.ui.teamsList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tarun.nbateamviewerapp.data.model.Team
import com.tarun.nbateamviewerapp.databinding.TeamsListFragmentBinding
import com.tarun.nbateamviewerapp.ui.TeamsSharedViewModel
import com.tarun.nbateamviewerapp.ui.extensions.setVisibility
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class TeamsListFragment : Fragment(), TeamsListAdapter.TeamsListAdapterListener {
    private val teamsListAdapter: TeamsListAdapter by inject { parametersOf(this)}
    private lateinit var binding: TeamsListFragmentBinding
    private val viewModel: TeamsSharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = TeamsListFragmentBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeTeamsList()
        viewModel.onTeamsListViewCreated()
    }

    override fun onTeamClicked(team: Team) {
        viewModel.onTeamClicked(team)
        val action = TeamsListFragmentDirections.actionTeamsListFragmentToTeamDetailsFragment()
        findNavController().navigate(action)
    }

    /**
     * Sets up the recycler view for showing the NBA teams list.
     */
    private fun setupRecyclerView() {
        binding.teamsListRecyclerView.apply {
            adapter = teamsListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    /**
     * Observes the list of teams and updates the list when changes are detected.
     */
    private fun observeTeamsList() {
        viewModel.teamsList.observe(viewLifecycleOwner, {
            teamsListAdapter.submitList(it)
            binding.emptyLabel.setVisibility(it.isEmpty())
        })
    }
}