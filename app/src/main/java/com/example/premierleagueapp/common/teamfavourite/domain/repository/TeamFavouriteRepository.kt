package com.example.premierleagueapp.common.teamfavourite.domain.repository

import io.reactivex.Completable

interface TeamFavouriteRepository {
    fun markTeamAsFavourite(teamId: Int):Completable
    fun markTeamAsUnFavourite(teamId: Int):Completable
}