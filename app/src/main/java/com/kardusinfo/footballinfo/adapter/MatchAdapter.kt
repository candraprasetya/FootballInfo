package com.kardusinfo.footballinfo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kardusinfo.footballinfo.activity.fragments.NextMatchFragment
import com.kardusinfo.footballinfo.activity.fragments.PrevMatchFragment

class MatchAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? = when(position) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        0 -> PrevMatchFragment()
        1 -> NextMatchFragment()
        else -> null
    }

    override fun getCount(): Int {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Prev Match"
            1 -> "Next Match"
            else -> null
        }
    }

}