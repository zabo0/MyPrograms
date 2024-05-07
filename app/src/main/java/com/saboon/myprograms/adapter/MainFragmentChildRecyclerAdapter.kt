package com.saboon.myprograms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.R

class MainFragmentChildRecyclerAdapter:
    RecyclerView.Adapter<MainFragmentChildRecyclerAdapter.MainFragmentChildViewHolder>() {
    class MainFragmentChildViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main_list_child_fragment_main, parent, false)
        return MainFragmentChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainFragmentChildViewHolder, position: Int) {
        val textStartTime: TextView = holder.itemView.findViewById(R.id.mainFragmentChildRecycler_startTime)
        val textEndTime: TextView = holder.itemView.findViewById(R.id.mainFragmentChildRecycler_endTime)
        val textSubjectTitle: TextView = holder.itemView.findViewById(R.id.mainFragmentChildRecycler_subjectTitle)
        val textEventTitle: TextView = holder.itemView.findViewById(R.id.mainFragmentChildRecycler_eventTitle)
        val textEventDesctiption: TextView = holder.itemView.findViewById(R.id.mainFragmentChildRecycler_eventDescription)
        val textEventPlace: TextView = holder.itemView.findViewById(R.id.mainFragmentChildRecycler_eventPlace)
    }


}