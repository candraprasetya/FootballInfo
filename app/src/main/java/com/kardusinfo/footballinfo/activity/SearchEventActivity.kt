package com.kardusinfo.footballinfo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.activity.details.MatchDetailsActivity
import com.kardusinfo.footballinfo.activity.interfacenya.SearchEventView
import com.kardusinfo.footballinfo.adapter.SearchEventAdapter
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.model.EventDataItem
import com.kardusinfo.footballinfo.model.MatchDataItem
import com.kardusinfo.footballinfo.presenter.SearchEventPresenter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class SearchEventActivity : AppCompatActivity(), SearchEventView {

    private var eventDataItem: MutableList<EventDataItem> = mutableListOf()
    private var extraDataEvent: MutableList<MatchDataItem> = mutableListOf()
    //private var listener = null

    private lateinit var presenter: SearchEventPresenter
    private lateinit var adapter: SearchEventAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.hide()

        search_view_event.onActionViewExpanded()
        search_view_event.onQueryTextListener() {
            onQueryTextChange { it ->
                presenter.getSearchEvent(it)
                true
            }
            hideProgress()
        }

        val apiRequest = ApiRepository()
        val gson = Gson()
        presenter = SearchEventPresenter(this,apiRequest,gson)

        ///*
        adapter = SearchEventAdapter(eventDataItem) {
            extraDataEvent.clear()
            extraDataEvent.add(MatchDataItem(
                    it.mIdEvent,
                    it.mDateEvent,
                    it.mHomeTeam,
                    it.mHomeScore,
                    "",
                    it.mAwayTeam,
                    it.mAwayScore,
                    "",
                    it.mTime))
            ctx.startActivity<MatchDetailsActivity>(
                    "id_event" to extraDataEvent[0].mIdEvent,
                    "home_team" to extraDataEvent[0].mHomeTeam,
                    "home_score" to extraDataEvent[0].mHomeScore,
                    "away_team" to extraDataEvent[0].mAwayTeam,
                    "away_score" to extraDataEvent[0].mAwayScore,
                    "date_event" to extraDataEvent[0].mDateEvent,
                    "time_event" to extraDataEvent[0].mTime
             )
        }

        recyclerView = recyclerview_search_event
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipe_search_event.onRefresh {
            presenter.getSearchEvent("Barcelona")
            hideProgress()
        }
    }

    override fun showProgress() {
      progress_bar_search_event.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar_search_event.visibility = View.GONE
    }

    override fun showListEvent(event: List<EventDataItem>) {

        eventDataItem.clear()
        eventDataItem.addAll(event)
        adapter.notifyDataSetChanged()
        hideProgress()
    }
}
