package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.saboon.myprograms.R
import com.saboon.myprograms.adapter.MainFragmentProgramsRecyclerAdapter
import com.saboon.myprograms.databinding.FragmentMainBinding
import com.saboon.myprograms.viewmodel.VMFragmentMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FragmentMain @Inject constructor(
    private val mainDrawerProgramRecyclerAdapter: MainFragmentProgramsRecyclerAdapter
): Fragment(R.layout.fragment_main) {


    private var _binding: FragmentMainBinding?=null
    private lateinit var viewModel: VMFragmentMain







    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(VMFragmentMain::class.java)

        val binding = FragmentMainBinding.bind(view)
        _binding = binding

        binding.topAppBar.setNavigationOnClickListener {
            binding.fragmentMainDrawerLayout.open()
        }

        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId){
                R.id.goToProgramDetails -> {
                    // TODO: navigate to program details
                    true
                }
                else -> false
            }

        }

        binding.fragmentMainProgramsRecyclerview.adapter = mainDrawerProgramRecyclerAdapter
        binding.fragmentMainProgramsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.goToProgramsButton.setOnClickListener {
            val action = FragmentMainDirections.actionFragmentMainToProgramsFragment()
            it.findNavController().navigate(action)
        }


        // TODO: drawer menudeki program listesine basinca main menudeki liste guncellenecek
        // TODO: programlara subject ekle

        observe()





    }



    private fun observe(){
        viewModel.observeAllPrograms().observe(viewLifecycleOwner, Observer {
            mainDrawerProgramRecyclerAdapter.programs = it
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}