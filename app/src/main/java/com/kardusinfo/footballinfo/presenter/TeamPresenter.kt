package com.kardusinfo.footballinfo.presenter

import com.google.gson.Gson
import com.kardusinfo.footballinfo.Utils.CoroutineContextProvider
import com.kardusinfo.footballinfo.activity.interfacenya.TeamView
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.api.TheSportDbApi
import com.kardusinfo.footballinfo.model.TeamDataItemResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView,
                    private val apiRequest: ApiRepository,
                    private val gson: Gson,
                    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getTeamListData(league: String?) {
        view.showloading()

        async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDbApi.getTeams(league)),
                    TeamDataItemResponse::class.java)
            }
            view.showTeamListData(data.await().teams)
            view.hideloading()
        }
    }

    fun getSearchTeamData(teamName: String?) {
        view.showloading()

        async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDbApi.getSearchTeams(teamName)),
                    TeamDataItemResponse::class.java)
            }
            view.showTeamListData(data.await().teams)
            view.hideloading()


        }
    }
}