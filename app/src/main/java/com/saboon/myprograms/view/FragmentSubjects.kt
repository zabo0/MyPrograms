package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentSubjectsBinding


class FragmentSubjects : Fragment(R.layout.fragment_subjects) {


    private var _binding : FragmentSubjectsBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSubjectsBinding.bind(view)
        _binding = binding


        // TODO: get arguments with navigation direction



    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}