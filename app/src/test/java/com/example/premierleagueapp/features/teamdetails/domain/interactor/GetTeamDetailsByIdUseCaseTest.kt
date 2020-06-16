package com.example.premierleagueapp.features.teamdetails.domain.interactor

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.domain.repository.TeamDetailsRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class GetTeamDetailsByIdUseCaseTest {

    @Test
    fun `GetTeamDetailsByIdUseCase_execute() with existing id then return Single of TeamDetailsEntity`() {
        // arrange
        val id = 102
        val repository = Mockito.mock(TeamDetailsRepository::class.java)
        Mockito.`when`(repository.getTeamDetailsByIdFromDB(id)).thenReturn(
            Single.just(
                TeamDetailsEntity(id = id)
            )
        )

        val getTeamDetailsByIdUseCase = GetTeamDetailsByIdUseCase(repository)

        // act
        val resultObserver = getTeamDetailsByIdUseCase.execute(id).test()

        // assert
        resultObserver.assertValue(TeamDetailsEntity(id = id))
        resultObserver.dispose()
    }

    @Test
    fun `GetTeamDetailsByIdUseCase_execute() with existing id then invoke repository_getTeamDetailsByIdFromDB`() {
        // arrange
        val id = 102
        val repository = Mockito.mock(TeamDetailsRepository::class.java)
        Mockito.`when`(repository.getTeamDetailsByIdFromDB(id)).thenReturn(
            Single.just(
                TeamDetailsEntity(id = id)
            )
        )
        val getTeamDetailsByIdUseCase = GetTeamDetailsByIdUseCase(repository)

        // act
        getTeamDetailsByIdUseCase.execute(id)

        // assert
        Mockito.verify(repository).getTeamDetailsByIdFromDB(ArgumentMatchers.eq(id))
    }

    @Test(expected = NullPointerException::class)
    fun `GetTeamDetailsByIdUseCase_execute() with not existing id then throw NullPointerException`() {
        // arrange
        val id = -1 // not existing id
        val repository = Mockito.mock(TeamDetailsRepository::class.java)
        Mockito.`when`(repository.getTeamDetailsByIdFromDB(id))
            .thenThrow(java.lang.NullPointerException())

        val getTeamDetailsByIdUseCase = GetTeamDetailsByIdUseCase(repository)

        // act
        getTeamDetailsByIdUseCase.execute(id)
    }
}