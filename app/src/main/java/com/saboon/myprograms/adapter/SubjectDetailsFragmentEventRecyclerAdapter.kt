package com.saboon.myprograms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.R
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.util.dialog.Dialogs
import com.saboon.myprograms.util.generator.DateTimeGenerator

class SubjectDetailsFragmentEventRecyclerAdapter:
    RecyclerView.Adapter<SubjectDetailsFragmentEventRecyclerAdapter.EventViewHolder>() {
    class EventViewHolder(view: View): RecyclerView.ViewHolder(view)

    //bu adaptor subject detailsteki event listesini ayarlar

    private var response : ((ModelEvent) -> Unit)? = null


    private val diffUtil = object : DiffUtil.ItemCallback<ModelEvent>() {
        override fun areItemsTheSame(oldItem: ModelEvent, newItem: ModelEvent): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ModelEvent, newItem: ModelEvent): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)


    var events: List<ModelEvent>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_subject_details_fragment_event_list, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setOnItemClickListener(listener: (ModelEvent) -> Unit) {
        response = listener
    }
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val textViewTimeStart: TextView = holder.itemView.findViewById(R.id.recyclerEvent_timeStart)
        val textViewTimeEnd: TextView = holder.itemView.findViewById(R.id.recyclerEvent_timeEnd)
        val textViewTitle: TextView = holder.itemView.findViewById(R.id.recyclerEvent_title)
        val textViewDescription: TextView = holder.itemView.findViewById(R.id.recyclerEvent_description)
        val textViewDate: TextView = holder.itemView.findViewById(R.id.recyclerEvent_date)
        val textViewPlace: TextView = holder.itemView.findViewById(R.id.recyclerEvent_place)


        holder.itemView.apply {

            if(events[position].timeStart == 0L || events[position].timeEnd == 0L){
                textViewTimeStart.text = resources.getString(R.string.all)
                textViewTimeEnd.text = resources.getString(R.string.day)
            }else{
                textViewTimeStart.text = DateTimeGenerator().convertLongToDateTime(events[position].timeStart, "HH:mm")
                textViewTimeEnd.text = DateTimeGenerator().convertLongToDateTime(events[position].timeEnd, "HH:mm")
            }


            textViewTitle.text = events[position].title
            textViewDescription.text = events[position].description
            when(events[position].repeat){
                0 -> {//no repeat
                    val date = DateTimeGenerator().convertLongToDateTime(events[position].date, "dd MMMM yyyy")
                    textViewDate.text = date
                }
                1 -> {//once in a week
                    val date = DateTimeGenerator().convertLongToDateTime(events[position].date, "EEEE")
                    textViewDate.text = resources.getString(R.string.onceInWeek, date)
                }
                2 -> {//once in a month
                    val date = DateTimeGenerator().convertLongToDateTime(events[position].date, "dd")
                    textViewDate.text = resources.getString(R.string.onceInMonth, date)
                }
                3 -> {//once in a year
                    val date = DateTimeGenerator().convertLongToDateTime(events[position].date, "dd MMMM")
                    textViewDate.text = resources.getString(R.string.onceInYear, date)
                }
            }

            textViewPlace.text = events[position].place


        }

        holder.itemView.setOnClickListener {
            response?.let {
                it(events[position])
            }
        }
    }
}