package com.kardusinfo.footballinfo.activity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.activity.details.MatchDetailsActivity
import com.kardusinfo.footballinfo.adapter.FavMatchAdapter
import com.kardusinfo.footballinfo.database.FavMatchDB
import com.kardusinfo.footballinfo.database.database
import kotlinx.android.synthetic.main.fragment_fav_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh

class FavMatchFragment : Fragment() {

    private var favoriteMatch : MutableList<FavMatchDB> = mutableListOf()
    private lateinit var adapter: FavMatchAdapter
    private lateinit var listMatch : RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavMatchAdapter(favoriteMatch){
            ctx.startActivity<MatchDetailsActivity>("id_event" to "${it.idEvent}",
                "home_team" to "${it.homeTeam}",
                "home_score" to "${it.homeScore}",
                "home_score" to "${it.homeScore}",
                "away_team" to "${it.awayTeam}",
                "away_score" to "${it.awayScore}",
                "away_score" to "${it.awayScore}",
                "date_event" to "${it.dateEvent}",
                "time_event" to "${it.time}")
        }

//        listMatch.layoutManager = LinearLayoutManager(ctx);

        listMatch = rv_fav_match
        listMatch.layoutManager = LinearLayoutManager(ctx)

        listMatch.adapter = adapter
        showFavoriteMatch()

        fav_swipe_refresh.onRefresh {
            favoriteMatch.clear()
            showFavoriteMatch()
        }

    }

    private fun showFavoriteMatch() {
        context?.database?.use {
            fav_swipe_refresh.isRefreshing = false
            val result = select(FavMatchDB.TABLE_FAV_MATCH)
            val favorite = result.parseList(classParser<FavMatchDB>())
            favoriteMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_match, container, false)
    }
}
