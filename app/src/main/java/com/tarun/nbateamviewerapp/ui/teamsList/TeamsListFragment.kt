package com.tarun.nbateamviewerapp.ui.teamsList

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tarun.nbateamviewerapp.R
import com.tarun.nbateamviewerapp.data.model.Team
import com.tarun.nbateamviewerapp.databinding.TeamsListFragmentBinding
import com.tarun.nbateamviewerapp.ui.viewModels.TeamsSharedViewModel
import com.tarun.nbateamviewerapp.ui.extensions.setVisibility
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class TeamsListFragment : Fragment(), TeamsListAdapter.TeamsListAdapterListener,
    AdapterView.OnItemSelectedListener {
    private val teamsListAdapter: TeamsListAdapter by inject { parametersOf(this)}
    private lateinit var binding: TeamsListFragmentBinding
    private val viewModel: TeamsSharedViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.teams_list_menu, menu)
        val item = menu.findItem(R.id.teams_list_spinner)

        val spinnerAdapter = context?.let {
            ArrayAdapter.createFromResource(
                it, R.array.team_sort_by,
                R.layout.teams_spinner_item
            )}
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = item.actionView as Spinner
        spinner.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = this@TeamsListFragment
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onSortTeamByOptionClicked(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing.
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
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
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

    override fun onTeamsListChanged() {
        // Scrolling the list to top in the event when user changes the sort by ordering.
        binding.teamsListRecyclerView.scrollToPosition(0)
    }
}