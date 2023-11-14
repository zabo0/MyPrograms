package com.saboon.myprograms.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
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
import com.saboon.myprograms.viewmodel.VMProgram
import com.saboon.myprograms.viewmodel.VMSubject
import kotlinx.coroutines.launch

class AddEditEventDialogFragment(): DialogFragment() {

    private lateinit var viewModelProgram: VMProgram
    private lateinit var viewModelSubject: VMSubject
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



    private lateinit var subject: ModelSubject
    private lateinit var event: ModelEvent


    private var date: Long = 0L

    var subjectId: String = "null"
        set(value) {
            field = value
        }

    var eventId: String = "null"
        set(value) {
            field = value
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_fragment_addeditevent, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelProgram = ViewModelProvider(requireActivity())[VMProgram::class.java]
        viewModelSubject = ViewModelProvider(requireActivity())[VMSubject::class.java]
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



        viewModelSubject.observeSubject(subjectId).observe(viewLifecycleOwner, Observer {
            subject = it
            topAppBar.subtitle = subject.title
        })

        topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_save -> {
                    if (eventId != "null"){
                        // TODO: update event
                    }else{
                        create()
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

        val repeatArray = resources.getStringArray(R.array.repeat)
        val repeatArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_simple_list_item, repeatArray)
        editTextRepeat.setAdapter(repeatArrayAdapter)

        val reminderArray = resources.getStringArray(R.array.reminder)
        val reminderArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_simple_list_item, reminderArray)
        editTextReminderTime.setAdapter(reminderArrayAdapter)


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
        val timeReminder = requireActivity().resources.getStringArray(R.array.reminder).indexOf(editTextReminderTime.text.toString())
        val repeat = requireActivity().resources.getStringArray(R.array.repeat).indexOf(editTextRepeat.text.toString())
        val id = IdGenerator().generateEventId(dateCreated,ownerId)

        return ModelEvent(id,dateCreated, dateCreated, ownerId,title,description,date,timeStart,timeEnd,place,timeReminder,repeat)
    }

    private fun create(){
        event = createEvent()
        viewModelEvent.viewModelScope.launch {
            viewModelEvent.insertEvent(event)

            dialog?.dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

}