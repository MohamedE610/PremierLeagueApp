package com.example.premierleagueapp.features.teamdetails.presentation.view.adapter

import android.view.View
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.data.source.local.entity.Player
import com.example.premierleagueapp.core.presentation.views.SimpleBaseAdapter
import com.example.premierleagueapp.core.presentation.views.SimpleBaseViewHolder
import kotlinx.android.synthetic.main.item_squad.view.*

class SquadAdapter : SimpleBaseAdapter<Player, SquadViewHolder>() {
    override fun getViewHolder(view: View, viewType: Int): SquadViewHolder {
        return SquadViewHolder(view)
    }

    override fun getLayoutResourceId(viewType: Int): Int {
        return R.layout.item_squad
    }
}

class SquadViewHolder constructor(view: View) : SimpleBaseViewHolder<Player>(view) {
    override fun bind(item: Player) {
        itemView.tvPlayerName.text = item.name
        itemView.tvPlayerPosition.text = item.position
    }
}