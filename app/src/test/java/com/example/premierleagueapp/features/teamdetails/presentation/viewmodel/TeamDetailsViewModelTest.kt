package com.example.premierleagueapp.features.teamdetails.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.features.teamdetails.domain.interactor.GetTeamDetailsByIdUseCase
import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.lang.NullPointerException

@RunWith(JUnit4::class)
class TeamDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `getTeamDetails() with existing id then update teamDetailsObservableResource value`() {

        // arrange
        val id = 102
        val repository = Mockito.mock(TeamDetailsRepository::class.java)
        Mockito.`when`(repository.getTeamDetailsByIdFromDB(id)).thenReturn(
            Single.just(
                TeamDetailsEntity(id = id)
            )
        )
        val getTeamDetailsByIdUseCase = GetTeamDetailsByIdUseCase(repository)

        val ioScheduler = Schedulers.trampoline()
        val mainScheduler = Schedulers.trampoline()
        val viewModel =
            TeamDetailsViewModel(
                getTeamDetailsByIdUseCase = getTeamDetailsByIdUseCase,
                ioScheduler = ioScheduler, mainScheduler = mainScheduler
            )

        // act
        viewModel.getTeamDetails(id)

        //assert
        val expected = TeamDetailsEntity(id = id)
        assertEquals(expected, viewModel.teamDetailsObservableResource.value)

    }


    @Test
    fun `getTeamDetails() with not existing id then update teamDetailsObservableResource_error_value`() {

        // arrange
        val id = -1 // not existing id
        val repository = Mockito.mock(TeamDetailsRepository::class.java)
        Mockito.`when`(repository.getTeamDetailsByIdFromDB(id))
            .thenReturn(Single.error(NullPointerException()))
        val getTeamDetailsByIdUseCase = GetTeamDetailsByIdUseCase(repository)

        val ioScheduler = Schedulers.trampoline()
        val mainScheduler = Schedulers.trampoline()
        val viewModel =
            TeamDetailsViewModel(
                getTeamDetailsByIdUseCase = getTeamDetailsByIdUseCase,
                ioScheduler = ioScheduler, mainScheduler = mainScheduler
            )

        // act
        viewModel.getTeamDetails(id)

        //assert
        val expected =
            PremierLeagueException(PremierLeagueException.Kind.UNEXPECTED)
        assertEquals(expected.kind, viewModel.teamDetailsObservableResource.error.value?.kind)

    }


}