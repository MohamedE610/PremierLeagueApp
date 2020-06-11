package com.example.premierleagueapp.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.core.data.source.local.dao.TeamsDao

@Database(entities = [TeamDetailsEntity::class], version = 1, exportSchema = false)
@TypeConverters(DBConverters::class)
abstract class PremierLeagueDB : RoomDatabase() {
    abstract fun teamsDao(): TeamsDao
}