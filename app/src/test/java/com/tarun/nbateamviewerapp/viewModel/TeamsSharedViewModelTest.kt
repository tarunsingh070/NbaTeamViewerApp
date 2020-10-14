package com.tarun.nbateamviewerapp.viewModel

import android.os.Build
import com.google.common.truth.Truth.assertThat
import com.tarun.nbateamviewerapp.R
import com.tarun.nbateamviewerapp.data.model.Team
import com.tarun.nbateamviewerapp.data.remote.TeamsRepository
import com.tarun.nbateamviewerapp.di.helperModule
import com.tarun.nbateamviewerapp.di.testHelperModule
import com.tarun.nbateamviewerapp.schedulers.SchedulerProvider
import com.tarun.nbateamviewerapp.schedulers.TestSchedulerProviderManager
import com.tarun.nbateamviewerapp.ui.viewModels.TeamsSharedViewModel
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class TeamsSharedViewModelTest : AutoCloseKoinTest() {
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    private val mockTeamsRepository: TeamsRepository by inject()
    private val testScheduler: SchedulerProvider by inject()
    private val viewModel: TeamsSharedViewModel by inject()

    @Mock
    private lateinit var team1: Team

    @Mock
    private lateinit var team2: Team

    @Mock
    private lateinit var team3: Team
    private lateinit var teamsList: List<Team>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        unloadKoinModules(helperModule)
        loadKoinModules(testHelperModule)

        teamsList = getTeamsList()

        declareMock<TeamsRepository> {
            Mockito.`when`(mockTeamsRepository.getTeams())
                .thenReturn(Single.just(teamsList))
        }
    }

    /**
     * Creates and returns a list of mock Team objects.
     */
    private fun getTeamsList(): List<Team> {
        Mockito.`when`(team1.id).thenReturn(1)
        Mockito.`when`(team1.fullName).thenReturn("Cteam")
        Mockito.`when`(team1.wins).thenReturn(5)
        Mockito.`when`(team1.losses).thenReturn(10)

        Mockito.`when`(team2.id).thenReturn(2)
        Mockito.`when`(team2.fullName).thenReturn("Bteam")
        Mockito.`when`(team2.wins).thenReturn(8)
        Mockito.`when`(team2.losses).thenReturn(6)

        Mockito.`when`(team3.id).thenReturn(3)
        Mockito.`when`(team3.fullName).thenReturn("Ateam")
        Mockito.`when`(team3.wins).thenReturn(3)
        Mockito.`when`(team3.losses).thenReturn(2)

        return arrayListOf(team1, team2, team3)
    }

    @Test
    fun `when onTeamsListViewCreated() method is called, ensure that teams list is fetched successfully and automatically sorted in alphabetical order`() {
        viewModel.onTeamsListViewCreated()
        Mockito.verify(mockTeamsRepository).getTeams()
        (testScheduler as TestSchedulerProviderManager).testScheduler.triggerActions()
        assertThat(viewModel.teamsList.value?.size == 3).isTrue()
        assertThat(viewModel.teamsList.value?.get(0)?.id ?: 0 == 3).isTrue()
    }

    @Test
    fun `when onTeamsListViewCreated() method is called and teams list fail to be fetched, ensure that error field is properly set`() {
        Mockito.`when`(mockTeamsRepository.getTeams())
            .thenReturn(Single.error(Exception()))

        viewModel.onTeamsListViewCreated()
        Mockito.verify(mockTeamsRepository).getTeams()
        (testScheduler as TestSchedulerProviderManager).testScheduler.triggerActions()
        assertThat(viewModel.isLoading.value == false).isTrue()
        assertThat(viewModel.errorMessage.value == R.string.error_fetching_teams_message).isTrue()
    }

    @Test
    fun `when onSortTeamByOptionClicked is called with position 1, verify that teams are sorted by wins in decreasing order`() {
        viewModel.teamsList.value = teamsList
        viewModel.onSortTeamByOptionClicked(1)
        assertThat(viewModel.teamsList.value?.get(0)?.id ?: 0 == 2).isTrue()
    }

    @Test
    fun `when onSortTeamByOptionClicked is called with position 2, verify that teams are sorted by losses in decreasing order`() {
        viewModel.teamsList.value = teamsList
        viewModel.onSortTeamByOptionClicked(2)
        assertThat(viewModel.teamsList.value?.get(0)?.id ?: 0 == 1).isTrue()
    }

    @Test
    fun `when onTeamClicked() method is called, ensure that the selectedTeam field is updated with the team sent in`() {
        viewModel.onTeamClicked(team2)
        assertThat(viewModel.getSelectedTeam().value?.id ?: -1 == 2).isTrue()
    }
}