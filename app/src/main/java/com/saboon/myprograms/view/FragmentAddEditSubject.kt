package com.saboon.myprograms.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentAddEditSubjectBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentAddEditSubject : Fragment(R.layout.fragment_add_edit_subject) {

    private var _binding: FragmentAddEditSubjectBinding?= null
    private lateinit var binding: FragmentAddEditSubjectBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddEditSubjectBinding.bind(view)
        _binding = binding

    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}