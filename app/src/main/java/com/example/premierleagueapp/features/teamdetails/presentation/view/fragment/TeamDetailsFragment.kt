package com.example.premierleagueapp.features.teamdetails.presentation.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.premierleagueapp.R
import com.example.premierleagueapp.common.teamfavourite.presentation.viewmodel.TeamFavouriteViewModel
import com.example.premierleagueapp.core.data.source.local.entity.ActiveCompetition
import com.example.premierleagueapp.core.data.source.local.entity.Player
import com.example.premierleagueapp.core.data.source.local.entity.TeamDetailsEntity
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException
import com.example.premierleagueapp.core.presentation.extensions.loadSVGFromUrl
import com.example.premierleagueapp.core.presentation.extensions.showShortToast
import com.example.premierleagueapp.core.presentation.viewmodel.ViewModelFactory
import com.example.premierleagueapp.features.teamdetails.presentation.view.activity.TeamDetailsActivity
import com.example.premierleagueapp.features.teamdetails.presentation.view.adapter.CompetitionsAdapter
import com.example.premierleagueapp.features.teamdetails.presentation.view.adapter.SquadAdapter
import com.example.premierleagueapp.features.teamdetails.presentation.viewmodel.TeamDetailsViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_team_details.*
import kotlinx.android.synthetic.main.fragment_team_details.*
import javax.inject.Inject

class TeamDetailsFragment : Fragment() {

    @Inject
    lateinit var teamDetailsViewModelFactory: ViewModelFactory<TeamDetailsViewModel>
    private val teamDetailsViewModel by lazy {
        ViewModelProvider(this, teamDetailsViewModelFactory).get(TeamDetailsViewModel::class.java)
    }

    @Inject
    lateinit var teamFavouriteViewModelFactory: ViewModelFactory<TeamFavouriteViewModel>
    private val teamFavouriteViewModel by lazy {
        ViewModelProvider(
            activity!!,
            teamFavouriteViewModelFactory
        ).get(TeamFavouriteViewModel::class.java)
    }

    private var isFavouriteBtnClicked = false

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
        val appCompatActivity = (activity as AppCompatActivity)
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        val teamName = arguments?.getString(TEAM_NAME) ?: ""
        activity?.title = teamName
        setOnFabTeamFavouriteClicked()
        addOnBackPressedCallbacks()
    }

    private fun addOnBackPressedCallbacks() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        if (isFavouriteBtnClicked)
                            setResultBack()
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }

    private fun setResultBack() {
        val teamDetails = teamDetailsViewModel.teamDetailsObservableResource.value ?: return
        val returnIntent = Intent()
        returnIntent.putExtra(TEAM_ID, teamDetails.id)
        returnIntent.putExtra(IS_FAVOURITE, teamDetails.isFavourite)
        requireActivity().setResult(RESULT_OK, returnIntent)
    }

    private fun setOnFabTeamFavouriteClicked() {
        fabTeamDetailsFavourite.setOnClickListener {
            val teamDetails = teamDetailsViewModel.teamDetailsObservableResource.value
            teamDetails?.let { team ->
                isFavouriteBtnClicked = true
                if (!team.isFavourite)
                    markTeamDetailsAsFavourite(team)
                else
                    markTeamDetailsAsUnFavourite(team)
            }
        }
    }

    private fun markTeamDetailsAsUnFavourite(team: TeamDetailsEntity) {
        team.isFavourite = false
        fabTeamDetailsFavourite.setImageResource(R.drawable.ic_unfavourite)
        teamFavouriteViewModel.markTeamAsUnFavourite(teamId = team.id)
    }

    private fun markTeamDetailsAsFavourite(team: TeamDetailsEntity) {
        team.isFavourite = true
        fabTeamDetailsFavourite.setImageResource(R.drawable.ic_favourite)
        teamFavouriteViewModel.markTeamAsFavourite(teamId = team.id)
    }

    // endregion initViews


    // region initObservers

    private fun initObservers() {
        teamDetailsViewModel.teamDetailsObservableResource.observe(viewLifecycleOwner,
            successObserver = Observer { it?.let { teamDetailsLoadedSuccessfully(it) } },
            commonErrorObserver = Observer { it?.let { showServerDownError(it) } })
    }

    private fun teamDetailsLoadedSuccessfully(it: TeamDetailsEntity) {
        imgTeamDetails.loadSVGFromUrl(
            it.crestUrl,
            placeholder = R.drawable.ic_flag_placeholder,
            error = R.drawable.ic_flag_placeholder
        )

        tvTeamDetailsClubColors.text = it.clubColors
        tvTeamDetailsVenue.text = it.venue

        setUpCompetitionsRecyclerView(it.activeCompetitions)
        setUpSquadRecyclerView(it.squad)

        if (it.isFavourite)
            fabTeamDetailsFavourite.setImageResource(R.drawable.ic_favourite)
        else
            fabTeamDetailsFavourite.setImageResource(R.drawable.ic_unfavourite)

    }

    private fun setUpSquadRecyclerView(squad: List<Player>) {
        val adapter = SquadAdapter()
        adapter.data.addAll(squad)
        rvTeamDetailsSquad.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTeamDetailsSquad.adapter = adapter
    }

    private fun setUpCompetitionsRecyclerView(activeCompetitions: List<ActiveCompetition>) {
        val adapter = CompetitionsAdapter()
        adapter.data.addAll(activeCompetitions)
        rvTeamDetailsActiveCompetitions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTeamDetailsActiveCompetitions.adapter = adapter
    }

    private fun showServerDownError(it: PremierLeagueException) {
        activity?.showShortToast(getString(R.string.lbl_common_error))
    }

    // endregion initObservers

    companion object {
        private const val TEAM_ID = "teamId"
        private const val TEAM_NAME = "teamName"
        private const val IS_FAVOURITE = "isFavourite"

        fun newInstance(teamId: Int, teamName: String) = TeamDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(TEAM_ID, teamId)
                putString(TEAM_NAME, teamName)
            }
        }
    }

}