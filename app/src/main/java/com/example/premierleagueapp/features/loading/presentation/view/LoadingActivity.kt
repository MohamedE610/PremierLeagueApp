package com.example.premierleagueapp.features.loading.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.extensions.showShortToast
import com.example.premierleagueapp.core.presentation.viewmodel.ViewModelFactory
import com.example.premierleagueapp.features.loading.presentation.viewmodel.LoadingViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoadingActivity : AppCompatActivity() {

    @Inject
    lateinit var loadingViewModelFactory: ViewModelFactory<LoadingViewModel>
    private val loadingViewModel by lazy {
        ViewModelProvider(this, loadingViewModelFactory).get(LoadingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_loading)
        initObservers()
        loadPremierLeagueTeams()
    }

    private fun loadPremierLeagueTeams() {
        loadingViewModel.loadPremierLeagueTeams()
    }

    private fun initObservers() {
        loadingViewModel.loadingTeamsObservable.observe(this,
            successObserver = Observer { teamsLoadedSuccessfully() },
            commonErrorObserver = Observer { it?.let { showServerDownError(it) } },
            httpErrorConsumer = Observer { it?.let { showHttpError(it) } },
            networkErrorConsumer = Observer { it?.let { showNetworkError(it) } },
            serverDownErrorConsumer = Observer { it?.let { showServerDownError(it) } },
            timeOutErrorConsumer = Observer { it?.let { showTimeOutError(it) } },
            unExpectedErrorConsumer = Observer { it?.let { showServerDownError(it) } })
    }

    private fun teamsLoadedSuccessfully() {
        showShortToast("Done !")
    }

    private fun showTimeOutError(it: PremierLeagueException) {

    }

    private fun showServerDownError(it: PremierLeagueException) {

    }

    private fun showNetworkError(it: PremierLeagueException) {

    }

    private fun showHttpError(it: PremierLeagueException) {

    }

}