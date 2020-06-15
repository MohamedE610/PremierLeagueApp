package com.example.premierleagueapp.features.loading.domain.interactor

import android.util.Log
import com.example.premierleagueapp.features.loading.data.mapper.map
import com.example.premierleagueapp.features.loading.data.model.Team
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

class LoadTeamsUseCase @Inject constructor(
    private val getTeamsUseCase: GetTeamsUseCase,
    private val getTeamDetailsUseCase: GetTeamDetailsUseCase,
    private val refreshTeamsDataUseCase: RefreshTeamsDataUseCase
) {

    private val counter = AtomicLong(0)

    fun execute() = Observable.create<List<Team>> { emitter ->
        try {
            val teamResponse = getTeamsUseCase.execute().blockingGet()
            val teamsArrayList = arrayListOf<Team>().apply { addAll(teamResponse.teams) }

            while (teamsArrayList.isEmpty()) {
                val teamsSubList = teamsArrayList.take(8)
                emitter.onNext(teamsSubList)
                teamsArrayList.removeAll(teamsSubList)
            }
            emitter.onComplete()
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }.delay(counter.getAndIncrement(), TimeUnit.MINUTES)
        .concatMapIterable { teams -> teams }
        .flatMapSingle { team ->
            Log.d("LoadTeamsUseCase", " -> $counter: [${team.id}] ${team.name}")
            getTeamDetailsUseCase.execute(teamId = team.id)
        }.toList().flatMapCompletable { teams ->
            refreshTeamsDataUseCase.execute(teams.map { it.map() })
        }
}