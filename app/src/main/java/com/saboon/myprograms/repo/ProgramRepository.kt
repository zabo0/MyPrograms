package com.saboon.myprograms.repo

import androidx.lifecycle.LiveData
import com.saboon.myprograms.db.ProgramDAO
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.repo.repositoryinterface.ProgramRepositoryInterface
import javax.inject.Inject

class ProgramRepository @Inject constructor(
    private val programDAO: ProgramDAO
): ProgramRepositoryInterface {


    override suspend fun insertProgram(program: ModelProgram) {
        programDAO.insertProgram(program)
    }

    override suspend fun deleteProgram(id: String) {
        programDAO.deleteProgram(id)
    }

    override fun observeProgram(id: String): LiveData<ModelProgram> {
        return programDAO.observeProgram(id)
    }

    override fun observeAllProgram(): LiveData<List<ModelProgram>> {
        return programDAO.observeAllPrograms()
    }

    override suspend fun renameProgram(id: String, newTitle: String, newDate: Long) {
        programDAO.renameProgram(id,newTitle,newDate)
    }


}


