package com.tarun.nbateamviewerapp.data.remote

import com.tarun.nbateamviewerapp.data.model.Team
import io.reactivex.rxjava3.core.Single

interface TeamsRepository {
    /**
     * Gets the list of NBA teams.
     */
    fun getTeams(): Single<List<Team>>
}