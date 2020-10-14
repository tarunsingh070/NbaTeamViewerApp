package com.tarun.nbateamviewerapp.di

import androidx.recyclerview.widget.DiffUtil
import com.tarun.nbateamviewerapp.data.model.Team
import com.tarun.nbateamviewerapp.ui.teamsList.TeamsListAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { (listener: TeamsListAdapter.TeamsListAdapterListener) ->
        TeamsListAdapter(listener, object : DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean =
                oldItem == newItem
        })
    }
}