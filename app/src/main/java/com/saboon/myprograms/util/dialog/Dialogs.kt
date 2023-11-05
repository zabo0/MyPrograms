package com.saboon.myprograms.util.dialog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.saboon.myprograms.R
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


    fun showAddEditSubjectBottomSheetDialog(context: Context){
        val bottomSheetDialog = BottomSheetDialog(context)
        //val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_addeditsubject)
    }

}