package com.example.premierleagueapp.features.teams.favouriteteams.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.example.premierleagueapp.R
import com.example.premierleagueapp.common.teamfavourite.presentation.viewmodel.TeamFavouriteViewModel
import com.example.premierleagueapp.core.presentation.extensions.showShortToast
import com.example.premierleagueapp.core.presentation.viewmodel.ViewModelFactory
import com.example.premierleagueapp.features.teams.allteams.presentation.model.TeamUI
import com.example.premierleagueapp.features.teams.allteams.presentation.view.adapter.FooterAdapter
import com.example.premierleagueapp.features.teams.allteams.presentation.view.adapter.TeamsAdapter
import com.example.premierleagueapp.features.teams.favouriteteams.presentation.viewmodel.FavouriteTeamsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favourite_teams.*
import javax.inject.Inject


class FavouriteTeamsFragment : Fragment() {

    @Inject
    lateinit var favoriteTeamsViewModelFactory: ViewModelFactory<FavouriteTeamsViewModel>
    private val favouriteTeamsViewModel by lazy {
        ViewModelProvider(
            this,
            favoriteTeamsViewModelFactory
        ).get(FavouriteTeamsViewModel::class.java)
    }

    @Inject
    lateinit var teamFavouriteViewModelFactory: ViewModelFactory<TeamFavouriteViewModel>
    private val teamFavouriteViewModel by lazy {
        ViewModelProvider(
            activity!!,
            teamFavouriteViewModelFactory
        ).get(TeamFavouriteViewModel::class.java)
    }

    private val mergeAdapter = MergeAdapter()
    private val teamsAdapter = TeamsAdapter(this::onItemClicked, this::onFavouriteItemClicked)
    private val footerAdapter = FooterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        getFavouriteTeams()
    }

    private fun getFavouriteTeams() {
        favouriteTeamsViewModel.getFavouriteTeams()
    }

    // region initObservers

    private fun initObservers() {
        favouriteTeamsViewModel.favouriteTeamsObservableResource.observe(this,
            successObserver = Observer { it?.let { favouriteTeamsLoadedSuccessfully(it) } },
            commonErrorObserver = Observer { it?.let { showCommonError() } })
    }

    private fun favouriteTeamsLoadedSuccessfully(it: List<TeamUI>) {
        if (it.isEmpty()) {
            return
        }
        updateTeamLis(it)
    }

    private fun updateTeamLis(it: List<TeamUI>) {
        teamsAdapter.data.clear()
        teamsAdapter.data.addAll(it)
        teamsAdapter.notifyDataSetChanged()
    }

    private fun showCommonError() {
        activity?.showShortToast(getString(R.string.lbl_common_error))
    }

    // endregion initObservers

    // region initViews

    private fun initViews() {
        setUpTeamsRecyclerView()
    }

    private fun setUpTeamsRecyclerView() {
        mergeAdapter.addAdapter(teamsAdapter)
        mergeAdapter.addAdapter(footerAdapter)
        rvFavouriteTeams.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvFavouriteTeams.adapter = mergeAdapter
    }

    private fun onItemClicked(team: TeamUI) {
        activity?.showShortToast(team.name)
    }

    private fun onFavouriteItemClicked(team: TeamUI, position: Int) {
        if (team.isFavourite) {
            teamFavouriteViewModel.markTeamAsUnFavourite(teamId = team.id)
            teamsAdapter.data.removeAt(position)
            teamsAdapter.notifyDataSetChanged()
            team.isFavourite = !team.isFavourite
            teamFavouriteViewModel.lastUpdatedTeam.value = team
        }
    }

    // endregion initViews

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteTeamsFragment()
    }
}