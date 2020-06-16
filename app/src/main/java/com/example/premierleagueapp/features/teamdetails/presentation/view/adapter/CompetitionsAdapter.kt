package com.example.premierleagueapp.features.teamdetails.presentation.view.adapter

import android.view.View
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.data.source.local.entity.ActiveCompetition
import com.example.premierleagueapp.core.presentation.views.SimpleBaseAdapter
import com.example.premierleagueapp.core.presentation.views.SimpleBaseViewHolder
import kotlinx.android.synthetic.main.item_competition.view.*

class CompetitionsAdapter : SimpleBaseAdapter<ActiveCompetition, CompetitionViewHolder>() {
    override fun getViewHolder(view: View, viewType: Int): CompetitionViewHolder {
        return CompetitionViewHolder(view)
    }

    override fun getLayoutResourceId(viewType: Int): Int {
        return R.layout.item_competition
    }

}

class CompetitionViewHolder constructor(view: View) :
    SimpleBaseViewHolder<ActiveCompetition>(view) {
    override fun bind(item: ActiveCompetition) {
        itemView.tvCompetitionArea.text = item.area.name
        itemView.tvCompetitionName.text = item.name
    }
}