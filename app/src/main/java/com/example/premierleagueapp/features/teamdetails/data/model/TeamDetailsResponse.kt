package com.example.premierleagueapp.features.teamdetails.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDetailsResponse(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "area")
    val teamArea: TeamArea = TeamArea(),
    @Json(name = "activeCompetitions")
    val teamActiveCompetitions: List<TeamActiveCompetition> = listOf(),
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
    val phone: String = "",
    @Json(name = "website")
    val website: String = "",
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "founded")
    val founded: Int = 0,
    @Json(name = "clubColors")
    val clubColors: String = "",
    @Json(name = "venue")
    val venue: String = "",
    @Json(name = "squad")
    val squad: List<Squad> = listOf(),
    @Json(name = "lastUpdated")
    val lastUpdated: String = ""
)

@JsonClass(generateAdapter = true)
data class TeamArea(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = ""
)

@JsonClass(generateAdapter = true)
data class TeamActiveCompetition(
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
data class Squad(
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