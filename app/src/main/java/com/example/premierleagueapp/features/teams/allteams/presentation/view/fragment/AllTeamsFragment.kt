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
        ViewModelProvider(activity!!, teamsViewModelFactory).get(TeamsViewModel::class.java)
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
        observeOnLoadFirstPageForTeams()
        observeOnLoadMoreTeams()
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
        updateTeamLis(it)
        setOnTeamsRecyclerViewOnScrollListener()
    }

    private fun moreTeamsLoadedSuccessfully(it: List<TeamUI>) {
        if (it.isEmpty()) {
            activity?.showShortToast(getString(R.string.lbl_no_more_teams_msg))
            return
        }
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
    }

    // endregion initObservers

    //region initViews

    private fun initViews() {
        setUpTeamsRecyclerView()
    }

    private fun setOnTeamsRecyclerViewOnScrollListener() {
        rvAllTeams.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(rvAllTeams.layoutManager as LinearLayoutManager, 1) {
            override fun onLoadMore() {
                teamsViewModel.loadMoreTeamsFromDBM()
            }
        })
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
            teamsViewModel.markTeamAsUnFavourite(teamId = team.id)
        else
            teamsViewModel.markTeamAsFavourite(teamId = team.id)

        team.isFavourite = !team.isFavourite
        teamsAdapter.notifyItemChanged(position)
    }

    //endregion initViews

    companion object {
        @JvmStatic
        fun newInstance() = AllTeamsFragment()
    }
}