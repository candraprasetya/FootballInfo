package com.kardusinfo.footballinfo.activity.interfacenya

import com.kardusinfo.footballinfo.model.TeamDataItem

interface TeamView {
    fun showloading()
    fun hideloading()
    fun showTeamListData(data: List<TeamDataItem>)
}