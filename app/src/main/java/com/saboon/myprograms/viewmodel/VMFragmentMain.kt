package com.saboon.myprograms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.repo.repositoryinterface.ProgramRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VMFragmentMain @Inject constructor(
    private val repository: ProgramRepositoryInterface
): ViewModel(){


    fun observeAllPrograms(): LiveData<List<ModelProgram>> {
        return repository.observeAllProgram()
    }

}


