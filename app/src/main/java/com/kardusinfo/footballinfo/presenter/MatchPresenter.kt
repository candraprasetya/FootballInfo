package com.kardusinfo.footballinfo.presenter

import com.google.gson.Gson
import com.kardusinfo.footballinfo.Utils.CoroutineContextProvider
import com.kardusinfo.footballinfo.activity.interfacenya.MatchEventView
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.api.TheSportDbApi
import com.kardusinfo.footballinfo.model.MatchDataItemResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter (private val matchEventView: MatchEventView,
                      private val apiRequest: ApiRepository,
                      private val gson: Gson
                      , private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getMatchPrevData(league: String?){
        async(context.main) {
            val dataMatch = bg {
                gson.fromJson(apiRequest
                    .doRequest(TheSportDbApi.getPrevMatch(league))
                    , MatchDataItemResponse::class.java
                )
            }

            matchEventView.showDataMatchList(dataMatch.await().events)
        }
    }

    fun getMatchNextData(league: String?) {
        async(context.main) {
            val dataMatch = bg {
                gson.fromJson( apiRequest.doRequest(TheSportDbApi.getNextMatch(league))
                    , MatchDataItemResponse::class.java )
            }
            matchEventView.showDataMatchList(dataMatch.await().events)
        }
    }
}
