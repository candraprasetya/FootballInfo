package com.kardusinfo.footballinfo.activity.details

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.R.drawable.ic_fav
import com.kardusinfo.footballinfo.R.drawable.ic_fav_fill
import com.kardusinfo.footballinfo.R.id.add_to_favorite
import com.kardusinfo.footballinfo.R.menu.fav_menu
import com.kardusinfo.footballinfo.activity.interfacenya.DetailMatchEventView
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.database.FavMatchDB
import com.kardusinfo.footballinfo.database.database
import com.kardusinfo.footballinfo.model.BadgeItem
import com.kardusinfo.footballinfo.model.DetailMatchDataItem
import com.kardusinfo.footballinfo.presenter.MatchDetailPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.SimpleDateFormat

class MatchDetailsActivity : AppCompatActivity(), DetailMatchEventView {

    private lateinit var detailPresenter: MatchDetailPresenter
    private lateinit var progressBar: ProgressBar

     private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var id_event : String
    private var home_team : String? = null
    private var away_team : String? = null
    private var home_score : String? = null
    private var away_score : String? = null
    private var date_event : String? = null
    private var time_event : String? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_match)

        val intent = intent

        id_event    = intent.getStringExtra("id_event")
        home_team   = intent.getStringExtra("home_team")
        home_score  = intent.getStringExtra("home_score")
        away_team   = intent.getStringExtra("away_team" )
        away_score  = intent.getStringExtra("away_score")
        date_event  = intent.getStringExtra("date_event")
        time_event  = intent.getStringExtra("time_event")

        progressBar = progress_bar_details

        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val formatGMT = SimpleDateFormat("E, dd MMM yyyy")
        val dateParse = formatDate.parse(date_event)
        val dateEvent = formatGMT.format(dateParse)

        tv_date_event_details.text  = dateEvent
        tv_time_event_details.text = time_event
        tv_home_team_details.text   = home_team
        tv_home_score_details.text  = home_score
        tv_away_team_details.text   = away_team
        tv_away_score_details.text  = away_score

        showProgress(progressBar)

        favoriteState()

        val apiReq = ApiRepository()
        val gson = Gson()
        detailPresenter = MatchDetailPresenter(this, apiReq, gson)

        detailPresenter.getMatchEventDetail(id_event)

        if(home_team.equals(null) || away_team.equals(null)) {
            home_team = "Barcelona"
            away_team = "Real Madrid"}

        detailPresenter.getTeamBadge(home_team, "Home")
        detailPresenter.getTeamBadge(away_team, "Away")

    }

    override fun showProgress(progressBar: ProgressBar) {
        progressBar.visibility= View.VISIBLE
    }

    override fun hideProgress(progressBar: ProgressBar) {
        progressBar.visibility= View.GONE
    }

    override fun showDetailMatch(data: List<DetailMatchDataItem>) {

        val home_Formation      = getDataList(data[0].mHomeFormation.toString().trim())
        val home_GoalDetails    = getDataList(data[0].mHomeGoalDetails)
        val home_Shot           = getDataList(data[0].mHomeShots)
        val home_GoalKeeper     = getDataList(data[0].mHomeLineupGoalkeeper)
        val home_Defense        = getDataList(data[0].mHomeLineupDefense)
        val home_Midfield       = getDataList(data[0].mHomeLineupMid)
        val home_Forward        = getDataList(data[0].mHomeLineupForward)
        val home_Substitutes    = getDataList(data[0].mHomeLineupSubstitutes)

        setToTextDetails(home_Formation, tv_away_formation)
        setToTextDetails(home_GoalDetails, tv_home_goal_details)
        setToTextDetails(home_Shot, tv_home_shots)
        setToTextDetails(home_GoalKeeper, tv_home_goalKeeper)
        setToTextDetails(home_Defense, tv_home_defense)
        setToTextDetails(home_Midfield, tv_home_midfield)
        setToTextDetails(home_Forward, tv_home_forward)
        setToTextDetails(home_Substitutes, tv_home_substitutes)

        val away_Formation      = data[0].mAwayFormation.toString().trim()
        val away_GoalDetails    = getDataList(data[0].mAwayGoalDetails)
        val away_Shot           = getDataList(data[0].mAwayShots)
        val away_GoalKeeper     = getDataList(data[0].mAwayLineupGoalkeeper)
        val away_Defense        = getDataList(data[0].mAwayLineupDefense)
        val away_Midfield       = getDataList(data[0].mAwayLineupMid)
        val away_Forward        = getDataList(data[0].mAwayLineupForward)
        val away_Substitutes    = getDataList(data[0].mAwayLineupSubstitutes)

        tv_away_formation.text = getDataString("",away_Formation)
        setToTextDetails(away_GoalDetails, tv_away_goal_details)
        setToTextDetails(away_Shot, tv_away_shots)
        setToTextDetails(away_GoalKeeper, tv_away_goalKeeper)
        setToTextDetails(away_Defense, tv_away_defense)
        setToTextDetails(away_Midfield, tv_away_midfield)
        setToTextDetails(away_Forward, tv_away_forward)
        setToTextDetails(away_Substitutes, tv_away_substitutes)

        hideProgress(progressBar)

    }

    override fun showHomeTeamBadge(data: List<BadgeItem>) {
        Picasso.get().load(data[0].mTeamBadge).into(img_home_badge)
    }

    override fun showAwayTeamBadge(data: List<BadgeItem>) {
        Picasso.get().load(data[0].mTeamBadge).into(img_away_badge)
    }

    private fun getDataString(dataText: String?, value: String): String {
        return if (value != "null")
            getString(R.string.textDetails,dataText, value)
        else
            getString(R.string.textDetails, ""," - ")
    }

    private fun getDataList (data: String?): List<String> {
        return data.toString().split(";")
    }

    private fun setToTextDetails(listData: List<String> , tv: TextView){
        for (value in listData) {
            tv.text = getDataString(tv.text.toString(), value.trim() )
        }
    }


    // Membuat Favorite menu pada toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(detail)
        menuInflater.inflate(fav_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_fav_fill)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_fav)
    }

    // fungsi add & remove dan set icon favorite pada saat menu fav di click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            add_to_favorite -> {
                if (isFavorite)  removeToFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        swipeRefreshLayout = detail_swipe_refresh

        try {
            database.use {
                insert(FavMatchDB.TABLE_FAV_MATCH,
                    FavMatchDB.ID_EVENT to id_event,
                    FavMatchDB.DATE_EVENT to date_event,
                    FavMatchDB.HOME_TEAM to home_team,
                    FavMatchDB.AWAY_TEAM to away_team,
                    FavMatchDB.HOME_SCORE to home_score,
                    FavMatchDB.AWAY_SCORE to away_score,
                    FavMatchDB.TIME_EVENT to time_event)
            }
            snackbar(swipeRefreshLayout, "Added to Your Favorite Match").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun removeToFavorite() {

        swipeRefreshLayout = detail_swipe_refresh

        try {
            database.use {
                delete(FavMatchDB.TABLE_FAV_MATCH, "( ID_EVENT = {id_event} )",
                    "id_event" to id_event)
            }
            snackbar(swipeRefreshLayout, "Remove From Favorite Match").show()
        }catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavMatchDB.TABLE_FAV_MATCH)
                .whereArgs("( ID_EVENT = {id_event})",
                    "id_event" to id_event)
            val favoriteMatch = result.parseList(classParser<FavMatchDB>())
            if (!favoriteMatch.isEmpty()) isFavorite = true
        }
    }

}
