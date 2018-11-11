package com.kardusinfo.footballinfo.activity.interfacenya

import com.kardusinfo.footballinfo.model.EventDataItem

interface SearchEventView {
    fun showProgress()
    fun hideProgress()
    fun showListEvent(event: List<EventDataItem>)
}