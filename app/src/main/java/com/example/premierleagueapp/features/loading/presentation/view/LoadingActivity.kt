package com.example.premierleagueapp.features.loading.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.extensions.isInternetAvailable
import com.example.premierleagueapp.core.presentation.extensions.showShortToast
import com.example.premierleagueapp.core.presentation.utils.InternetConnectivityListener
import com.example.premierleagueapp.core.presentation.viewmodel.ViewModelFactory
import com.example.premierleagueapp.features.loading.presentation.viewmodel.LoadingViewModel
import com.example.premierleagueapp.features.teams.presentaion.view.activity.TeamsActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoadingActivity : AppCompatActivity() {

    @Inject
    lateinit var internetConnectivityListener: InternetConnectivityListener

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
        observeOnInternetConnection()
    }

    private fun observeOnInternetConnection() {
        internetConnectivityListener.internetConnectivityLiveData.observe(this,
            Observer {
                it?.let { isInternetAvailable ->
                    if (isInternetAvailable) {
                        showShortToast(getString(R.string.lbl_internet_connected))
                        loadPremierLeagueTeams()
                    }
                }
            })
    }

    private fun teamsLoadedSuccessfully() {
        navigateToTeamsActivity()
    }

    private fun navigateToTeamsActivity() {
        val intent = Intent(this, TeamsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showTimeOutError(it: PremierLeagueException) {
        showShortToast(getString(R.string.lbl_common_error))
    }

    private fun showServerDownError(it: PremierLeagueException) {
        showShortToast(getString(R.string.lbl_common_error))
    }

    private fun showNetworkError(it: PremierLeagueException) {
        showShortToast(getString(R.string.lbl_network_error))
    }

    private fun showHttpError(it: PremierLeagueException) {
        showShortToast(getString(R.string.lbl_common_error))
    }

    override fun onResume() {
        super.onResume()
        internetConnectivityListener.registerInternetReceiver()
    }

    override fun onPause() {
        super.onPause()
        internetConnectivityListener.unregisterInternetReceiver()
    }

}