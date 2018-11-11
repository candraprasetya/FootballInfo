package com.kardusinfo.footballinfo.api

import android.net.Uri
import com.kardusinfo.footballinfo.BuildConfig

object TheSportDbApi {

    fun getPrevMatch(league: String? = "4335"): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("eventspastleague.php")
            .appendQueryParameter("id",league)
            .build()
            .toString()
    }

    fun getNextMatch(league: String? = "4335"): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id",league)
            .build().toString()
    }

    fun getDetailMatch(event: String? = ""): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("lookupevent.php")
            .appendQueryParameter("id",event)
            .build().toString()
    }

    fun getBadge(badgeTeam: String? = ""): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("searchteams.php")
            .appendQueryParameter("t",badgeTeam)
            .build().toString()
    }

    fun getTeams(league: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("search_all_teams.php")
            .appendQueryParameter("l", league)
            .build().toString()
    }

    fun getTeamDetail (idTeam: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("lookupteam.php")
            .appendQueryParameter("id", idTeam)
            .build().toString()
    }

      fun getPlayers(idTeam: String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("lookup_all_players.php")
            .appendQueryParameter("id", idTeam)
            .build().toString()
    }

    fun getSearchMatches(teamName: String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("searchevents.php")
            .appendQueryParameter("e", teamName)
            .build().toString()
    }

    fun getSearchTeams(teamName: String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath("searchteams.php")
            .appendQueryParameter("t", teamName)
            .build().toString()
    }

}