package com.kardusinfo.footballinfo.presenter

import com.google.gson.Gson
import com.kardusinfo.footballinfo.Utils.CoroutineContextProvider
import com.kardusinfo.footballinfo.activity.interfacenya.SearchEventView
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.api.TheSportDbApi
import com.kardusinfo.footballinfo.model.EventDataResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchEventPresenter(private val view: SearchEventView,
                           private val apiRequest: ApiRepository,
                           private val gson: Gson,
                           private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getSearchEvent(teamName: String?) {
        view.showProgress()
        async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDbApi.getSearchMatches(teamName)),
                       EventDataResponse::class.java )
            }
            view.showListEvent(data.await().event)
            view.hideProgress()
        }
    }
}