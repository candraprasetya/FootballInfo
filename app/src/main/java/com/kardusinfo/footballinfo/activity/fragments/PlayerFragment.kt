package com.kardusinfo.footballinfo.activity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.activity.details.PlayerDetailsActivity
import com.kardusinfo.footballinfo.activity.interfacenya.PlayerView
import com.kardusinfo.footballinfo.adapter.PlayerAdapter
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.model.PlayerDataItem
import com.kardusinfo.footballinfo.model.TeamDataItem
import com.kardusinfo.footballinfo.presenter.PlayerPresenter
import kotlinx.android.synthetic.main.fragment_player.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class PlayerFragment : Fragment(), PlayerView {


    private var id: String? = null
    private lateinit var teamData: TeamDataItem
    private var playerData: MutableList<PlayerDataItem> = mutableListOf()
    private lateinit var recyclerViewPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter : PlayerPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        teamData = activity?.intent!!.getParcelableExtra("team_data")
        id = teamData.mIdTeam
        Log.d("debug","Id Team : " +id)

        progressBar = progress_bar_player
        swipeRefreshLayout = swipe_refresh_player

        adapter = PlayerAdapter(playerData) {

            ctx.startActivity<PlayerDetailsActivity>("player_data" to it )
        }

        recyclerViewPlayer = recyclerview_player
        recyclerViewPlayer.layoutManager = LinearLayoutManager(ctx)

        recyclerViewPlayer.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)

        presenter.getPlayerListData(id)

        swipeRefreshLayout.onRefresh {
            presenter.getPlayerListData(id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showPlayerListData(data: List<PlayerDataItem>) {
        swipeRefreshLayout.isRefreshing = false
        playerData.clear()
        playerData.addAll(data)
        adapter.notifyDataSetChanged()
        hideProgress()
    }

}
