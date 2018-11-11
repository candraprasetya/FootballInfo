package com.kardusinfo.footballinfo.activity.interfacenya

import com.kardusinfo.footballinfo.model.PlayerDataItem

interface PlayerView {
    fun showProgress()
    fun hideProgress()
    fun showPlayerListData(data: List<PlayerDataItem>)
}