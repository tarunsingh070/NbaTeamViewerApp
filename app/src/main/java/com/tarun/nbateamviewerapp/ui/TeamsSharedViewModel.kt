package com.tarun.nbateamviewerapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tarun.nbateamviewerapp.R
import com.tarun.nbateamviewerapp.data.model.Team
import com.tarun.nbateamviewerapp.data.remote.TeamsRepository
import com.tarun.nbateamviewerapp.schedulers.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import org.koin.core.KoinComponent
import org.koin.core.inject

class TeamsSharedViewModel : ViewModel(), KoinComponent {
    private val tag = "TeamsSharedViewModel"
    private val schedulerProvider: SchedulerProvider by inject()
    val teamsList: MutableLiveData<List<Team>> = MutableLiveData()
    private lateinit var selectedTeam: MutableLiveData<Team>
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorMessage: MutableLiveData<Int> = MutableLiveData()
    private val teamsRepository: TeamsRepository by inject()

    private val disposables = CompositeDisposable()

    /**
     * Handles the event when onViewCreated method of [TeamsListFragment] is called.
     */
    fun onTeamsListViewCreated() {
        getTeamsList()
    }

    /**
     * Gets the list of teams.
     */
    private fun getTeamsList() {
        setLoadingState(true)
        disposables.add(
            teamsRepository.getTeams()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : DisposableSingleObserver<List<Team>>() {
                    override fun onSuccess(teams: List<Team>?) {
                        setLoadingState(false)
                        teamsList.value = getTeamsListSortedByName(teams)
                    }

                    override fun onError(e: Throwable?) {
                        setLoadingState(false)
                        setErrorMessage(R.string.error_fetching_teams_message)
                        Log.e(tag, "Error fetching NBA teams list.", e)
                    }
                })
        )
    }

    /**
     * Sets the selected team.
     *
     * @param team The instance of the [Team] selected.
     */
    fun onTeamClicked(team: Team) {
        selectedTeam = MutableLiveData(team)
    }

    /**
     * Sorts the list provided in alphabetical order by name and returns it.
     *
     * @param teamsList The list to be sorted.
     */
    private fun getTeamsListSortedByName(teamsList: List<Team>?): List<Team>? =
        teamsList?.sortedBy { it.fullName.first() }

    /**
     * Sets the loading state value.
     *
     * @param shouldShow A boolean indicating if the loading state should be shown or hidden.
     */
    private fun setLoadingState(shouldShow: Boolean) {
        isLoading.value = shouldShow
    }

    /**
     * Sets the error message to be shown to the user.
     *
     * @param errorMessageResId The resource ID of the error message to be shown
     */
    private fun setErrorMessage(errorMessageResId: Int) {
        errorMessage.value = errorMessageResId
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}