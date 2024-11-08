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
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.util.generator.DateTimeGenerator
import com.saboon.myprograms.view.FragmentSubjectsDirections

class SubjectsFragmentRecyclerAdapter:
    RecyclerView.Adapter<SubjectsFragmentRecyclerAdapter.SubjectViewHolder>() {
    class SubjectViewHolder(view: View): RecyclerView.ViewHolder(view)


    private val diffUtil = object : DiffUtil.ItemCallback<ModelSubject>() {
        override fun areItemsTheSame(oldItem: ModelSubject, newItem: ModelSubject): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ModelSubject, newItem: ModelSubject): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    // TODO: listeden bir item silindiginde tahminimce burada listenin boyutu guncellenmiyor.
    //  silinen itemden sonra gelen itemlerin indis numaralari bir azalmasi gerekirken ayni kaliyor.
    //  boylece en son itemi acmaya calisinca arrayoutofbount hatasi aliyor.

    var subjects: List<ModelSubject>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_subject_list_item_fragment_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val textSubjectTitle : TextView = holder.itemView.findViewById(R.id.subjectFragment_recyclerView_subjectTitle)
        val textSubjectCreatedDate: TextView = holder.itemView.findViewById(R.id.subjectFragment_recyclerView_subjectCreatedDate)


        textSubjectTitle.text = subjects[position].title
        textSubjectCreatedDate.text = DateTimeGenerator().convertLongToDateTime(subjects[position].dateCreated,"dd MMM yyyy")

        holder.itemView.setOnClickListener {
            val action = FragmentSubjectsDirections.actionFragmentSubjectsToFragmentSubjectDetails(subjects[position].id, subjects[position].ownerId)
            it.findNavController().navigate(action)
        }
    }
}