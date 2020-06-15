package com.example.premierleagueapp.features.loading.presentation.di.component

import com.example.premierleagueapp.features.loading.presentation.di.module.LoadingModule
import com.example.premierleagueapp.features.loading.presentation.worker.LoadTeamsDataWorker
import dagger.Subcomponent

@Subcomponent(modules = [LoadingModule::class])
interface LoadingWorkerComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): LoadingWorkerComponent
    }

    fun inject(loadTeamsDataWorker: LoadTeamsDataWorker)
}