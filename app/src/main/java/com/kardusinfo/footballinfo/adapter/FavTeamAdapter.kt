package com.kardusinfo.footballinfo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.model.TeamDataItem
import kotlinx.android.synthetic.main.team_list.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavTeamAdapter (private val favoriteTeam: List<TeamDataItem>,
                      private val listener: (TeamDataItem) -> Unit)
    : RecyclerView.Adapter<ViewHolderFavTeam>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFavTeam {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_list, parent, false)
        return ViewHolderFavTeam(view)
    }

    override fun getItemCount(): Int = favoriteTeam.size

    override fun onBindViewHolder(holder: ViewHolderFavTeam, position: Int) {
        holder.bindItem(favoriteTeam[position], listener)
    }

}

class ViewHolderFavTeam(view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(itemFavoriteTeam: TeamDataItem, listener: (TeamDataItem) -> Unit) {
        itemView.name_team_view.text = itemFavoriteTeam.mTeamName
        Glide.with(itemView.context).load(itemFavoriteTeam.mTeamBadge).into(itemView.image_team_view)

        itemView.onClick { listener(itemFavoriteTeam) }
    }

}