package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSubjectDetails : Fragment(R.layout.fragment_subject_details) {

    private var _binding: FragmentSubjectDetailsBinding?=null
    private lateinit var binding: FragmentSubjectDetailsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentSubjectDetailsBinding.bind(view)
        _binding = binding

    }





    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}