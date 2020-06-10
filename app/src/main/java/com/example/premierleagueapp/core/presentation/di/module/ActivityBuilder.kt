package com.example.premierleagueapp.core.presentation.di.module

import com.example.premierleagueapp.features.loading.presentation.di.LoadingModule
import com.example.premierleagueapp.features.loading.presentation.view.LoadingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [LoadingModule::class])
    abstract fun bindLoadingActivity(): LoadingActivity

}