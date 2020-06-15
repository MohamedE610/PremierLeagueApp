package com.example.premierleagueapp.features.teams.allteams.data.mapper

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel

fun TeamDetailsEntity.map() = TeamModel(
    id = this.id,
    area = this.area,
    name = this.name,
    shortName = this.shortName,
    website = this.website,
    address = this.address,
    clubColors = this.clubColors,
    email = this.email,
    founded = this.founded,
    phone = this.phone?:"",
    venue = this.venue,
    crestUrl = this.crestUrl,
    isFavourite = this.isFavourite
)