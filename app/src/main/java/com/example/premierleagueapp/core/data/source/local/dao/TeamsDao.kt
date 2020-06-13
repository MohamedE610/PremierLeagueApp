package com.example.premierleagueapp.core.data.source.local.dao

import androidx.room.*
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Flowable

@Dao
interface TeamsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(teams: List<TeamDetailsEntity>)

    @Query("Delete From TeamDetails")
    fun deleteAllTeams()

    @Query("Select * From TeamDetails Limit :limit Offset :offset")
    fun getTeams(offset: Int, limit: Int): List<TeamDetailsEntity>

    @Query("Select * From TeamDetails Where id = :teamId")
    fun getTeamById(teamId: Int): TeamDetailsEntity?

    @Update
    fun updateTeam(team: TeamDetailsEntity)

    @Query("Select * From TeamDetails Where isFavourite = 1")
    fun getFavouriteTeams(): Flowable<List<TeamDetailsEntity>>
}