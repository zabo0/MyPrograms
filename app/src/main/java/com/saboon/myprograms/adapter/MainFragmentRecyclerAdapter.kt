package com.saboon.myprograms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.R
import com.saboon.myprograms.viewmodel.VMEvent

class MainFragmentRecyclerAdapter: RecyclerView.Adapter<MainFragmentRecyclerAdapter.MainFragmentViewHolder>() {
    class MainFragmentViewHolder(view: View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main_list_item_fragment_main,parent, false)
        return MainFragmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 7 //days of wee
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        val textDay: TextView = holder.itemView.findViewById(R.id.mainFragmentRecyclerView_dayName)
        val textDate: TextView = holder.itemView.findViewById(R.id.mainFragmentRecyclerView_date)
        val recyclerChild: RecyclerView = holder.itemView.findViewById(R.id.mainFragmentRecyclerView_recyclerView)



    }
}