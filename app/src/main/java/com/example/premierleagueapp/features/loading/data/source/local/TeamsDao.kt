package com.example.premierleagueapp.features.loading.data.source.local

import androidx.room.*
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity

@Dao
interface TeamsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTams(teams: List<TeamDetailsEntity>)

    @Query("Delete From TeamDetails")
    fun deleteAllTeams()
}