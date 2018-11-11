package com.kardusinfo.footballinfo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kardusinfo.footballinfo.activity.fragments.FavMatchFragment
import com.kardusinfo.footballinfo.activity.fragments.FavTeamFragment

class FavAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when(position) {
            0 -> FavMatchFragment()
            1 -> FavTeamFragment()
            else -> null

        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Matches"
            1 -> "Teams"
            else -> null
        }
    }
}