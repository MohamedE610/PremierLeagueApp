package com.example.premierleagueapp.core.data.source.local.db

import androidx.room.TypeConverter
import com.example.premierleagueapp.core.data.source.local.entity.ActiveCompetition
import com.example.premierleagueapp.core.data.source.local.entity.Area
import com.example.premierleagueapp.core.data.source.local.entity.Player
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class DBConverters {

    @TypeConverter
    fun getAreaJson(area: Area): String? {
        val moshi = Moshi.Builder().build()
        val areaAdapter =
            moshi.adapter(Area::class.java)
        return areaAdapter.toJson(area)
    }

    @TypeConverter
    fun getAreaObject(areaJson: String): Area? {
        val moshi = Moshi.Builder().build()
        val areaAdapter =
            moshi.adapter(Area::class.java)
        return areaAdapter.fromJson(areaJson)
    }

    @TypeConverter
    fun getActiveCompetitionsJson(activeCompetition: List<ActiveCompetition>): String? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Player::class.java)
        val activeCompetitionAdapter =
            moshi.adapter<List<ActiveCompetition>>(type)
        return activeCompetitionAdapter.toJson(activeCompetition)
    }

    @TypeConverter
    fun getActiveCompetitionsList(activeCompetitionJson: String): List<ActiveCompetition>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Player::class.java)
        val activeCompetitionAdapter =
            moshi.adapter<List<ActiveCompetition>>(type)
        return activeCompetitionAdapter.fromJson(activeCompetitionJson)
    }

    @TypeConverter
    fun getPlayerListJson(players: List<Player>): String? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Player::class.java)
        val playersAdapter =
            moshi.adapter<List<Player>>(type)
        return playersAdapter.toJson(players)
    }

    @TypeConverter
    fun getPlayerList(playersJson: String): List<Player>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Player::class.java)
        val playersAdapter =
            moshi.adapter<List<Player>>(type)
        return playersAdapter.fromJson(playersJson)
    }
}