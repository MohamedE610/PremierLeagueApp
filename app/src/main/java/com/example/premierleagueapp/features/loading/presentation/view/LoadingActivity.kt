package com.example.premierleagueapp.features.loading.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
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
        startLoadingPremierLeagueTeams()
    }

    private fun startLoadingPremierLeagueTeams() {
        loadingViewModel.startLoadPremierLeagueTeams()
        loadingViewModel.startObservingOnFirstPageOfTeamsLoaded(this)
    }

    private fun initObservers() {
        observeOnFirstPageLoaded()
        observeOnLoadingError()
        observeOnInternetConnection()
    }

    private fun observeOnFirstPageLoaded() {
        loadingViewModel.isFirstPageLoadedLiveEvent.observe(this,
            Observer {
                navigateToTeamsActivity()
            })
    }

    private fun observeOnLoadingError() {
        loadingViewModel.onLoadingErrorLiveEvent.observe(this, Observer {
            it?.let { errorKind ->
                when (errorKind) {
                    PremierLeagueException.Kind.NETWORK -> showNetworkError()
                    PremierLeagueException.Kind.HTTP -> showHttpError()
                    PremierLeagueException.Kind.TIME_OUT -> showTimeOutError()
                    PremierLeagueException.Kind.SERVER_DOWN,
                    PremierLeagueException.Kind.UNEXPECTED -> {
                        showServerDownError()
                    }
                }
            }
        })
    }

    private fun observeOnInternetConnection() {
        internetConnectivityListener.internetConnectivityLiveData.observe(this,
            Observer {
                it?.let { isInternetAvailable ->
                    if (isInternetAvailable) {
                        showShortToast(getString(R.string.lbl_internet_connected))
                        startLoadingPremierLeagueTeams()
                    }
                }
            })
    }

    private fun navigateToTeamsActivity() {
        val intent = Intent(this, TeamsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showTimeOutError() {
        showShortToast(getString(R.string.lbl_common_error))
        showShortToast(getString(R.string.lbl_try_again_later))
    }

    private fun showServerDownError() {
        showShortToast(getString(R.string.lbl_common_error))
        showShortToast(getString(R.string.lbl_try_again_later))
    }

    private fun showNetworkError() {
        showShortToast(getString(R.string.lbl_network_error))
    }

    private fun showHttpError() {
        showShortToast(getString(R.string.lbl_common_error))
        showShortToast(getString(R.string.lbl_try_again_later))
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