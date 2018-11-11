package com.kardusinfo.footballinfo.activity.details

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.adapter.TeamDetailAdapter
import com.kardusinfo.footballinfo.database.FavTeamDB
import com.kardusinfo.footballinfo.database.database
import com.kardusinfo.footballinfo.model.TeamDataItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class TeamDetailsActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var teamData: TeamDataItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_team)

        teamData = intent.getParcelableExtra("team_data")

        favoriteState()

        Picasso.get().load(teamData.mTeamBadge).into(img_team_detail)
        tv_team_year_detail.text = teamData.mTeamFormedYear
        tv_team_stadium_detail.text = teamData.mTeamStadium

        if (teamData.mStadiumThumb.isNullOrEmpty()){
            img_background_detail.setImageResource(R.drawable.bg)
        } else {
            Picasso.get().load(teamData.mStadiumThumb).into(img_background_detail)
        }

        viewpager_team_detail.adapter = TeamDetailAdapter(supportFragmentManager, teamData.mTeamDescription, teamData.mIdTeam)
        tabs_team_detail.setupWithViewPager(viewpager_team_detail)

        setSupportActionBar(toolbar_team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = teamData.mTeamName
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(detail)
        menuInflater.inflate(R.menu.fav_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav_fill)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav)
    }

    // fungsi add & remove dan set icon favorite pada saat menu fav di click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            android.R.id.home -> {
                super.onBackPressed()
                true }

            R.id.add_to_favorite -> {
                if (isFavorite)  removeToFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavTeamDB.TABLE_FAV_TEAM,
                    FavTeamDB.ID_TEAM to teamData.mIdTeam,
                    FavTeamDB.NAME_TEAM to teamData.mTeamName,
                    FavTeamDB.IMAGE_TEAM to teamData.mTeamBadge,
                    FavTeamDB.YEAR_TEAM to teamData.mTeamFormedYear,
                    FavTeamDB.STADIUM_TEAM to teamData.mTeamStadium,
                    FavTeamDB.STADIUM_THUMB to teamData.mStadiumThumb,
                    FavTeamDB.FANART1_TEAM to teamData.mTeamFanArt1,
                    FavTeamDB.FANART2_TEAM to teamData.mTeamFanArt2,
                    FavTeamDB.DESC_TEAM to teamData.mTeamDescription)


            }
            snackbar(detail_activity, "Added to Your Favorite Team").show()
        }catch (e: SQLiteConstraintException){
            snackbar(detail_activity, e.localizedMessage).show()
        }
    }

    private fun removeToFavorite() {
        try {
            database.use {
                delete(FavTeamDB.TABLE_FAV_TEAM, "( ID_TEAM = {id_team} )",
                    "id_team" to teamData.mIdTeam.toString())
            }
            snackbar(detail_activity, "Remove From Favorite Team").show()
        }catch (e: SQLiteConstraintException){
            snackbar(detail_activity, e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavTeamDB.TABLE_FAV_TEAM)
                .whereArgs("( ID_TEAM = {id_team})",
                    "id_team" to teamData.mIdTeam.toString() )
            val favoriteTeam = result.parseList(classParser<TeamDataItem>())
            if (!favoriteTeam.isEmpty()) isFavorite = true
        }
    }
}
