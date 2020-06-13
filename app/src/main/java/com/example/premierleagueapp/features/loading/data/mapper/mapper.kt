package com.example.premierleagueapp.features.loading.data.mapper

import com.example.premierleagueapp.core.data.source.local.entity.Area
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.loading.data.model.Team
import com.example.premierleagueapp.features.loading.data.model.TeamArea

fun TeamArea.map() = Area(id = this.id, name = this.name)

fun Team.map() = TeamDetailsEntity(
    id = this.id,
    area = this.teamArea.map(),
    name = this.name,
    shortName = this.shortName,
    tla = this.tla,
    crestUrl = this.crestUrl,
    address = this.address,
    phone = this.phone ?: "",
    website = this.website,
    email = this.email ?: "",
    founded = this.founded ?: 0,
    clubColors = this.clubColors,
    venue = this.venue,
    lastUpdated = this.lastUpdated
)
