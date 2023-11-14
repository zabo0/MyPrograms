package com.saboon.myprograms.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.saboon.myprograms.R
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.util.generator.DateTimeGenerator
import com.saboon.myprograms.util.generator.IdGenerator
import com.saboon.myprograms.viewmodel.VMEvent
import kotlinx.coroutines.launch

class AddEditEventDialogFragment(): DialogFragment() {

    private lateinit var viewModelEvent: VMEvent

    private lateinit var topAppBar : MaterialToolbar

    private lateinit var editTextTitle : TextInputEditText
    private lateinit var editTextDescription : TextInputEditText
    private lateinit var editTextDate : TextInputEditText
    private lateinit var editTextRepeat : AutoCompleteTextView
    private lateinit var editTextStartTime : TextInputEditText
    private lateinit var editTextEndTime : TextInputEditText
    private lateinit var editTextReminderTime : AutoCompleteTextView
    private lateinit var editTextPlace : TextInputEditText



    private var date: Long = 0L

    lateinit var subject: ModelSubject

    var event: ModelEvent? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_fragment_addeditevent, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelEvent = ViewModelProvider(requireActivity())[VMEvent::class.java]

        topAppBar = view.findViewById(R.id.dialogFragment_event_topAppBar)
        editTextTitle = view.findViewById(R.id.dialogFragment_event_eventTitle)
        editTextDescription = view.findViewById(R.id.dialogFragment_event_eventDescription)
        editTextDate = view.findViewById(R.id.dialogFragment_event_eventDate)
        editTextRepeat = view.findViewById(R.id.dialogFragment_event_eventRepeat)
        editTextStartTime = view.findViewById(R.id.dialogFragment_event_eventTimeStart)
        editTextEndTime = view.findViewById(R.id.dialogFragment_event_eventTimeEnd)
        editTextReminderTime = view.findViewById(R.id.dialogFragment_event_eventReminder)
        editTextPlace = view.findViewById(R.id.dialogFragment_event_eventPlace)



        topAppBar.subtitle = subject.title



        val repeatArray = resources.getStringArray(R.array.repeat)
        val repeatArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_simple_list_item, repeatArray)
        editTextRepeat.setAdapter(repeatArrayAdapter)

        val reminderArray = resources.getStringArray(R.array.reminder)
        val reminderArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_simple_list_item, reminderArray)
        editTextReminderTime.setAdapter(reminderArrayAdapter)


        if (event != null){
            applyDataToView(event!!)
            topAppBar.title = resources.getString(R.string.editEvent)
        }

        topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_save -> {
                    if (event != null){
                        // TODO: update event
                        updateEvent()
                        dialog?.dismiss()
                    }else{
                        //create new event
                        saveToDatabase(createEvent())
                        dialog?.dismiss()
                    }
                    true
                }
                R.id.menu_cancel -> {
                    dialog?.dismiss()
                    true
                }
                else -> {false}
            }
        }

        editTextStartTime.setOnClickListener {
            TimePickers(requireContext(), childFragmentManager).timePicker(resources.getString(R.string.timeStart), editTextStartTime.text.toString()) {
                editTextStartTime.setText(it)
                if (editTextEndTime.text.toString() == ""){
                    val hour = it.split(":")[0].toInt() + 1
                    val minute = it.split(":")[1]
                    val text = "${hour}:${minute}"
                    editTextEndTime.setText(text)
                }
            }
        }

        editTextEndTime.setOnClickListener {
            TimePickers(requireContext(), childFragmentManager).timePicker(resources.getString(R.string.timeEnd), editTextEndTime.text.toString()) {
                editTextEndTime.setText(it)
                if(editTextStartTime.text.toString() == ""){
                    val hour = it.split(":")[0].toInt() - 1
                    val minute = it.split(":")[1]
                    val text = "${hour}:${minute}"
                    editTextStartTime.setText(text)
                }
            }
        }

        editTextRepeat.addTextChangedListener {
            editTextDate.setText("")
        }


        editTextDate.setOnClickListener {view ->

            if(editTextRepeat.text.toString() == "" || editTextRepeat.text.toString() == requireActivity().resources.getStringArray(R.array.repeat)[0]){
                DatePickers(requireContext(),childFragmentManager).fullDatePicker("select day"){
                    date = it
                    editTextDate.setText(DateTimeGenerator().convertLongToDateTime(date,"dd MMMM yyyy"))
                }
            }

            if (editTextRepeat.text.toString() == requireActivity().resources.getStringArray(R.array.repeat)[1]){
                DatePickers(requireContext(),childFragmentManager).dayOfWeekPicker(view){
                    date = it
                    editTextDate.setText(DateTimeGenerator().convertLongToDateTime(date, "EEEE"))
                }
            }

            if (editTextRepeat.text.toString() == requireActivity().resources.getStringArray(R.array.repeat)[2]){
                DatePickers(requireContext(), childFragmentManager).dayOfMonthPicker {
                    date = it
                    editTextDate.setText(resources.getString(R.string.onceInMonth, DateTimeGenerator().convertLongToDateTime(date, "dd")))
                }
            }

            if (editTextRepeat.text.toString() == requireActivity().resources.getStringArray(R.array.repeat)[3]){
                DatePickers(requireContext(), childFragmentManager).dayOfYearPicker {
                    date = it
                    editTextDate.setText(resources.getString(R.string.onceInMonth, DateTimeGenerator().convertLongToDateTime(date, "dd MMMM")))
                }
            }
        }




    }

    private fun createEvent(): ModelEvent{
        val dateCreated = DateTimeGenerator().getDateInMillis()
        val ownerId = subject.id
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        //date initialized in editTextDate.setOnClickListener
        val timeStart = DateTimeGenerator().convertTimeToLong(editTextStartTime.text.toString(),"HH:mm")
        val timeEnd = DateTimeGenerator().convertTimeToLong(editTextEndTime.text.toString(),"HH:mm")
        val place = editTextPlace.text.toString()
        // TODO: burada hatirlatici olayini yap
        var timeReminder = 0
        if(editTextReminderTime.text.toString() != ""){
            timeReminder = requireActivity().resources.getStringArray(R.array.reminder).indexOf(editTextReminderTime.text.toString())
        }
        var repeat = 0
        if(editTextRepeat.text.toString() != ""){
            repeat = requireActivity().resources.getStringArray(R.array.repeat).indexOf(editTextRepeat.text.toString())
        }
        val id = IdGenerator().generateEventId(dateCreated,ownerId)

        return ModelEvent(id,dateCreated, dateCreated, ownerId,title,description,date,timeStart,timeEnd,place,timeReminder,repeat)
    }

    private fun updateEvent(){
        val id = event!!.id
        val dateModified = DateTimeGenerator().getDateInMillis()
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        //date initialized in editTextDate.setOnClickListener
        val timeStart = DateTimeGenerator().convertTimeToLong(editTextStartTime.text.toString(),"HH:mm")
        val timeEnd = DateTimeGenerator().convertTimeToLong(editTextEndTime.text.toString(),"HH:mm")
        val place = editTextPlace.text.toString()
        // TODO: burada hatirlatici olayini yap
        var timeReminder = 0
        if(editTextReminderTime.text.toString() != ""){
            timeReminder = requireActivity().resources.getStringArray(R.array.reminder).indexOf(editTextReminderTime.text.toString())
        }
        val repeat = requireActivity().resources.getStringArray(R.array.repeat).indexOf(editTextRepeat.text.toString())

        viewModelEvent.viewModelScope.launch {
            viewModelEvent.updateEvent(id,dateModified,title,description,date,timeStart,timeEnd,place,timeReminder, repeat)
        }
    }

    private fun saveToDatabase(event: ModelEvent){
        viewModelEvent.viewModelScope.launch {
            viewModelEvent.insertEvent(event)
            //dialog?.dismiss()
        }
    }

    private fun applyDataToView(event: ModelEvent){
        editTextTitle.setText(event.title)
        editTextDescription.setText(event.description)
        editTextRepeat.setText(requireActivity().resources.getStringArray(R.array.repeat)[event.repeat], false)
        when(event.repeat){
            0 -> {//no repeat
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "dd MMMM yyyy")
                editTextDate.setText(date)
            }
            1 -> {//once in a week
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "EEEE")
                editTextDate.setText(resources.getString(R.string.onceInWeek, date))
            }
            2 -> {//once in a month
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "dd")
                editTextDate.setText(resources.getString(R.string.onceInMonth, date))
            }
            3 -> {//once in a year
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "dd MMMM")
                editTextDate.setText(resources.getString(R.string.onceInYear, date))
            }
        }

        if(event.timeStart != 0L || event.timeEnd != 0L){
            editTextStartTime.setText(DateTimeGenerator().convertLongToDateTime(event.timeStart, "HH:mm"))
            editTextEndTime.setText(DateTimeGenerator().convertLongToDateTime(event.timeEnd, "HH:mm"))
        }

        editTextReminderTime.setText(resources.getStringArray(R.array.reminder)[event.timeReminder],false)
        editTextPlace.setText(event.place)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

}