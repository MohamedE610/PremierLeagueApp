package com.example.premierleagueapp.features.teams.favouriteteams.presentation.viewmodel

import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.di.qualifier.IoScheduler
import com.example.premierleagueapp.core.presentation.di.qualifier.MainScheduler
import com.example.premierleagueapp.core.presentation.modelwraper.ObservableResource
import com.example.premierleagueapp.core.presentation.viewmodel.BaseViewModel
import com.example.premierleagueapp.features.teams.allteams.presentation.mapper.map
import com.example.premierleagueapp.features.teams.allteams.presentation.model.TeamUI
import com.example.premierleagueapp.features.teams.favouriteteams.domain.interactor.GetFavouriteTeamsUseCase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavouriteTeamsViewModel @Inject constructor(
    private val getFavouriteTeamsUseCase: GetFavouriteTeamsUseCase,
    @IoScheduler private val ioScheduler: Scheduler,
    @MainScheduler private val mainScheduler: Scheduler
) : BaseViewModel() {

    val favouriteTeamsObservableResource = ObservableResource<List<TeamUI>>()

    fun getFavouriteTeams() {
        val disposable = getFavouriteTeamsUseCase.execute()
            .observeOn(mainScheduler)
            .subscribeOn(ioScheduler)
            .subscribe({
                it?.let { teams ->
                    favouriteTeamsObservableResource.value = teams.map { team-> team.map() }
                }
            }, {
                if (it is PremierLeagueException)
                    favouriteTeamsObservableResource.error.value = it
                else
                    favouriteTeamsObservableResource.error.value =
                        PremierLeagueException(PremierLeagueException.Kind.UNEXPECTED, it)
            })

        addDisposable(disposable)
    }
}