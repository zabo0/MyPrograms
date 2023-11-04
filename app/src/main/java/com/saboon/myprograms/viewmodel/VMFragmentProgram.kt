package com.saboon.myprograms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.repo.repositoryinterface.ProgramRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VMFragmentProgram @Inject constructor(
    private val repository: ProgramRepositoryInterface
):ViewModel(){

    suspend fun insertProgram(program: ModelProgram){
        repository.insertProgram(program)
    }

    suspend fun deleteProgram(id: String){
        repository.deleteProgram(id)
    }

    fun observeAllPrograms(): LiveData<List<ModelProgram>>{
        return repository.observeAllProgram()
    }

    fun observeProgram(id: String): LiveData<ModelProgram>{
        return repository.observeProgram(id)
    }

    suspend fun renameProgram(id: String, newTitle: String, newDate: Long){
        repository.renameProgram(id, newTitle, newDate)
    }
}