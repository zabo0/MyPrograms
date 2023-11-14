package com.saboon.myprograms.util.dialog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.saboon.myprograms.R
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.util.generator.DateTimeGenerator
import kotlinx.coroutines.launch

class Dialogs(private val activity: Activity, private val context: Context){


    private val string = activity.resources

    fun showCreateProgramDialog(title: String, response: (String) -> Unit){

        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.alert_dialog_create_program, null)

        val editText: TextInputEditText = dialogLayout.findViewById(R.id.alertDialog_editText_createProgram)
        val textInputLayout: TextInputLayout = dialogLayout.findViewById(R.id.alertDialog_textInputLayout_createProgram)
        val saveButton : Button = dialogLayout.findViewById(R.id.dialogSaveButton)
        val cancelButton: Button = dialogLayout.findViewById(R.id.dialogCancelButton)

        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .create()


        saveButton.setOnClickListener {

            val text = editText.text.toString()
            if (text != ""){
                    response(editText.text.toString())
                alertDialog.cancel()
            }else{
                textInputLayout.error = string.getString(R.string.youShoulTypeATitle)
            }
        }

        cancelButton.setOnClickListener {
            alertDialog.cancel()
        }

        alertDialog.setView(dialogLayout)
        alertDialog.show()
    }

    fun showEditProgramDialog(title: String, response: (String) -> Unit){
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.alert_dialog_create_program, null)

        val editText: TextInputEditText = dialogLayout.findViewById(R.id.alertDialog_editText_createProgram)
        val textInputLayout: TextInputLayout = dialogLayout.findViewById(R.id.alertDialog_textInputLayout_createProgram)
        val saveButton : Button = dialogLayout.findViewById(R.id.dialogSaveButton)
        val cancelButton: Button = dialogLayout.findViewById(R.id.dialogCancelButton)

        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .create()

        //textInputLayout.hint = oldTitle


        saveButton.setOnClickListener {

            val title = editText.text.toString()
            if (title != ""){
                response(editText.text.toString())
                alertDialog.cancel()
            }else{
                textInputLayout.error = string.getString(R.string.youShoulTypeATitle)
            }
        }

        cancelButton.setOnClickListener {
            alertDialog.cancel()
        }

        alertDialog.setView(dialogLayout)
        alertDialog.show()
    }


    fun showDeleteAlertDialog(title: String,message: String, response: (Boolean) -> Unit){

        val string = activity.resources

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(string.getString(R.string.no)){dialog, which ->
                response(false)
            }
            .setPositiveButton(string.getString(R.string.yes)){dialog, which ->
                response(true)
            }
            .show()
    }

    fun showEventDetailsDialog(event: ModelEvent, callback: (ModelEvent?) -> Unit){
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_event_details, null)

        val titleText: TextView = dialogLayout.findViewById(R.id.eventDetailsDialog_title)
        val descriptionText: TextView = dialogLayout.findViewById(R.id.eventDetailsDialog_description)
        val dateText: TextView = dialogLayout.findViewById(R.id.eventDetailsDialog_date)
        val startTimeText: TextView = dialogLayout.findViewById(R.id.eventDetailsDialog_timeStart)
        val endTimeText: TextView = dialogLayout.findViewById(R.id.eventDetailsDialog_timeEnd)
        val reminderText: TextView = dialogLayout.findViewById(R.id.eventDetailsDialog_reminder)
        val placeText: TextView = dialogLayout.findViewById(R.id.eventDetailsDialog_place)
        val editButton : Button = dialogLayout.findViewById(R.id.eventDetailsDialog_EditButton)
        val deleteButton: Button = dialogLayout.findViewById(R.id.eventDetailsDialog_DeleteButton)
        val cancelButton: Button = dialogLayout.findViewById(R.id.eventDetailsDialog_CancelButton)


        if(event.title != "") titleText.text = event.title
        if(event.description != "") descriptionText.text = event.description

        if(event.timeStart == 0L || event.timeEnd == 0L){
            startTimeText.text = dialogLayout.resources.getString(R.string.all)
            endTimeText.text = dialogLayout.resources.getString(R.string.day)
        }
        else{
            startTimeText.text = DateTimeGenerator().convertLongToDateTime(event.timeStart, "HH:mm")
            endTimeText.text = DateTimeGenerator().convertLongToDateTime(event.timeEnd, "HH:mm")
        }
        reminderText.text = event.timeReminder.toString()
        if(event.place != "") placeText.text = event.place

        when(event.repeat){
            0 -> {//no repeat
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "dd MMMM yyyy")
                dateText.text = date
            }
            1 -> {//once in a week
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "EEEE")
                dateText.text = dialogLayout.resources.getString(R.string.onceInWeek, date)
            }
            2 -> {//once in a month
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "dd")
                dateText.text = dialogLayout.resources.getString(R.string.onceInMonth, date)
            }
            3 -> {//once in a year
                val date = DateTimeGenerator().convertLongToDateTime(event.date, "dd MMMM")
                dateText.text = dialogLayout.resources.getString(R.string.onceInYear, date)
            }
        }

        val alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(dialogLayout.resources.getString(R.string.eventDetails))
            .create()

        editButton.setOnClickListener {
            callback(event)
            alertDialog.cancel()
        }

        cancelButton.setOnClickListener {
            alertDialog.cancel()
        }

        deleteButton.setOnClickListener {
            callback(null)
            alertDialog.cancel()
        }

        alertDialog.setView(dialogLayout)
        alertDialog.show()
    }

}