package com.example.premierleagueapp.features.teams.allteams.domain.model

import com.example.premierleagueapp.core.data.source.local.entity.Area

data class TeamModel(
    val id: Int = 0,
    val area: Area = Area(),
    val name: String = "",
    val shortName: String = "",
    val address: String = "",
    val phone: String = "",
    val website: String = "",
    val email: String? = null,
    val founded: Int = 0,
    val clubColors: String = "",
    val venue: String = "",
    val crestUrl:String = "",
    var isFavourite: Boolean = false
)