package com.example.premierleagueapp.features.teams.allteams.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.presentation.extensions.hide
import com.example.premierleagueapp.core.presentation.extensions.visible
import kotlinx.android.synthetic.main.item_footer.view.*

class FooterAdapter() : RecyclerView.Adapter<FooterViewHolder>() {
    var isLoading = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
        return FooterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: FooterViewHolder, position: Int) {
        if (isLoading)
            holder.itemView.pbLoading.visible()
        else
            holder.itemView.pbLoading.hide()
    }
}

class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)