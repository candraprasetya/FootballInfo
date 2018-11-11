package com.kardusinfo.footballinfo.activity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.activity.details.TeamDetailsActivity
import com.kardusinfo.footballinfo.adapter.FavTeamAdapter
import com.kardusinfo.footballinfo.database.FavTeamDB
import com.kardusinfo.footballinfo.database.database
import com.kardusinfo.footballinfo.model.TeamDataItem
import kotlinx.android.synthetic.main.fragment_fav_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavTeamFragment : Fragment() {

    private var favoriteTeam : MutableList<TeamDataItem> = mutableListOf()
    private lateinit var adapter: FavTeamAdapter
    private lateinit var rvTeam : RecyclerView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavTeamAdapter(favoriteTeam){
            ctx.startActivity<TeamDetailsActivity>("team_data" to it)
        }

        rvTeam = rv_fav_team
        rvTeam.layoutManager = LinearLayoutManager(ctx)
        rvTeam.adapter = adapter
        showFavoriteTeam()

        fav_team_swipe_refresh.onRefresh {
            favoriteTeam.clear()
            showFavoriteTeam()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fav_team, container, false)
    }

    private fun showFavoriteTeam() {
        context?.database?.use {
            fav_team_swipe_refresh.isRefreshing = false
            val result = select(FavTeamDB.TABLE_FAV_TEAM)
            val favorite = result.parseList(classParser<TeamDataItem>())
            favoriteTeam.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}
