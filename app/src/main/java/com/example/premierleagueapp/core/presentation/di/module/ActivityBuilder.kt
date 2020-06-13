package com.example.premierleagueapp.core.presentation.di.module

import com.example.premierleagueapp.common.teamfavourite.presentation.di.TeamFavouriteModule
import com.example.premierleagueapp.features.loading.presentation.di.LoadingModule
import com.example.premierleagueapp.features.loading.presentation.view.LoadingActivity
import com.example.premierleagueapp.features.teams.allteams.presentation.di.AllTeamsFragmentBuilder
import com.example.premierleagueapp.features.teams.favouriteteams.presentation.di.FavouriteTeamsFragmentBuilder
import com.example.premierleagueapp.features.teams.presentaion.view.activity.TeamsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [LoadingModule::class])
    abstract fun bindLoadingActivity(): LoadingActivity

    @ContributesAndroidInjector(
        modules = [TeamFavouriteModule::class,
            AllTeamsFragmentBuilder::class, FavouriteTeamsFragmentBuilder::class]
    )
    abstract fun bindTeamsActivity(): TeamsActivity

}