package com.tarun.nbateamviewerapp.data.remote

import com.tarun.nbateamviewerapp.data.model.Team
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

/**
 * The API service interface containing all required endpoints to the NBA API.
 */
interface ApiService {
    @GET("scoremedia/nba-team-viewer/master/input.json")
    fun getNbaTeams(): Single<List<Team>>
}