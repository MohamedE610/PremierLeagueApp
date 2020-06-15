package com.example.premierleagueapp.core.presentation.application

import android.app.Activity
import android.app.Application
import com.example.premierleagueapp.core.presentation.di.component.AppComponent
import com.example.premierleagueapp.core.presentation.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class PremierLeagueApp : Application(), HasActivityInjector {

    var appComponent: AppComponent? = null

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent?.inject(this)
    }
}