package com.saboon.myprograms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.repo.repositoryinterface.ProgramRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VMFragmentMain @Inject constructor(
    private val repository: ProgramRepositoryInterface
): ViewModel(){


    val program = MutableLiveData<ModelProgram>()
    val programs = MutableLiveData<List<ModelProgram>?>()
    val subjects = MutableLiveData<List<ModelSubject>?>()

    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()



    fun observeAllPrograms(): LiveData<List<ModelProgram>> {
        return repository.observeAllProgram()
    }

    fun observeProgram(id: String): LiveData<ModelProgram>{
        return repository.observeProgram(id)
    }

}


