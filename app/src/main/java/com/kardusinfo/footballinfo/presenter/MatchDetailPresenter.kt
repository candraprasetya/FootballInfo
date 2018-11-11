package com.kardusinfo.footballinfo.presenter

import com.google.gson.Gson
import com.kardusinfo.footballinfo.Utils.CoroutineContextProvider
import com.kardusinfo.footballinfo.activity.interfacenya.DetailMatchEventView
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.api.TheSportDbApi
import com.kardusinfo.footballinfo.model.BadgeResponse
import com.kardusinfo.footballinfo.model.DetailMatchDataResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter (public val detailMatchEventView: DetailMatchEventView,
                            public val apiRequest: ApiRepository,
                            public val gson: Gson
                            , private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamBadge( team: String?, teamType: String? ){

        doAsync {
            val dataTeam = gson.fromJson(apiRequest
                .doRequest(TheSportDbApi.getBadge(team))
                , BadgeResponse::class.java )

            uiThread {
                if(teamType == "Away")
                    detailMatchEventView.showAwayTeamBadge(dataTeam.teams)
                else
                    detailMatchEventView.showHomeTeamBadge(dataTeam.teams)

            }
        }
    }

    fun getMatchEventDetail(event: String?){

        async(context.main) {
            val dataDetail = bg {
                gson.fromJson(apiRequest.doRequest(
                    TheSportDbApi.getDetailMatch(event))
                    , DetailMatchDataResponse::class.java)
            }
            detailMatchEventView.showDetailMatch(dataDetail.await().events)
        }
    }

}