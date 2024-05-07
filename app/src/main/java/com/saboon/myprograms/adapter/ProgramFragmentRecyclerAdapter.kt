package com.saboon.myprograms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.R
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.util.generator.DateTimeGenerator
import com.saboon.myprograms.view.FragmentProgramsDirections

class ProgramFragmentRecyclerAdapter():RecyclerView.Adapter<ProgramFragmentRecyclerAdapter.FragmentProgramViewHolder>() {


    private var response : ((String, String) -> Unit)? = null



    class FragmentProgramViewHolder(view: View):RecyclerView.ViewHolder(view)



    //bu adaptor program fragmentinin listesini ayarlar

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_program_list_item_fragment_program,parent,false)
        return FragmentProgramViewHolder(view)
    }

    override fun getItemCount(): Int {
        return programs.size
    }


    fun setOnItemClickListener(listener: (String , String) -> Unit) {
        response = listener
    }

    override fun onBindViewHolder(holder: FragmentProgramViewHolder, position: Int) {
        val programTitle: TextView = holder.itemView.findViewById(R.id.programTitle)
        val programCreatedDate: TextView = holder.itemView.findViewById(R.id.programCreatedDate)

        val deleteButton: ImageView = holder.itemView.findViewById(R.id.programDelete)
        val editButton: ImageView = holder.itemView.findViewById(R.id.programEdit)

        programTitle.text = programs[position].title
        programCreatedDate.text = DateTimeGenerator().convertLongToDateTime(programs[position].dateModified, "dd MMM yyyy / HH:mm")


        deleteButton.setOnClickListener {
            response?.let {
                it(programs[position].id, "delete")
            }
        }

        editButton.setOnClickListener {
            response?.let {
                it(programs[position].id, "edit")
            }
        }

        holder.itemView.setOnClickListener {
            val action = FragmentProgramsDirections.actionProgramsFragmentToFragmentMain(programs[position].id)
            it.findNavController().navigate(action)
        }

    }
}
