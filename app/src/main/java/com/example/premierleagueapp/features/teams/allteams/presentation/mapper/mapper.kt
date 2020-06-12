package com.example.premierleagueapp.features.teams.allteams.presentation.mapper

import com.example.premierleagueapp.features.teams.allteams.domain.model.TeamModel
import com.example.premierleagueapp.features.teams.allteams.presentation.model.TeamUI


fun TeamModel.map() = TeamUI(
    id = this.id,
    name = this.name,
    website = this.website,
    venue = this.venue,
    clubColors = this.clubColors,
    crestUrl = this.crestUrl,
    isFavourite = this.isFavourite
)