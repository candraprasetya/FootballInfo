package com.kardusinfo.footballinfo.presenter

import com.google.gson.Gson
import com.kardusinfo.footballinfo.Utils.CoroutineContextProvider
import com.kardusinfo.footballinfo.activity.interfacenya.PlayerView
import com.kardusinfo.footballinfo.api.ApiRepository
import com.kardusinfo.footballinfo.api.TheSportDbApi
import com.kardusinfo.footballinfo.model.PlayerDataItemResponse
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val apiRequest: ApiRepository,
                      private val gson: Gson,
                      private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
){

    fun getPlayerListData(id: String?) {
        view.showProgress()

        async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRequest.doRequest(TheSportDbApi.getPlayers(id)),
                    PlayerDataItemResponse::class.java)
            }
            view.showPlayerListData(data.await().player)
            view.hideProgress()

        }
    }
}