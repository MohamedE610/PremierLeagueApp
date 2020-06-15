package com.example.premierleagueapp.features.teams.allteams.presentation.view.fragment

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
import com.example.premierleagueapp.core.presentation.extensions.hide
import com.example.premierleagueapp.core.presentation.extensions.showShortToast
import com.example.premierleagueapp.core.presentation.extensions.visible
import com.example.premierleagueapp.core.presentation.viewmodel.ViewModelFactory
import com.example.premierleagueapp.core.presentation.views.EndlessRecyclerOnScrollListener
import com.example.premierleagueapp.features.teams.allteams.presentation.model.TeamUI
import com.example.premierleagueapp.features.teams.allteams.presentation.view.adapter.FooterAdapter
import com.example.premierleagueapp.features.teams.allteams.presentation.view.adapter.TeamsAdapter
import com.example.premierleagueapp.features.teams.allteams.presentation.viewmodel.TeamsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_all_teams.*
import javax.inject.Inject

class AllTeamsFragment : Fragment() {

    @Inject
    lateinit var teamsViewModelFactory: ViewModelFactory<TeamsViewModel>
    private val teamsViewModel by lazy {
        ViewModelProvider(this, teamsViewModelFactory).get(TeamsViewModel::class.java)
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
        return inflater.inflate(R.layout.fragment_all_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        loadFirstPageForTeamsFromDB()
    }

    private fun loadFirstPageForTeamsFromDB() {
        teamsViewModel.loadFirstPage()
    }

    // region initObservers

    private fun initObservers() {
        observerOnLastUpdatedTeam()
        observeOnLoadFirstPageForTeams()
        observeOnLoadMoreTeams()
    }

    // observe when team.isFavourite value change in favourite list
    // to update current team in all list
    private fun observerOnLastUpdatedTeam() {
        teamFavouriteViewModel.lastUpdatedTeam.observe(viewLifecycleOwner,
            Observer {
                it?.let {
                    updateTeam(it)
                }
            })
    }

    private fun updateTeam(team: TeamUI) {
        val teamIndex = teamsAdapter.data.indexOfFirst { it.id == team.id }
        if (teamIndex >= 0 && teamIndex < teamsAdapter.data.size) {
            teamsAdapter.data[teamIndex].isFavourite = team.isFavourite
            teamsAdapter.notifyItemChanged(teamIndex)
        }
    }

    private fun observeOnLoadFirstPageForTeams() {
        teamsViewModel.teamsObservableResource.observe(viewLifecycleOwner,
            successObserver = Observer { it?.let { firstPageTeamsLoadedSuccessfully(it) } },
            commonErrorObserver = Observer { it?.let { showCommonError() } },
            loadingObserver = Observer { it?.let { showLoading(it) } })
    }

    private fun observeOnLoadMoreTeams() {
        teamsViewModel.moreTeamsObservableResource.observe(viewLifecycleOwner,
            successObserver = Observer { it?.let { moreTeamsLoadedSuccessfully(it) } },
            commonErrorObserver = Observer { it?.let { showCommonError() } },
            loadingObserver = Observer { it?.let { showLoadMoreLoading(it) } })
    }

    private fun firstPageTeamsLoadedSuccessfully(it: List<TeamUI>) {
        if (it.isEmpty()) {
            activity?.showShortToast(getString(R.string.lbl_empty_error_msg))
            return
        }
        //teamsAdapter.data.clear()
        updateTeamLis(it)
        setOnTeamsRecyclerViewOnScrollListener()
    }

    private fun moreTeamsLoadedSuccessfully(it: List<TeamUI>) {
        endlessRecyclerOnScrollListener.isLoadingMore = false
        updateTeamLis(it)
    }

    private fun updateTeamLis(it: List<TeamUI>) {
        teamsAdapter.data.addAll(it)
        teamsAdapter.notifyDataSetChanged()
    }

    // show loading for first time load teams
    // then we will show loading in footer in teams list
    private fun showLoading(it: Boolean) {
        if (it)
            pbAllTeams.visible()
        else
            pbAllTeams.hide()
    }

    private fun showLoadMoreLoading(loading: Boolean) {
        footerAdapter.isLoading = loading
        footerAdapter.notifyDataSetChanged()
    }

    private fun showCommonError() {
        activity?.showShortToast(getString(R.string.lbl_common_error))
        endlessRecyclerOnScrollListener.isLoadingMore = false
    }

    // endregion initObservers

    //region initViews

    private fun initViews() {
        setUpTeamsRecyclerView()
    }

    private fun setOnTeamsRecyclerViewOnScrollListener() {
        rvAllTeams.addOnScrollListener(endlessRecyclerOnScrollListener)
    }

    private fun setUpTeamsRecyclerView() {
        mergeAdapter.addAdapter(teamsAdapter)
        mergeAdapter.addAdapter(footerAdapter)
        rvAllTeams.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvAllTeams.adapter = mergeAdapter
    }

    private fun onItemClicked(team: TeamUI) {
        activity?.showShortToast(team.name)
    }

    private fun onFavouriteItemClicked(team: TeamUI, position: Int) {
        if (team.isFavourite)
            teamFavouriteViewModel.markTeamAsUnFavourite(teamId = team.id)
        else
            teamFavouriteViewModel.markTeamAsFavourite(teamId = team.id)

        team.isFavourite = !team.isFavourite
        teamsAdapter.notifyItemChanged(position)
    }

    //endregion initViews

    // region listeners

    private val endlessRecyclerOnScrollListener by lazy {
        object : EndlessRecyclerOnScrollListener(rvAllTeams.layoutManager as LinearLayoutManager) {
            override fun onLoadMore() {
                teamsViewModel.loadMoreTeamsFromDBM()
            }
        }
    }

    //endregion listeners

    companion object {
        @JvmStatic
        fun newInstance() = AllTeamsFragment()
    }
}