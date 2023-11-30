package com.saboon.myprograms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.R
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.view.FragmentMainDirections

class MainFragmentProgramsRecyclerAdapter: RecyclerView.Adapter<MainFragmentProgramsRecyclerAdapter.MFPViewHolder>() {

    class MFPViewHolder(view: View):RecyclerView.ViewHolder(view)

    //bu adapter silinecek

    private val diffUtil = object : DiffUtil.ItemCallback<ModelProgram>() {
        override fun areItemsTheSame(oldItem: ModelProgram, newItem: ModelProgram): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ModelProgram, newItem: ModelProgram): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)


    var programs: List<ModelProgram>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MFPViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_program_list_fragment_main,parent,false)
        return MFPViewHolder(view)
    }

    override fun getItemCount(): Int {
        return programs.size
    }

    override fun onBindViewHolder(holder: MFPViewHolder, position: Int) {
        val title: TextView = holder.itemView.findViewById(R.id.recyclerRowMainListDrawerMenu_programName)
        val subjectCount: TextView = holder.itemView.findViewById(R.id.recyclerRowMainListDrawerMenu_subjectCount)

        holder.itemView.apply {
            title.text = programs[position].title
            subjectCount.text = "0"
        }


        holder.itemView.setOnClickListener {
            val action = FragmentMainDirections.actionFragmentMainSelf(programs[position].id)
            it.findNavController().navigate(action)
        }




    }
}