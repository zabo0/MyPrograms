package com.saboon.myprograms.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.saboon.myprograms.adapter.MainFragmentProgramsRecyclerAdapter
import com.saboon.myprograms.adapter.ProgramFragmentRecyclerAdapter
import com.saboon.myprograms.adapter.SubjectDetailsFragmentEventRecyclerAdapter
import com.saboon.myprograms.adapter.SubjectDetailsFragmentRecyclerAdapter
import com.saboon.myprograms.adapter.SubjectsFragmentRecyclerAdapter
import com.saboon.myprograms.view.FragmentMain
import com.saboon.myprograms.view.FragmentPrograms
import com.saboon.myprograms.view.FragmentSubjectDetails
import com.saboon.myprograms.view.FragmentSubjects
import javax.inject.Inject

class MyProgsFragmentFactory @Inject constructor(
     private val progRecycAdapter: MainFragmentProgramsRecyclerAdapter,
     private val programFragmentRcyclerAdapter: ProgramFragmentRecyclerAdapter,
    private val subjectsFragmentRecyclerAdapter: SubjectsFragmentRecyclerAdapter,
    private val subjectDetailsFragmentRecyclerAdapter: SubjectDetailsFragmentEventRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            FragmentMain::class.java.name -> FragmentMain(progRecycAdapter)
            FragmentPrograms::class.java.name -> FragmentPrograms(programFragmentRcyclerAdapter)
            FragmentSubjects::class.java.name -> FragmentSubjects(subjectsFragmentRecyclerAdapter)
            FragmentSubjectDetails::class.java.name -> FragmentSubjectDetails(subjectDetailsFragmentRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}





