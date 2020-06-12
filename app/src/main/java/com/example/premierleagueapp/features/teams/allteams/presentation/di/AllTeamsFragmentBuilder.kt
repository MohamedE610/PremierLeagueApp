package com.example.premierleagueapp.features.teams.allteams.presentation.di

import com.example.premierleagueapp.features.teams.allteams.presentation.view.fragment.AllTeamsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AllTeamsFragmentBuilder {
    @ContributesAndroidInjector(modules = [AllTeamsModule::class])
    abstract fun bindAllTeamsFragment(): AllTeamsFragment
}