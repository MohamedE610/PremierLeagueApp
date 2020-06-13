package com.example.premierleagueapp.features.teamdetails.data.mapper

import com.example.premierleagueapp.core.data.source.local.entity.ActiveCompetition
import com.example.premierleagueapp.core.data.source.local.entity.Area
import com.example.premierleagueapp.core.data.source.local.entity.Player
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.features.teamdetails.data.model.Squad
import com.example.premierleagueapp.features.teamdetails.data.model.TeamActiveCompetition
import com.example.premierleagueapp.features.teamdetails.data.model.TeamArea
import com.example.premierleagueapp.features.teamdetails.data.model.TeamDetailsResponse

fun TeamDetailsResponse.map() = TeamDetailsEntity(
    id = this.id,
    area = this.teamArea.map(),
    activeCompetitions = this.teamActiveCompetitions.map { it.map() },
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
    squad = this.squad.map { it.map() },
    lastUpdated = this.lastUpdated
)

fun TeamArea.map() = Area(id = this.id, name = this.name)

fun TeamActiveCompetition.map() = ActiveCompetition(
    id = this.id,
    name = this.name,
    area = this.teamArea.map(),
    code = this.code,
    lastUpdated = this.lastUpdated,
    plan = this.plan
)

fun Squad.map() = Player(
    id = this.id,
    name = this.name,
    position = this.position ?: "",
    dateOfBirth = this.dateOfBirth,
    countryOfBirth = this.countryOfBirth,
    nationality = this.nationality,
    shirtNumber = this.shirtNumber ?: "",
    role = this.role
)
