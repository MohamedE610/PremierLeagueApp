package com.example.premierleagueapp.features.loading.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamsResponse(
    @Json(name = "count")
    val count: Int = 0,
    @Json(name = "filters")
    val filters: Filters = Filters(),
    @Json(name = "competition")
    val competition: Competition = Competition(),
    @Json(name = "season")
    val season: Season = Season(),
    @Json(name = "teams")
    val teams: List<Team> = listOf()
)

@JsonClass(generateAdapter = true)
class Filters(
)

@JsonClass(generateAdapter = true)
data class Competition(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "area")
    val teamArea: TeamArea = TeamArea(),
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
data class Season(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "startDate")
    val startDate: String = "",
    @Json(name = "endDate")
    val endDate: String = "",
    @Json(name = "currentMatchday")
    val currentMatchday: Int = 0,
    @Json(name = "winner")
    val winner: Any? = null
)

@JsonClass(generateAdapter = true)
data class Team(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "area")
    val teamArea: TeamArea = TeamArea(),
    @Json(name = "name")
    val name: String = "",
    @Json(name = "shortName")
    val shortName: String = "",
    @Json(name = "tla")
    val tla: String = "",
    @Json(name = "crestUrl")
    val crestUrl: String = "",
    @Json(name = "address")
    val address: String = "",
    @Json(name = "phone")
    val phone: String? = null,
    @Json(name = "website")
    val website: String = "",
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "founded")
    val founded: Int? = null,
    @Json(name = "clubColors")
    val clubColors: String = "",
    @Json(name = "venue")
    val venue: String = "",
    @Json(name = "lastUpdated")
    val lastUpdated: String = ""
)