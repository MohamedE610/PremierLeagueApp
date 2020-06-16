package com.example.premierleagueapp.common.teamfavourite.presentation.viewmodel

import android.util.Log
import com.example.premierleagueapp.common.teamfavourite.domain.interactor.MarkTeamAsFavouriteUseCase
import com.example.premierleagueapp.common.teamfavourite.domain.interactor.MarkTeamAsUnFavouriteUseCase
import com.example.premierleagueapp.core.presentation.di.qualifier.IoScheduler
import com.example.premierleagueapp.core.presentation.di.qualifier.MainScheduler
import com.example.premierleagueapp.core.presentation.modelwraper.SingleLiveEvent
import com.example.premierleagueapp.core.presentation.viewmodel.BaseViewModel
import com.example.premierleagueapp.features.teams.allteams.presentation.model.TeamUI
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TeamFavouriteViewModel @Inject constructor(
    private val markTeamAsFavouriteUseCase: MarkTeamAsFavouriteUseCase,
    private val markTeamAsUnFavouriteUseCase: MarkTeamAsUnFavouriteUseCase,
    @IoScheduler private val ioScheduler: Scheduler,
    @MainScheduler private val mainScheduler: Scheduler
) : BaseViewModel() {
    val lastUpdatedTeam = SingleLiveEvent<TeamUI>()

    fun markTeamAsFavourite(teamId: Int) {
        val disposable = markTeamAsFavouriteUseCase.execute(teamId)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .subscribe({
                Log.d("markTeamAsFavourite", "success")
            }, {
                Log.d("markTeamAsFavourite", "failed")
            })

        addDisposable(disposable)
    }

    fun markTeamAsUnFavourite(teamId: Int) {
        val disposable = markTeamAsUnFavouriteUseCase.execute(teamId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("markTeamAsUnFavourite", "success")
            }, {
                Log.d("markTeamAsUnFavourite", "failed")
            })

        addDisposable(disposable)
    }

}