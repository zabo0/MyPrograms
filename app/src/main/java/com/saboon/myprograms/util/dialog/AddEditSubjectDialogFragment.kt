package com.saboon.myprograms.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.saboon.myprograms.util.DateGenerator
import com.saboon.myprograms.util.IdGenerator
import com.saboon.myprograms.view.FragmentSubjectsDirections
import com.saboon.myprograms.viewmodel.VMFragmentProgram
import com.saboon.myprograms.viewmodel.VMFragmentSubject
import kotlinx.coroutines.launch

class AddEditSubjectDialogFragment: DialogFragment() {


    private lateinit var viewModelProgram: VMFragmentProgram
    private lateinit var viewModelSubject: VMFragmentSubject

    private lateinit var textTitle: TextInputEditText
    private lateinit var textPerson1: TextInputEditText
    private lateinit var textPerson2: TextInputEditText
    private lateinit var textPerson3: TextInputEditText
    private lateinit var radioColorPicker1: RadioGroup
    private lateinit var radioColorPicker2: RadioGroup
    private lateinit var topAppBar: MaterialToolbar

    private lateinit var program: ModelProgram
    private lateinit var subject: ModelSubject

    var programId: String = "null"
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


        viewModelProgram = ViewModelProvider(requireActivity()).get(VMFragmentProgram::class.java)
        viewModelSubject = ViewModelProvider(requireActivity()).get(VMFragmentSubject::class.java)

        topAppBar = view.findViewById(R.id.dialogFragmetn_topAppBar)
        textTitle = view.findViewById(R.id.dialogFragment_subjectTitle)
        textPerson1 = view.findViewById(R.id.dialogFragment_person1)
        textPerson2 = view.findViewById(R.id.dialogFragment_person2)
        textPerson3 = view.findViewById(R.id.dialogFragment_person3)
        radioColorPicker1 = view.findViewById(R.id.dialogFragment_radioGroup_1)
        radioColorPicker2 = view.findViewById(R.id.dialogFragment_radioGroup_2)




        viewModelProgram.observeProgram(programId).observe(viewLifecycleOwner, Observer {
            program = it
            topAppBar.subtitle = program.title
        })


        topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_save ->{
                    subject = createSubject()
                    viewModelSubject.viewModelScope.launch {
                        viewModelSubject.insertSubject(subject)

                        val action = FragmentSubjectsDirections.actionFragmentSubjectsToFragmentSubjectDetails(program.id, subject.id)
                        findNavController().navigate(action)
                        dialog?.dismiss()
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




    }


    private fun createSubject(): ModelSubject{

        val dateCreated = DateGenerator().getDateInMillis()
        val ownerId = program.id
        val title = textTitle.text.toString()
        val person1 = textPerson1.text.toString()
        val person2 = textPerson2.text.toString()
        val person3 = textPerson3.text.toString()
        val color = "#ff0000"

        val id = IdGenerator().generateSubjectId(dateCreated, program.title)

        return ModelSubject(id,dateCreated,dateCreated,ownerId,title,person1,person2,person3,color)

    }


    override fun getTheme(): Int {
        return R.style.DialogTheme
    }



}