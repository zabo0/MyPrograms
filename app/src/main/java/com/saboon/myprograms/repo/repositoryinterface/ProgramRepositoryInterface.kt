package com.saboon.myprograms.repo.repositoryinterface

import androidx.lifecycle.LiveData
import com.saboon.myprograms.model.ModelProgram

interface ProgramRepositoryInterface {

    suspend fun insertProgram(program:ModelProgram)
    suspend fun deleteProgram(id:String)
    fun observeProgram(id: String): LiveData<ModelProgram>
    fun observeAllProgram(): LiveData<List<ModelProgram>>
    suspend fun renameProgram(id: String, newTitle: String, newDate: Long)


}