package com.saboon.myprograms.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.saboon.myprograms.adapter.MainFragmentProgramsRecyclerAdapter
import com.saboon.myprograms.adapter.ProgramFragmentRecyclerAdapter
import com.saboon.myprograms.adapter.SubjectsFragmentRecyclerAdapter
import javax.inject.Inject

class MyProgsFragmentFactory @Inject constructor(
     private val progRecycAdapter: MainFragmentProgramsRecyclerAdapter,
     private val programFragmentRcyclerAdapter: ProgramFragmentRecyclerAdapter,
    private val subjectsFragmentRecyclerAdapter: SubjectsFragmentRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            FragmentMain::class.java.name -> FragmentMain(progRecycAdapter)
            FragmentPrograms::class.java.name -> FragmentPrograms(programFragmentRcyclerAdapter)
            FragmentSubjects::class.java.name -> FragmentSubjects(subjectsFragmentRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}





