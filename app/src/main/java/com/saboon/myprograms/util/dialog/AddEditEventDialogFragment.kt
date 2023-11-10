package com.saboon.myprograms.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.saboon.myprograms.R
import com.saboon.myprograms.viewmodel.VMEvent
import com.saboon.myprograms.viewmodel.VMProgram
import com.saboon.myprograms.viewmodel.VMSubject

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
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

}