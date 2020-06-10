package com.example.premierleagueapp.features.loading.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamArea(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = ""
)