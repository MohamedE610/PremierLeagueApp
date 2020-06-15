package com.example.premierleagueapp.features.loading.presentation.di.module

import com.example.premierleagueapp.core.data.source.remote.ServiceGenerator
import com.example.premierleagueapp.features.loading.data.repository.LoadingRepositoryImpl
import com.example.premierleagueapp.features.loading.data.source.local.LoadingLocalDataSource
import com.example.premierleagueapp.features.loading.data.source.local.LoadingLocalDataSourceImpl
import com.example.premierleagueapp.features.loading.data.source.remote.FootballApi
import com.example.premierleagueapp.features.loading.data.source.remote.LoadingRemoteDataSource
import com.example.premierleagueapp.features.loading.data.source.remote.LoadingRemoteDataSourceImpl
import com.example.premierleagueapp.features.loading.domain.repository.LoadingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [LoadingModule.BindsModule::class])
class LoadingModule {

    @Provides
    fun providesFootballApi() = ServiceGenerator().createService(FootballApi::class.java)

    @Module
    interface BindsModule {
        @Binds
        fun bindLoadingLocalDataSource(localDataSource: LoadingLocalDataSourceImpl): LoadingLocalDataSource

        @Binds
        fun bindLoadingRemoteDataSource(remoteDataSource: LoadingRemoteDataSourceImpl): LoadingRemoteDataSource

        @Binds
        fun bindLoadingRepository(repository: LoadingRepositoryImpl): LoadingRepository
    }
}