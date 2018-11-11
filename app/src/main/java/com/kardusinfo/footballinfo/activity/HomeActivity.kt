package com.kardusinfo.footballinfo.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.facebook.stetho.Stetho
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.activity.details.MatchDetailsActivity
import com.kardusinfo.footballinfo.activity.fragments.*
import com.kardusinfo.footballinfo.model.MatchDataItem
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity(),
    PrevMatchFragment.OnFragmentInteractionListener,
    NextMatchFragment.OnFragmentInteractionListener {

    // Overide onFragmentInteraction untuk pass data detail activity
    override fun onFragmentInteraction(item: MatchDataItem) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        startActivity<MatchDetailsActivity>(
            "id_event" to item.mIdEvent
            ,   "date_event" to item.mDateEvent
            ,   "home_team" to item.mHomeTeam
            ,   "home_score" to item.mHomeScore
            ,   "away_team" to item.mAwayTeam
            ,   "away_score" to item.mAwayScore )
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_match -> {
                toast(R.string.title_match_nav)
                // val fragment = PrevMatchFragment.newInstance()
                val fragment = MatchFragment()
                openMatchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_teams -> {
                longToast(R.string.title_teams_nav)
                //val fragment = NextMatchFragment.newInstance()
                val fragment = TeamFragment()
                openMatchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favorite -> {
                longToast(R.string.title_favorite_nav)
                // val fragment = FavoriteMatchesFragment()
                val fragment = FavoritesFragment()
                openMatchFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Stetho.initializeWithDefaults(this)

        openMatchFragment( MatchFragment() )

        btn_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openMatchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
