package com.tarun.nbateamviewerapp.ui.teamsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tarun.nbateamviewerapp.R
import com.tarun.nbateamviewerapp.data.model.Team
import com.tarun.nbateamviewerapp.databinding.ItemTeamBinding

/**
 * The adapter class for showing the list of teams.
 */
class TeamsListAdapter(private val listener: TeamsListAdapterListener,
                       teamsListDiff: DiffUtil.ItemCallback<Team>) :
    ListAdapter<Team, TeamsListAdapter.TeamViewHolder>(teamsListDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindTo(getItem(position), listener)
    }

    /**
     * View holder class for [TeamsListAdapter]
     *
     * @param binding: The instance of [ItemTeamBinding] for this instance of holder.
     */
    class TeamViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): TeamViewHolder {
                val itemTeamBinding =
                    ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TeamViewHolder(itemTeamBinding)
            }
        }

        fun bindTo(team: Team, listener: TeamsListAdapterListener) {
            val context = itemView.context
            binding.apply {
                teamName.text = team.fullName
                teamWins.text = context.getString(R.string.wins_count, team.wins)
                teamLosses.text = context.getString(R.string.losses_count, team.losses)
            }
            itemView.setOnClickListener { listener.onTeamClicked(team) }
        }
    }

    /**
     * TeamsListAdapter's callback listener interface.
     */
    interface TeamsListAdapterListener {
        /**
         * Handles the event when a team from the list is clicked.
         */
        fun onTeamClicked(team: Team)
    }
}