package com.example.premierleagueapp.core.presentation.di.component

import android.app.Application
import com.example.premierleagueapp.core.presentation.application.PremierLeagueApp
import com.example.premierleagueapp.core.presentation.di.module.ActivityBuilder
import com.example.premierleagueapp.core.presentation.di.module.AppModule
import com.example.premierleagueapp.core.presentation.di.module.SubComponentsModule
import com.example.premierleagueapp.features.loading.presentation.di.component.LoadingWorkerComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        AppModule::class,
        SubComponentsModule::class
    ]
)
interface AppComponent {
    fun inject(app: PremierLeagueApp)

    fun loadingWorkerComponent(): LoadingWorkerComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
