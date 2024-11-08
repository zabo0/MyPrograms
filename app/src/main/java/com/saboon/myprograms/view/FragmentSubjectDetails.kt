package com.saboon.myprograms.view

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.saboon.myprograms.R
import com.saboon.myprograms.adapter.SubjectDetailsFragmentEventRecyclerAdapter
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.util.dialog.AddEditEventDialogFragment
import com.saboon.myprograms.util.dialog.AddEditSubjectDialogFragment
import com.saboon.myprograms.util.dialog.Dialogs
import com.saboon.myprograms.viewmodel.VMEvent
import com.saboon.myprograms.viewmodel.VMProgram
import com.saboon.myprograms.viewmodel.VMSubject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSubjectDetails @Inject constructor(
    private val eventRecyclerAdapter: SubjectDetailsFragmentEventRecyclerAdapter
): Fragment(R.layout.fragment_subject_details) {

    private var _binding: FragmentSubjectDetailsBinding?=null
    private lateinit var binding: FragmentSubjectDetailsBinding

    private lateinit var viewModelProgram : VMProgram
    private lateinit var viewModelSubject: VMSubject
    private lateinit var viewModelEvent: VMEvent

    private lateinit var program : ModelProgram
    private lateinit var subject : ModelSubject
    private lateinit var events: List<ModelEvent>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentSubjectDetailsBinding.bind(view)
        _binding = binding

        viewModelProgram = ViewModelProvider(requireActivity())[VMProgram::class.java]
        viewModelSubject = ViewModelProvider(requireActivity())[VMSubject::class.java]
        viewModelEvent = ViewModelProvider(requireActivity())[VMEvent::class.java]


        arguments?.let {
            val programId = it.getString("programId")
            val subjectId = it.getString("subjectId")

            viewModelProgram.observeProgram(programId!!).observe(viewLifecycleOwner, Observer {
                program = it

                binding.topAppBar.subtitle = program.title
            })

            viewModelSubject.observeSubject(subjectId!!).observe(viewLifecycleOwner, Observer {subjectResult ->
                subject = subjectResult
                applyDataToView()

                viewModelEvent.observeAllEventByOwnerSubjectId(subject.id).observe(viewLifecycleOwner, Observer { eventList ->
                    if (eventList != null){
                        events = eventList
                        eventRecyclerAdapter.events = events
                    }
                })
            })

        }

        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = FragmentSubjectDetailsDirections.actionFragmentSubjectDetailsToFragmentSubjects(program.id)
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)


        binding.subjectDetailsRecyclerView.adapter = eventRecyclerAdapter
        binding.subjectDetailsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(),1)
        dividerItemDecoration.dividerInsetStart = 50
        dividerItemDecoration.dividerInsetEnd = 50
        dividerItemDecoration.isLastItemDecorated = false
        binding.subjectDetailsRecyclerView.addItemDecoration(dividerItemDecoration)


        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_edit_subject -> {
                    val addSubjectDialogFragment = AddEditSubjectDialogFragment()
                    addSubjectDialogFragment.programId = program.id
                    addSubjectDialogFragment.subjectId = subject.id
                    addSubjectDialogFragment.show(parentFragmentManager,"dialog")
                    true
                }
                R.id.menu_delete_subject -> {

                    Dialogs(requireActivity(), requireContext()).showDeleteAlertDialog(resources.getString(R.string.delete), resources.getString(R.string.areYouSureDeleteAllEvent)){
                        if(it){

                            viewModelEvent.viewModelScope.launch {
                                viewModelEvent.deleteAllEventByOwnerId(subject.id)
                            }

                            viewModelSubject.viewModelScope.launch {
                                viewModelSubject.deleteSubject(subject.id)
                                val action = FragmentSubjectDetailsDirections.actionFragmentSubjectDetailsToFragmentSubjects(program.id)
                                findNavController().navigate(action)
                            }
                        }
                    }
                    true
                }
                else -> {false}
            }
        }


        binding.topAppBar.setNavigationOnClickListener {
            val action = FragmentSubjectDetailsDirections.actionFragmentSubjectDetailsToFragmentSubjects(program.id)
            it.findNavController().navigate(action)
        }

        binding.addNewEventFab.setOnClickListener {
            val addEventDialogFragment = AddEditEventDialogFragment()
            addEventDialogFragment.subject = subject
            addEventDialogFragment.show(parentFragmentManager,"dialog")
        }


        eventRecyclerAdapter.setOnItemClickListener {event ->
            Dialogs(requireActivity(),requireContext()).showEventDetailsDialog(event){
                if(it != null){
                    //edit event
                    val editEventDialogFragment = AddEditEventDialogFragment()
                    editEventDialogFragment.subject = subject
                    editEventDialogFragment.event = it
                    editEventDialogFragment.show(parentFragmentManager, "dialog")
                }
                else{
                    //delete event
                    Dialogs(requireActivity(), requireContext()).showDeleteAlertDialog(resources.getString(R.string.delete), resources.getString(R.string.areYouSure)){ isPositive->
                        if(isPositive){
                            viewModelEvent.viewModelScope.launch {
                                viewModelEvent.deleteEvent(event.id)
                            }
                        }
                    }
                }
            }
        }



    }



    private fun applyDataToView(){

        binding.subjectTitle.text = subject.title
        if(subject.person1 != ""){
            binding.person1.visibility = View.VISIBLE
            binding.person1.text = subject.person1
        }
        if(subject.person2 != ""){
            binding.person2.visibility = View.VISIBLE
            binding.person2.text = subject.person2
        }
        if(subject.person3 != ""){
            binding.person3.visibility = View.VISIBLE
            binding.person3.text = subject.person3
        }
        val gradient= GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,
            intArrayOf(Color.parseColor(subject.color), 0x00000000)
        )

        binding.gradientView.background = gradient


    }





    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}