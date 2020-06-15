package com.example.premierleagueapp.core.data.source.local.entity


import androidx.room.*
import com.example.premierleagueapp.core.data.source.local.db.DBConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "TeamDetails")
data class TeamDetailsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @TypeConverters(DBConverters::class)
    @ColumnInfo(name = "area")
    val area: Area = Area(),

    @TypeConverters(DBConverters::class)
    @ColumnInfo(name = "activeCompetitions")
    val activeCompetitions: List<ActiveCompetition> = listOf(),

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "shortName")
    val shortName: String = "",

    @ColumnInfo(name = "tla")
    val tla: String = "",

    @ColumnInfo(name = "crestUrl")
    val crestUrl: String = "",

    @ColumnInfo(name = "address")
    val address: String = "",

    @ColumnInfo(name = "phone")
    val phone: String? = "",

    @ColumnInfo(name = "website")
    val website: String = "",

    @ColumnInfo(name = "email")
    val email: String? = null,

    @ColumnInfo(name = "founded")
    val founded: Int = 0,

    @ColumnInfo(name = "clubColors")
    val clubColors: String = "",

    @ColumnInfo(name = "venue")
    val venue: String = "",

    @TypeConverters(DBConverters::class)
    @ColumnInfo(name = "squad")
    val squad: List<Player> = listOf(),

    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: String = "",

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean = false
)

@JsonClass(generateAdapter = true)
data class Area(
    @Json(name = "id")
    val id: Int = 0,

    @Json(name = "name")
    val name: String = ""
)

@JsonClass(generateAdapter = true)
data class ActiveCompetition(
    @Json(name = "id")
    val id: Int = 0,

    @TypeConverters(DBConverters::class)
    @Json(name = "area")
    val area: Area = Area(),

    @Json(name = "name")
    val name: String = "",

    @Json(name = "code")
    val code: String = "",

    @Json(name = "plan")
    val plan: String = "",

    @Json(name = "lastUpdated")
    val lastUpdated: String = ""
)

@JsonClass(generateAdapter = true)
data class Player(
    @Json(name = "id")
    val id: Int = 0,

    @Json(name = "name")
    val name: String = "",

    @Json(name = "position")
    val position: String? = null,

    @Json(name = "dateOfBirth")
    val dateOfBirth: String = "",

    @Json(name = "countryOfBirth")
    val countryOfBirth: String = "",

    @Json(name = "nationality")
    val nationality: String = "",

    @Json(name = "shirtNumber")
    val shirtNumber: String? = null,

    @Json(name = "role")
    val role: String = ""
)