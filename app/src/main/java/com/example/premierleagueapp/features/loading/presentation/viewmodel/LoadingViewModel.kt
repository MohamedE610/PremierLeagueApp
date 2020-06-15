package com.example.premierleagueapp.features.loading.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.modelwraper.SingleLiveEvent
import com.example.premierleagueapp.core.presentation.viewmodel.BaseViewModel
import com.example.premierleagueapp.features.loading.presentation.worker.LoadTeamsDataWorker
import com.example.premierleagueapp.features.loading.presentation.worker.LoadTeamsDataWorker.Companion.ERROR_KIND
import com.example.premierleagueapp.features.loading.presentation.worker.LoadTeamsDataWorker.Companion.IS_FIRST_PAGE_LOADED
import javax.inject.Inject

class LoadingViewModel @Inject constructor(private val applicationContext: Context) :
    BaseViewModel() {

    val isFirstPageLoadedLiveEvent = SingleLiveEvent<Void>()
    val onLoadingErrorLiveEvent = SingleLiveEvent<PremierLeagueException.Kind>()

    fun startLoadPremierLeagueTeams() {
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(LoadTeamsDataWorker::class.java)
            .addTag(LoadTeamsDataWorker::class.java.simpleName)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniqueWork(
                LoadTeamsDataWorker::class.java.simpleName,
                ExistingWorkPolicy.KEEP,
                oneTimeWorkRequest
            )
    }

    fun startObservingOnFirstPageOfTeamsLoaded(lifecycleOwner: LifecycleOwner) {
        WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData(LoadTeamsDataWorker::class.java.simpleName)
            .observe(lifecycleOwner, Observer {
                it?.let { workInfos ->
                    if (workInfos.isEmpty())
                        return@Observer

                    val workInfo = workInfos[0]
                    workInfo?.let {
                        checkIfFirstPageLoaded(workInfo)
                        checkIfLoadingFailed(workInfo)
                    }
                }
            })
    }

    private fun checkIfFirstPageLoaded(workInfo: WorkInfo) {
        val progress = workInfo.progress.getBoolean(IS_FIRST_PAGE_LOADED, false)
        if (progress)
            isFirstPageLoadedLiveEvent.call()
    }

    private fun checkIfLoadingFailed(workInfo: WorkInfo) {
        if (workInfo.state == WorkInfo.State.FAILED) {
            val errorKind = workInfo.outputData.getString(ERROR_KIND) ?: ""

            val kind = if (errorKind.isNotEmpty())
                PremierLeagueException.Kind.valueOf(errorKind)
            else
                PremierLeagueException.Kind.UNEXPECTED
            onLoadingErrorLiveEvent.value = kind
        }
    }

}