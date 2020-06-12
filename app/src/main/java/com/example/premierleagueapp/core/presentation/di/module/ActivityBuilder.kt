package com.example.premierleagueapp.core.presentation.di.module

import com.example.premierleagueapp.common.teamfavourite.presentation.di.FavouriteTeamModule
import com.example.premierleagueapp.features.loading.presentation.di.LoadingModule
import com.example.premierleagueapp.features.loading.presentation.view.LoadingActivity
import com.example.premierleagueapp.features.teams.allteams.presentation.di.AllTeamsFragmentBuilder
import com.example.premierleagueapp.features.teams.presentaion.view.activity.TeamsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [LoadingModule::class])
    abstract fun bindLoadingActivity(): LoadingActivity

    @ContributesAndroidInjector(modules = [FavouriteTeamModule::class, AllTeamsFragmentBuilder::class])
    abstract fun bindTeamsActivity(): TeamsActivity

}