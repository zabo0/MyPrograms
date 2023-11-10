package com.saboon.myprograms.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.saboon.myprograms.R
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.util.CUSTOM_COLOR_1
import com.saboon.myprograms.util.CUSTOM_COLOR_2
import com.saboon.myprograms.util.CUSTOM_COLOR_3
import com.saboon.myprograms.util.CUSTOM_COLOR_4
import com.saboon.myprograms.util.CUSTOM_COLOR_5
import com.saboon.myprograms.util.CUSTOM_COLOR_6
import com.saboon.myprograms.util.CUSTOM_COLOR_7
import com.saboon.myprograms.util.CUSTOM_COLOR_8
import com.saboon.myprograms.util.DateGenerator
import com.saboon.myprograms.util.IdGenerator
import com.saboon.myprograms.view.FragmentSubjectsDirections
import com.saboon.myprograms.viewmodel.VMProgram
import com.saboon.myprograms.viewmodel.VMSubject
import kotlinx.coroutines.launch

class AddEditSubjectDialogFragment(): DialogFragment() {

    private lateinit var viewModelProgram: VMProgram
    private lateinit var viewModelSubject: VMSubject

    private lateinit var textTitle: TextInputEditText
    private lateinit var textPerson1: TextInputEditText
    private lateinit var textPerson2: TextInputEditText
    private lateinit var textPerson3: TextInputEditText
    private lateinit var radioColorPicker1: RadioGroup
    private lateinit var radioColorPicker2: RadioGroup
    private lateinit var topAppBar: MaterialToolbar

    private lateinit var radioColor_1: RadioButton
    private lateinit var radioColor_2: RadioButton
    private lateinit var radioColor_3: RadioButton
    private lateinit var radioColor_4: RadioButton
    private lateinit var radioColor_5: RadioButton
    private lateinit var radioColor_6: RadioButton
    private lateinit var radioColor_7: RadioButton
    private lateinit var radioColor_8: RadioButton

    private lateinit var program: ModelProgram
    private lateinit var subject: ModelSubject

    private var selectedColor: String?= CUSTOM_COLOR_1

    var programId: String = "null"
        set(value) {
            field = value
        }

    var subjectId: String = "null"
        set(value) {
            field = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_fragment_addeditsubject,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModelProgram = ViewModelProvider(requireActivity()).get(VMProgram::class.java)
        viewModelSubject = ViewModelProvider(requireActivity()).get(VMSubject::class.java)

        topAppBar = view.findViewById(R.id.dialogFragment_subject_topAppBar)
        textTitle = view.findViewById(R.id.dialogFragment_subjectTitle)
        textPerson1 = view.findViewById(R.id.dialogFragment_person1)
        textPerson2 = view.findViewById(R.id.dialogFragment_person2)
        textPerson3 = view.findViewById(R.id.dialogFragment_person3)
        radioColorPicker1 = view.findViewById(R.id.dialogFragment_radioGroup_1)
        radioColorPicker2 = view.findViewById(R.id.dialogFragment_radioGroup_2)

        radioColor_1 = view.findViewById(R.id.radio_color1)
        radioColor_2 = view.findViewById(R.id.radio_color2)
        radioColor_3 = view.findViewById(R.id.radio_color3)
        radioColor_4 = view.findViewById(R.id.radio_color4)
        radioColor_5 = view.findViewById(R.id.radio_color5)
        radioColor_6 = view.findViewById(R.id.radio_color6)
        radioColor_7 = view.findViewById(R.id.radio_color7)
        radioColor_8 = view.findViewById(R.id.radio_color8)






        viewModelProgram.observeProgram(programId).observe(viewLifecycleOwner, Observer {
            program = it
            topAppBar.subtitle = program.title
        })

        if (subjectId != "null"){
            viewModelSubject.observeSubject(subjectId).observe(viewLifecycleOwner, Observer {
                subject = it
                applyViewForEdit()
            })
        }


        topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_save ->{
                    if(subjectId != "null"){
                        update()
                    }else{
                        create()
                    }
                    true
            }
                R.id.menu_cancel ->{
                    dialog?.dismiss()
                    true
                }else -> {
                false
            }
        }
        }
        radioButtons()
    }


    private fun createSubject(): ModelSubject{

        val dateCreated = DateGenerator().getDateInMillis()
        val ownerId = program.id
        val title = textTitle.text.toString()
        val person1 = textPerson1.text.toString()
        val person2 = textPerson2.text.toString()
        val person3 = textPerson3.text.toString()
        val color = selectedColor!!

        val id = IdGenerator().generateSubjectId(dateCreated, program.title)

        return ModelSubject(id,dateCreated,dateCreated,ownerId,title,person1,person2,person3,color)

    }

    private fun applyViewForEdit(){
        textTitle.setText(subject.title)
        textPerson1.setText(subject.person1)
        textPerson2.setText(subject.person2)
        textPerson3.setText(subject.person3)
        topAppBar.title = resources.getString(R.string.editSubject)

        when(subject.color){
            CUSTOM_COLOR_1 -> {radioColor_1.isChecked = true}
            CUSTOM_COLOR_2 -> {radioColor_2.isChecked = true}
            CUSTOM_COLOR_3 -> {radioColor_3.isChecked = true}
            CUSTOM_COLOR_4 -> {radioColor_4.isChecked = true}
            CUSTOM_COLOR_5 -> {radioColor_5.isChecked = true}
            CUSTOM_COLOR_6 -> {radioColor_6.isChecked = true}
            CUSTOM_COLOR_7 -> {radioColor_7.isChecked = true}
            CUSTOM_COLOR_8 -> {radioColor_8.isChecked = true}
            else -> {false}
        }
    }

    private fun update(){
        val newSubject = ModelSubject(
            subject.id,
            subject.dateCreated,
            DateGenerator().getDateInMillis(),
            subject.ownerId,
            textTitle.text.toString(),
            textPerson1.text.toString(),
            textPerson2.text.toString(),
            textPerson3.text.toString(),
            selectedColor!!
        )


        viewModelSubject.viewModelScope.launch {
            viewModelSubject.updateSubject(newSubject)
            dialog?.dismiss()
        }
    }

    private fun create(){
        subject = createSubject()
        viewModelSubject.viewModelScope.launch {
            viewModelSubject.insertSubject(subject)

            val action = FragmentSubjectsDirections.actionFragmentSubjectsToFragmentSubjectDetails(subject.id, program.id)
            findNavController().navigate(action)
            dialog?.dismiss()
        }
    }

    private fun radioButtons(){
        radioColorPicker1.setOnCheckedChangeListener { radioGroup, checkedID ->
            // Check which radio button was clicked
            when (checkedID) {
                R.id.radio_color1 -> {
                    if(radioColor_1.isChecked){
                        radioColorPicker2.clearCheck()
                        selectedColor = CUSTOM_COLOR_1
                    }
                }
                R.id.radio_color2 -> {
                    if(radioColor_2.isChecked){
                        radioColorPicker2.clearCheck()
                        selectedColor = CUSTOM_COLOR_2
                    }
                }
                R.id.radio_color3 -> {
                    if(radioColor_3.isChecked){
                        radioColorPicker2.clearCheck()
                        selectedColor = CUSTOM_COLOR_3
                    }
                }
                R.id.radio_color4 -> {
                    if(radioColor_4.isChecked){
                        radioColorPicker2.clearCheck()
                        selectedColor = CUSTOM_COLOR_4
                    }
                }
            }
        }

        radioColorPicker2.setOnCheckedChangeListener { radioGroup, checkedID ->
            // Check which radio button was clicked
            when (checkedID) {
                R.id.radio_color5 ->{
                    if(radioColor_5.isChecked){
                        radioColorPicker1.clearCheck()
                        selectedColor = CUSTOM_COLOR_5
                    }
                }
                R.id.radio_color6 -> {
                    if(radioColor_6.isChecked){
                        radioColorPicker1.clearCheck()
                        selectedColor = CUSTOM_COLOR_6
                    }
                }
                R.id.radio_color7 -> {
                    if(radioColor_7.isChecked){
                        radioColorPicker1.clearCheck()
                        selectedColor = CUSTOM_COLOR_7
                    }
                }

                R.id.radio_color8 -> {
                    if(radioColor_8.isChecked){
                        radioColorPicker1.clearCheck()
                        selectedColor = CUSTOM_COLOR_8
                    }
                }
            }
        }
    }


    override fun getTheme(): Int {
        return R.style.DialogTheme
    }



}