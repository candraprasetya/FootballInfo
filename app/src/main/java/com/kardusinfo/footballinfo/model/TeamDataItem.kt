package com.kardusinfo.footballinfo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamDataItem(
    val id: Long?,

    @SerializedName("idTeam")
    var mIdTeam: String? = null,

    @SerializedName("strTeam")
    var mTeamName: String? = null,

    @SerializedName("strTeamBadge")
    var mTeamBadge: String? = null,

    @SerializedName("intFormedYear")
    var mTeamFormedYear: String? = null,

    @SerializedName("strStadium")
    var mTeamStadium: String? = null,

    @SerializedName("strStadiumThumb")
    var mStadiumThumb: String? = null,

    @SerializedName("strTeamFanart1")
    var mTeamFanArt1: String? = null,

    @SerializedName("strTeamFanart2")
    var mTeamFanArt2: String? = null,

    @SerializedName("strDescriptionEN")
    var mTeamDescription: String? = null ) : Parcelable

