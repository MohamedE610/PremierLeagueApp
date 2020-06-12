package com.example.premierleagueapp.common.teamfavourite.data.source.local

import io.reactivex.Completable

interface TeamFavouriteLocalDataSource {
    fun markTeamAsFavourite(teamId: Int):Completable
    fun markTeamAsUnFavourite(teamId: Int):Completable
}