package com.example.companymeetingscheduler.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.companymeetingscheduler.R
import com.example.companymeetingscheduler.datamodel.MeetingModel
import com.example.companymeetingscheduler.utils.Utils
import kotlinx.android.synthetic.main.row_item_meeting_list.view.*

class MeetingListAdapter(private val meetingsList : ArrayList<MeetingModel?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_item_meeting_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.apply {
                tv_descp_list?.text = meetingsList[position]?.description
                tv_time?.text = "${Utils.convert24hrsto12hrs(meetingsList[position]?.start_time)} - ${
                Utils.convert24hrsto12hrs(meetingsList[position]?.end_time)}"
            }

        }
    }

    override fun getItemCount(): Int {
        return meetingsList.count()
    }

    fun updateItem(itemList : ArrayList<MeetingModel>){
        meetingsList.apply {
            clear()
            addAll(itemList)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tv_time = view.tv_time
        val tv_descp_list = view.tv_descp_list
    }

}