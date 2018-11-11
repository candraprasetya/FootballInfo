package com.kardusinfo.footballinfo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.model.EventDataItem
import kotlinx.android.synthetic.main.match_list.view.*
import java.text.SimpleDateFormat

class SearchEventAdapter(private var eventDataItem: List<EventDataItem>
                         , private val listener: (EventDataItem) -> Unit)
    : RecyclerView.Adapter<ViewHolderSearch>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSearch {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_list, parent, false)
        return ViewHolderSearch(view)
    }

    override fun getItemCount(): Int = eventDataItem.size

    override fun onBindViewHolder(holder: ViewHolderSearch, position: Int) {
        holder.bindItem(eventDataItem[position], listener)
    }

}

class ViewHolderSearch(val view: View): RecyclerView.ViewHolder(view) {

    fun bindItem(eventData: EventDataItem, listener: (EventDataItem) -> Unit){

        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        val formatGMT = SimpleDateFormat("E, dd MMM yyyy")
        val dateParse = formatDate.parse(eventData.mDateEvent)
        val dateEvent = formatGMT.format(dateParse)

        itemView.tv_date_event.text = dateEvent
        itemView.tv_time_event.text = eventData.mTime
        itemView.tv_home_team.text = eventData.mHomeTeam
        itemView.tv_away_team.text = eventData.mAwayTeam
        itemView.tv_home_score.text = eventData.mHomeScore
        itemView.tv_away_score.text = eventData.mAwayScore

        itemView.setOnClickListener {
            listener(eventData)
        }
    }
}