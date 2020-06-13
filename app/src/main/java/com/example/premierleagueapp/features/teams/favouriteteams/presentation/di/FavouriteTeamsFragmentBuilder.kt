package com.example.premierleagueapp.features.teams.favouriteteams.presentation.di

import com.example.premierleagueapp.features.teams.favouriteteams.presentation.view.FavouriteTeamsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavouriteTeamsFragmentBuilder {
    @ContributesAndroidInjector(modules = [FavouriteTeamsModule::class])
    abstract fun bindFavouriteTeamsFragment(): FavouriteTeamsFragment
}