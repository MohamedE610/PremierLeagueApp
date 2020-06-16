package com.example.premierleagueapp.core.presentation.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.premierleagueapp.core.data.source.local.db.PremierLeagueDB
import com.example.premierleagueapp.core.presentation.utils.AppConstants
import com.example.premierleagueapp.core.presentation.di.qualifier.DatabaseInfo
import com.example.premierleagueapp.core.presentation.di.qualifier.IoScheduler
import com.example.premierleagueapp.core.presentation.di.qualifier.MainScheduler
import com.example.premierleagueapp.core.presentation.di.qualifier.PreferenceInfo
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module()
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @MainScheduler
    @Provides
    @Singleton
    fun provideMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @IoScheduler
    @Provides
    @Singleton
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun providePreferenceObj(
        @PreferenceInfo spName: String,
        application: Application
    ): SharedPreferences {
        return application.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePremierLeagueDB(
        @DatabaseInfo dbName: String,
        context: Context
    ): PremierLeagueDB {
        return Room.databaseBuilder(context, PremierLeagueDB::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesTeamsDao(database: PremierLeagueDB) = database.teamsDao()
}