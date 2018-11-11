package com.kardusinfo.footballinfo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDataItem(
    @SerializedName("idEvent")
    var mIdEvent: String? = null,

    @SerializedName("dateEvent")
    var mDateEvent: String? = null,

    @SerializedName("strHomeTeam")
    var mHomeTeam: String? = null,

    @SerializedName("intHomeScore")
    var mHomeScore: String? = null,

    @SerializedName("strHomeGoalDetails") // X
    val mHomeGoalDetails: String? = null,

    @SerializedName("strAwayTeam")
    var mAwayTeam: String? = null,

    @SerializedName("intAwayScore")
    var mAwayScore: String? = null,

    @SerializedName("strAwayGoalDetails") // X
    val mAwayGoalDetails: String? = null,

    @SerializedName("strTime")
    var mTime: String? = null
): Parcelable