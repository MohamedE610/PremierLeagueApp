package com.example.premierleagueapp.features.teamdetails.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.extensions.showShortToast
import com.example.premierleagueapp.core.presentation.viewmodel.ViewModelFactory
import com.example.premierleagueapp.features.teamdetails.presentation.viewmodel.TeamDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TeamDetailsFragment : Fragment() {

    @Inject
    lateinit var teamDetailsViewModelFactory: ViewModelFactory<TeamDetailsViewModel>

    private val teamDetailsViewModel by lazy {
        ViewModelProvider(this, teamDetailsViewModelFactory).get(TeamDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        loadTeamDetails()
    }

    private fun loadTeamDetails() {
        val teamId = arguments?.getInt(TEAM_ID, -1) ?: -1
        teamDetailsViewModel.getTeamDetails(teamId)
    }

    // region initViews

    private fun initViews() {

    }

    // endregion initViews


    // region initObservers

    private fun initObservers() {
        teamDetailsViewModel.teamDetailsObservableResource.observe(viewLifecycleOwner,
            successObserver = Observer { it?.let { teamDetailsLoadedSuccessfully(it) }},
            loadingObserver = Observer { it?.let { showLoading(it) }},
            commonErrorObserver = Observer { it?.let { showServerDownError(it)}},
            unExpectedErrorConsumer = Observer { it?.let { showServerDownError(it)}},
            serverDownErrorConsumer = Observer { it?.let { showServerDownError(it)}},
            timeOutErrorConsumer = Observer { it?.let { showTimeOutError(it) }},
            networkErrorConsumer = Observer { it?.let { showNetworkError(it) }},
            httpErrorConsumer = Observer { it?.let { showHttpError(it)}})
    }

    private fun teamDetailsLoadedSuccessfully(it: TeamDetailsEntity) {

    }

    private fun showLoading(it: Boolean) {

    }
    
    private fun showHttpError(it: PremierLeagueException) {
        activity?.showShortToast(it.message?:getString(R.string.lbl_common_error))
    }

    private fun showNetworkError(it: PremierLeagueException) {
        activity?.showShortToast(getString(R.string.lbl_network_error))
    }

    private fun showServerDownError(it: PremierLeagueException) {
        activity?.showShortToast(getString(R.string.lbl_common_error))
    }

    private fun showTimeOutError(it: PremierLeagueException) {
        activity?.showShortToast(getString(R.string.lbl_common_error))
    }

    // endregion initObservers


    companion object {
        private const val TEAM_ID = "teamId"
        fun newInstance(teamId: Int) = TeamDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(TEAM_ID, teamId)
            }
        }
    }

}