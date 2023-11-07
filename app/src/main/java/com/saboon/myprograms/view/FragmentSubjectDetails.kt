package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.viewmodel.VMFragmentProgram
import com.saboon.myprograms.viewmodel.VMFragmentSubject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSubjectDetails : Fragment(R.layout.fragment_subject_details) {

    private var _binding: FragmentSubjectDetailsBinding?=null
    private lateinit var binding: FragmentSubjectDetailsBinding

    private lateinit var viewModelProgram : VMFragmentProgram
    private lateinit var viewModelSubject: VMFragmentSubject

    private lateinit var program : ModelProgram
    private lateinit var subject : ModelSubject


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentSubjectDetailsBinding.bind(view)
        _binding = binding

        viewModelProgram = ViewModelProvider(requireActivity())[VMFragmentProgram::class.java]
        viewModelSubject = ViewModelProvider(requireActivity())[VMFragmentSubject::class.java]


        arguments?.let {
            val programId = it.getString("programId")
            val subjectId = it.getString("subjectId")

            if (programId != null && subjectId != null)
            viewModelProgram.observeProgram(programId).observe(viewLifecycleOwner, Observer {
                program = it

                binding.topAppBar.subtitle = program.title
            })

            viewModelSubject.observeSubject(subjectId!!).observe(viewLifecycleOwner, Observer {
                subject = it
                applyDataToView()
            })

        }


        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_edit_subject -> {
                    // TODO: do subject edit actions
                    true
                }
                R.id.menu_delete_subject -> {
                    viewModelSubject.viewModelScope.launch {
                        viewModelSubject.deleteSubject(subject.id)

                        val action = FragmentSubjectDetailsDirections.actionFragmentSubjectDetailsToFragmentSubjects(program.id)
                        Toast.makeText(requireContext(),"deleted", Toast.LENGTH_LONG).show()
                        findNavController().navigate(action)
                    }
                    true
                }
                else -> {false}
            }
        }

    }


    private fun applyDataToView(){

        binding.subjectTitle.text = subject.title
        binding.lecturersName.text = "${subject.person1}, ${subject.person2}, ${subject.person3}"


    }





    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}