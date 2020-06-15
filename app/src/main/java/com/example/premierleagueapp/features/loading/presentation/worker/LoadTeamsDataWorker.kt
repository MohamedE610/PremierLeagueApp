package com.example.premierleagueapp.features.loading.presentation.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.application.PremierLeagueApp
import com.example.premierleagueapp.features.loading.data.mapper.map
import com.example.premierleagueapp.features.loading.data.model.Team
import com.example.premierleagueapp.features.loading.domain.repository.LoadingRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LoadTeamsDataWorker(appContext: Context, workerParameters: WorkerParameters) :
    RxWorker(appContext, workerParameters) {

    @Inject
    lateinit var loadingRepository: LoadingRepository

    init {
        val loadingWorkerComponent =
            (appContext as PremierLeagueApp).appComponent?.loadingWorkerComponent()?.build()
        loadingWorkerComponent?.inject(this)
    }

    override fun createWork(): Single<Result> {
        return loadTeamsData()
            .onErrorResumeNext { error ->
                handleOnErrorResumeNext(error)
            }.toSingle { Result.success() }
            .onErrorReturn {
                handleOnErrorReturn(it)
            }

    }

    private fun handleOnErrorResumeNext(error: Throwable): Completable {
        return if (error is PremierLeagueException)
            when (error.kind) {
                PremierLeagueException.Kind.HTTP -> {
                    // {"message":"You reached your request limit. Wait 4 seconds.","errorCode":429}
                    // 429 statue code of api number of calls limitations per minutes
                    if (error.statusCode == 429) {
                        updateProgress()
                        Thread.sleep(ONE_MINUTE)
                        loadTeamsData()
                    } else
                        Completable.error(error)
                }
                else -> Completable.error(error)
            }
        else
            Completable.error(error)
    }

    private fun handleOnErrorReturn(it: Throwable): Result {
        val outputData = Data.Builder()
        val errorKind = when {
            it is PremierLeagueException -> it.kind.name
            it.cause is PremierLeagueException -> (it.cause as PremierLeagueException).kind.name
            else -> ""
        }
        outputData.putString(ERROR_KIND, errorKind)
        return Result.failure(outputData.build())
    }

    private fun loadTeamsData(): Completable {
        return Observable.create<List<Team>> { emitter ->
            try {
                val teamResponse = loadingRepository.getTeams().blockingGet()
                val teamsArrayList = arrayListOf<Team>().apply { addAll(teamResponse.teams) }

                while (teamsArrayList.isNotEmpty()) {
                    val teamsSubList = teamsArrayList.take(8)
                    emitter.onNext(teamsSubList)
                    teamsArrayList.removeAll(teamsSubList)
                    if (teamsArrayList.isNotEmpty())
                        Thread.sleep(ONE_MINUTE)
                }
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.concatMapIterable { teams -> teams }
            .flatMapSingle { team ->
                Log.d("LoadTeams", " -> [${team.id}] ${team.name}")
                loadingRepository.getTeamDetailsById(teamId = team.id)
            }.buffer(8).flatMapCompletable { teams ->
                val teamNames = teams.joinToString(",") { it.name }
                Log.d("LoadTeams", " -> $teamNames")
                val completable = loadingRepository.refreshTeamsData(teams.map { it.map() })
                updateProgress()
                completable
            }
    }

    private fun updateProgress() {
        val data = Data.Builder().putBoolean(IS_FIRST_PAGE_LOADED, true).build()
        setProgress(data)
    }

    companion object {
        const val IS_FIRST_PAGE_LOADED = "isFirstPageLoaded"
        const val ERROR_KIND = "errorKind"
        private const val ONE_MINUTE: Long = 60 * 1000
    }
}