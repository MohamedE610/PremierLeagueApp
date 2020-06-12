package com.example.premierleagueapp.features.teams.allteams.presentation.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.presentation.extensions.loadFromUrl
import com.example.premierleagueapp.core.presentation.extensions.loadSVGFromUrl
import com.example.premierleagueapp.core.presentation.views.SimpleBaseAdapter
import com.example.premierleagueapp.core.presentation.views.SimpleBaseViewHolder
import com.example.premierleagueapp.features.teams.allteams.presentation.model.TeamUI
import kotlinx.android.synthetic.main.item_team.view.*

class TeamsAdapter constructor(
    private val onItemClicked: (team: TeamUI) -> Unit,
    private val onFavouriteItemClicked: (team: TeamUI, position: Int) -> Unit
) :
    SimpleBaseAdapter<TeamUI, TeamViewHolder>() {

    override fun getViewHolder(view: View, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            view,
            onItemClicked,
            onFavouriteItemClicked
        )
    }

    override fun getLayoutResourceId(viewType: Int): Int {
        return R.layout.item_team
    }

}

class TeamViewHolder(
    view: View,
    onItemClicked: (team: TeamUI) -> Unit,
    onFavouriteItemClicked: (team: TeamUI, position: Int) -> Unit
) : SimpleBaseViewHolder<TeamUI>(view) {
    var team: TeamUI? = null

    init {
        itemView.setOnClickListener {
            team?.let { onItemClicked(it) }
        }

        itemView.btnTeamWebsite.setOnClickListener {
            team?.let { openTeamWebSite(it.website) }
        }

        itemView.imgTeamFav.setOnClickListener {
            team?.let {
                onFavouriteItemClicked(it, bindingAdapterPosition)
            }
        }
    }

    private fun openTeamWebSite(website: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
        startActivity(itemView.context, browserIntent, null)
    }

    override fun bind(item: TeamUI) {
        team = item
        itemView.imgTeam.loadSVGFromUrl(
            item.crestUrl,
            placeholder = R.drawable.ic_flag_placeholder,
            error = R.drawable.ic_flag_placeholder
        )

        itemView.tvTeamName.text = item.name
        itemView.tvTeamClubColors.text = item.clubColors
        itemView.tvTeamVenue.text = item.venue

        itemView.imgTeamFav.setImageResource(
            if (item.isFavourite)
                R.drawable.ic_favourite
            else
                R.drawable.ic_unfavourite
        )

    }
}