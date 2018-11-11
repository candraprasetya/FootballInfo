package com.kardusinfo.footballinfo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.model.TeamDataItem
import kotlinx.android.synthetic.main.team_list.view.*

class TeamAdapter (private val teamDataItem: List<TeamDataItem>,
                   private val listener: (TeamDataItem) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teamDataItem[position], listener)
    }


    override fun getItemCount(): Int = teamDataItem.size

}

class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(teamDataItem: TeamDataItem, listener: (TeamDataItem) -> Unit) {
        itemView.name_team_view.text = teamDataItem.mTeamName
        Glide.with(itemView.context).load(teamDataItem.mTeamBadge).into(itemView.image_team_view)

        itemView.setOnClickListener {
            listener(teamDataItem)
        }

    }

}