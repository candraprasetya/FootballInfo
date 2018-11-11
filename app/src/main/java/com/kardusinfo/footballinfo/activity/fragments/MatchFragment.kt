package com.kardusinfo.footballinfo.activity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.activity.SearchEventActivity
import com.kardusinfo.footballinfo.adapter.MatchAdapter
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.startActivity

class MatchFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        viewpager_match.adapter = MatchAdapter(childFragmentManager)
        tabs_match.setupWithViewPager(viewpager_match)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.search_menu_id -> {
                startActivity<SearchEventActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
