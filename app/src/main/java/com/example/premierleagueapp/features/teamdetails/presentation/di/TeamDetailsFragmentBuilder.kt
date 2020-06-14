package com.example.premierleagueapp.features.teamdetails.presentation.di

import dagger.android.ContributesAndroidInjector

import com.example.premierleagueapp.features.teamdetails.presentation.view.fragment.TeamDetailsFragment
import dagger.Module

@Module
abstract class TeamDetailsFragmentBuilder {

    @ContributesAndroidInjector(modules = [TeamDetailsModule::class])
    abstract fun bindTeamDetailsFragment(): TeamDetailsFragment

}