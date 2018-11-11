package com.kardusinfo.footballinfo.activity.interfacenya

import android.widget.ProgressBar
import com.kardusinfo.footballinfo.model.BadgeItem
import com.kardusinfo.footballinfo.model.DetailMatchDataItem

interface DetailMatchEventView {

    fun showProgress(progressBar: ProgressBar)

    fun hideProgress(progressBar: ProgressBar)

    fun showDetailMatch (data : List<DetailMatchDataItem>)

    fun showHomeTeamBadge (data: List<BadgeItem>)

    fun showAwayTeamBadge (data: List<BadgeItem>)

}