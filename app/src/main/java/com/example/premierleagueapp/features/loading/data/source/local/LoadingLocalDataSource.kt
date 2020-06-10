package com.example.premierleagueapp.features.loading.data.source.local

import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import io.reactivex.Completable

interface LoadingLocalDataSource {
    fun refreshTeamsData(teams: List<TeamDetailsEntity>): Completable
}