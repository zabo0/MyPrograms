package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentMainBinding
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.util.generator.DateTimeGenerator
import com.saboon.myprograms.viewmodel.VMEvent
import com.saboon.myprograms.viewmodel.VMProgram
import com.saboon.myprograms.viewmodel.VMSubject
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FragmentMain @Inject constructor(
    //private val mainDrawerProgramRecyclerAdapter: MainFragmentProgramsRecyclerAdapter
): Fragment(R.layout.fragment_main) {


    private var _binding: FragmentMainBinding?=null
    private lateinit var binding: FragmentMainBinding


    private lateinit var viewModelSubject: VMSubject
    private lateinit var viewModelProgram: VMProgram
    private lateinit var viewModelEvent: VMEvent


    private lateinit var program: ModelProgram
    private lateinit var subjects: List<ModelSubject>
    private lateinit var allProgramEvents: List<ModelEvent>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModelProgram = ViewModelProvider(requireActivity())[VMProgram::class.java]
        viewModelSubject = ViewModelProvider(requireActivity())[VMSubject::class.java]
        viewModelEvent = ViewModelProvider(requireActivity())[VMEvent::class.java]

        binding = FragmentMainBinding.bind(view)
        _binding = binding


        arguments.let {
            if (it!=null){
                val programId = it.getString("programId").toString()

                viewModelProgram.observeProgram(programId).observe(viewLifecycleOwner, Observer {
                    program = it
                    binding.topAppBar.subtitle = program.title


                    observe()
                })



            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.fragmentMainDrawerLayout.open()
        }

        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId){
                R.id.goToSubjects -> {
                    val action = FragmentMainDirections.actionFragmentMainToFragmentSubjects(program.id)
                    findNavController().navigate(action)
                    true
                }
                R.id.goToProgramDetails -> {
                    val action = FragmentMainDirections.actionFragmentMainToProgramsFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }

        }

        binding.fragmentMainButtonAddSubject.setOnClickListener {
            val action = FragmentMainDirections.actionFragmentMainToFragmentSubjects(program.id)
            findNavController().navigate(action)
        }

        //databaseden butun eventleri cagir findEventById clasina gonder ve liste al. listeyi recycler adaptere gonder.




    }



    private fun observe(){
        viewModelSubject.observeAllSubjectByOwner(program.id).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                subjects = it
                if (subjects.isEmpty()){
                    binding.mainFragmentRecyclerview.visibility = View.GONE
                    binding.fragmentMainLinearLayoutEmpty.visibility = View.VISIBLE
                }else{
                    binding.mainFragmentRecyclerview.visibility = View.VISIBLE
                    binding.fragmentMainLinearLayoutEmpty.visibility = View.GONE
                }
            }
        })

        viewModelEvent.observeAllEventByOwnerProgramId(program.id).observe(viewLifecycleOwner, Observer {
            if( it!= null){
                allProgramEvents = it
                if (allProgramEvents.isNotEmpty()){
                    val weekNumber = DateTimeGenerator().getWeekNumber()

                }
            }

        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}