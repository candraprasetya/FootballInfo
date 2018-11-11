package com.kardusinfo.footballinfo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.database.FavMatchDB
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavMatchAdapter(
    private val favoriteMatch: List<FavMatchDB>,
    private val listener: (FavMatchDB) -> Unit)
    : RecyclerView.Adapter<FavMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMatchViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_list, parent, false)
        return FavMatchViewHolder(view)
    }

    override fun getItemCount(): Int = favoriteMatch.size


    override fun onBindViewHolder(holder: FavMatchViewHolder, position: Int) {
        holder.bindItem(favoriteMatch[position], listener)
    }

}

class FavMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val mDateEvent: TextView = view.find(R.id.tv_date_event)
    val mHomeTeam: TextView = view.find(R.id.tv_home_team)
    val mAwayTeam: TextView = view.find(R.id.tv_away_team)
    val mHomeScore: TextView = view.find(R.id.tv_home_score)
    val mAwayScore: TextView = view.find(R.id.tv_away_score)
    val mTime: TextView = view.find(R.id.tv_time_event)

    fun bindItem(itemFav: FavMatchDB, listener: (FavMatchDB) -> Unit) {
        mDateEvent.text = itemFav.dateEvent
        mHomeTeam.text = itemFav.homeTeam
        mAwayTeam.text = itemFav.awayTeam
        mHomeScore.text = itemFav.homeScore
        mAwayScore.text = itemFav.awayScore
        mTime.text = itemFav.time

        itemView.onClick { listener(itemFav) }

    }
}


