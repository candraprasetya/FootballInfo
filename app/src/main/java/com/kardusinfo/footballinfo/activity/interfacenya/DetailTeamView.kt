package com.kardusinfo.footballinfo.activity.interfacenya

import android.widget.ProgressBar
import com.kardusinfo.footballinfo.model.TeamDataItem

interface DetailTeamView {
    fun showProgress(progressBar: ProgressBar)

    fun hideProgress(progressBar: ProgressBar)

    fun showDetailTeam (data : List<TeamDataItem>)

}