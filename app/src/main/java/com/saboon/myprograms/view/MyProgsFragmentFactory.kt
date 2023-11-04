package com.saboon.myprograms.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.saboon.myprograms.adapter.MainFragmentProgramsRecyclerAdapter
import com.saboon.myprograms.adapter.ProgramFragmentRecyclerAdapter
import javax.inject.Inject

class MyProgsFragmentFactory @Inject constructor(
     private val progRecycAdapter: MainFragmentProgramsRecyclerAdapter,
     private val programFragmentRcyclerAdapter: ProgramFragmentRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            FragmentMain::class.java.name -> FragmentMain(progRecycAdapter)
            FragmentPrograms::class.java.name -> FragmentPrograms(programFragmentRcyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}





