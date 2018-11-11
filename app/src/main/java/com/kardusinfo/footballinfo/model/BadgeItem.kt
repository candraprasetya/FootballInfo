package com.kardusinfo.footballinfo.model

import com.google.gson.annotations.SerializedName


data class BadgeItem (
    @SerializedName("strTeamBadge")
    val mTeamBadge: String? = ""
)