package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.saboon.myprograms.R
import com.saboon.myprograms.adapter.SubjectsFragmentRecyclerAdapter
import com.saboon.myprograms.databinding.FragmentSubjectsBinding
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.util.dialog.AddEditSubjectDialogFragment
import com.saboon.myprograms.viewmodel.VMFragmentProgram
import com.saboon.myprograms.viewmodel.VMFragmentSubject
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSubjects @Inject constructor(
    private val subjectsRecyclerAdapter: SubjectsFragmentRecyclerAdapter
) : Fragment(R.layout.fragment_subjects) {


    private var _binding : FragmentSubjectsBinding?=null
    private lateinit var binding: FragmentSubjectsBinding

    private lateinit var viewModelSubject: VMFragmentSubject
    private lateinit var viewModelProgram: VMFragmentProgram

    private lateinit var program: ModelProgram
    private lateinit var subjects: List<ModelSubject>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSubjectsBinding.bind(view)
        _binding = binding

        viewModelSubject = ViewModelProvider(requireActivity()).get(VMFragmentSubject::class.java)
        viewModelProgram = ViewModelProvider(requireActivity()).get(VMFragmentProgram::class.java)

        arguments.let {
            if (it != null){
                val programId = it.getString("programId").toString()

                viewModelProgram.observeProgram(programId).observe(viewLifecycleOwner, Observer {
                    program = it

                    binding.topAppBar.subtitle = program.title
                    observe()
                })
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            val action = FragmentSubjectsDirections.actionFragmentSubjectsToFragmentMain(program.id)
            findNavController().navigate(action)
        }


        binding.addSubjectFab.setOnClickListener {

            val addSubjectDialogFragment = AddEditSubjectDialogFragment()
            addSubjectDialogFragment.programId = program.id
            addSubjectDialogFragment.show(parentFragmentManager,"dialog")
        }


        binding.subjectsRecyclerView.adapter = subjectsRecyclerAdapter
        binding.subjectsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(),1)
        dividerItemDecoration.dividerInsetStart = 50
        dividerItemDecoration.dividerInsetEnd = 50
        dividerItemDecoration.isLastItemDecorated = false
        binding.subjectsRecyclerView.addItemDecoration(dividerItemDecoration)



    }


    private fun observe(){

        viewModelSubject.observeAllSubjectByOwner(program.id).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                subjects = it

                subjectsRecyclerAdapter.subjects = subjects
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}