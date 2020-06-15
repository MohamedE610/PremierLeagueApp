package com.example.premierleagueapp.features.teams.allteams.presentation.model

data class TeamUI(
    val id: Int = 0,
    val name: String = "",
    val website: String = "",
    val clubColors: String = "",
    val venue: String = "",
    val crestUrl:String = "",
    var isFavourite: Boolean = false,
    val teamPlayersNames: String = ""
)