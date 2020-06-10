package com.example.premierleagueapp.features.loading.presentation.viewmodel

import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.modelwraper.ObservableResource
import com.example.premierleagueapp.core.presentation.viewmodel.BaseViewModel
import com.example.premierleagueapp.features.loading.domain.interactor.LoadTeamsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoadingViewModel @Inject constructor(private val loadTeamsUseCase: LoadTeamsUseCase) :
    BaseViewModel() {

    val loadingTeamsObservable = ObservableResource<Unit>()

    fun loadPremierLeagueTeams() {
        val disposable = loadTeamsUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                loadingTeamsObservable.call()
            }, {
                if (it is PremierLeagueException)
                    loadingTeamsObservable.error.value = it
                else
                    loadingTeamsObservable.error.value =
                        PremierLeagueException(PremierLeagueException.Kind.UNEXPECTED, it)
            })

        addDisposable(disposable)
    }
}