package com.kardusinfo.footballinfo.activity.interfacenya

import com.kardusinfo.footballinfo.model.MatchDataItem

interface MatchEventView {

    fun showProgress()

    fun hideProgress()

    fun showDataMatchList(data: List<MatchDataItem>)
}