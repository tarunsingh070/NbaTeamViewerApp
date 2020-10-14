package com.tarun.nbateamviewerapp.data.remote

import com.tarun.nbateamviewerapp.data.model.Team
import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class TeamsRepositoryImpl : TeamsRepository, KoinComponent {
    private val apiService: ApiService by inject()

    override fun getTeams(): Single<List<Team>> {
        return apiService.getNbaTeams()
    }
}