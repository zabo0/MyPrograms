package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.saboon.myprograms.R
import com.saboon.myprograms.adapter.ProgramFragmentRecyclerAdapter
import com.saboon.myprograms.databinding.FragmentProgramsBinding
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.util.DateGenerator
import com.saboon.myprograms.util.dialog.Dialogs
import com.saboon.myprograms.util.IdGenerator
import com.saboon.myprograms.viewmodel.VMFragmentProgram
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentPrograms @Inject constructor(
    private val programRecyclerAdapter: ProgramFragmentRecyclerAdapter
) : Fragment(R.layout.fragment_programs) {


    private var _binging: FragmentProgramsBinding?=null

    private lateinit var viewModel: VMFragmentProgram


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(VMFragmentProgram::class.java)

        val binding = FragmentProgramsBinding.bind(view)
        _binging = binding


        binding.programRecyclerView.adapter = programRecyclerAdapter
        binding.programRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(),1)
        dividerItemDecoration.dividerInsetStart = 50
        dividerItemDecoration.dividerInsetEnd = 50
        dividerItemDecoration.isLastItemDecorated = false
        binding.programRecyclerView.addItemDecoration(dividerItemDecoration)


        programRecyclerAdapter.setOnItemClickListener{ id, process->

            if (process == "delete"){
                Dialogs(requireActivity(),requireContext()).showDeleteAlertDialog(getString(R.string.delete),getString(R.string.areYouSure)){
                    if (it){
                        viewModel.viewModelScope.launch {
                            viewModel.deleteProgram(id)
                        }
                    }
                }
            }else if(process == "edit"){
                Dialogs(requireActivity(), requireContext()).showEditProgramDialog(getString(R.string.edit)){ newTitle ->
                    if (newTitle != ""){
                        viewModel.viewModelScope.launch {
                            viewModel.renameProgram(id, newTitle, DateGenerator().getDateInMillis())
                        }
                    }
                }
            }



        }



        binding.addProgramFab.setOnClickListener {

            Dialogs(requireActivity(),requireContext()).showCreateProgramDialog(getString(R.string.createProgram)){ title->
                if(title != ""){
                    viewModel.viewModelScope.launch {

                        val dateCreated = DateGenerator().getDateInMillis()
                        val id = IdGenerator().generateProgramId(dateCreated)

                        val program = ModelProgram(id,dateCreated,dateCreated,title)
                        viewModel.insertProgram(program)
                    }
                }else{
                    Toast.makeText(requireContext(),getString(R.string.youShoulTypeATitle),Toast.LENGTH_LONG).show()
                }
            }
        }







        observe()
    }


    private fun observe(){
        viewModel.observeAllPrograms().observe(viewLifecycleOwner, Observer {
            if (it != null){
                programRecyclerAdapter.programs = it
            }
        })
    }





    override fun onDestroy() {
        super.onDestroy()

        _binging = null
    }





}