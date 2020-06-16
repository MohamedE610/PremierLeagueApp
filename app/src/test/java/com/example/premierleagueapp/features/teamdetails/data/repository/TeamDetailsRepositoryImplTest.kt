package com.example.premierleagueapp.features.teamdetails.data.repository

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.data.source.local.TeamDetailsLocalDataSource
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Test

class TeamDetailsRepositoryImplTest {

    @Test
    fun `getTeamDetailsByIdFromDB() with existing id then return Single of TeamDetailsEntity`() {
        // arrange
        val id = 102
        val localDataSource = object : TeamDetailsLocalDataSource {
            override fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity?> {
                return Single.just(TeamDetailsEntity(id = teamId))
            }
        }
        val teamDetailsRepositoryImpl = TeamDetailsRepositoryImpl(localDataSource)

        // act
        val result = teamDetailsRepositoryImpl.getTeamDetailsByIdFromDB(teamId = id).blockingGet()

        //assert
        val expected = TeamDetailsEntity(id)
        assertEquals(expected, result)
    }

    @Test
    fun `getTeamDetailsByIdFromDB() with existing id then return invoke localDataSource_getTeamDetailsByIdFromDB`() {
        // arrange
        var invoked = false
        val localDataSource = object : TeamDetailsLocalDataSource {
            override fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity?> {
                invoked = true
                return Single.just(TeamDetailsEntity(id = teamId))
            }
        }
        val teamDetailsRepositoryImpl = TeamDetailsRepositoryImpl(localDataSource)

        // act
        teamDetailsRepositoryImpl.getTeamDetailsByIdFromDB(teamId = 1)

        //assert
        assert(invoked)
    }

    @Test(expected = NullPointerException::class)
    fun `getTeamDetailsByIdFromDB() with not existing id then throw NullPointerException`() {
        // arrange
        val id = -1
        val localDataSource = object : TeamDetailsLocalDataSource {
            override fun getTeamDetailsByIdFromDB(teamId: Int): Single<TeamDetailsEntity?> {
                return Single.just(null)
            }
        }
        val teamDetailsRepositoryImpl = TeamDetailsRepositoryImpl(localDataSource)

        // act
        teamDetailsRepositoryImpl.getTeamDetailsByIdFromDB(teamId = id)

    }
}