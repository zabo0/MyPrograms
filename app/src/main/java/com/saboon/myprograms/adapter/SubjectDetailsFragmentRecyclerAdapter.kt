package com.saboon.myprograms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.R
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject

class SubjectDetailsFragmentRecyclerAdapter:
    RecyclerView.Adapter<SubjectDetailsFragmentRecyclerAdapter.SubjectDetailsViewHolder>() {
    class SubjectDetailsViewHolder(view: View): RecyclerView.ViewHolder(view)



    private val diffUtil = object : DiffUtil.ItemCallback<ModelSubject>() {
        override fun areItemsTheSame(oldItem: ModelSubject, newItem: ModelSubject): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ModelSubject, newItem: ModelSubject): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)


    var subjects: List<ModelSubject>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_subject_list_item_fragment_subject,parent,false)
        return SubjectDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    override fun onBindViewHolder(holder: SubjectDetailsViewHolder, position: Int) {

    }


}