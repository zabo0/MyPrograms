package com.saboon.myprograms.repo

import androidx.lifecycle.LiveData
import com.saboon.myprograms.db.SubjectDAO
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.repo.repositoryinterface.SubjectRepositoryInterface
import javax.inject.Inject

class SubjectRepository @Inject constructor(
    private val subjectDAO: SubjectDAO
): SubjectRepositoryInterface {

    override suspend fun insertSubject(subject: ModelSubject){
        subjectDAO.insertSubject(subject)
    }

    override suspend fun deleteSubject(id:String){
        subjectDAO.deleteSubject(id)
    }

    override fun observeSubject(id: String): LiveData<ModelSubject>{
        return subjectDAO.observeSubject(id)
    }

    override fun observeAllSubject():LiveData<List<ModelSubject>>{
        return subjectDAO.observeAllSubjects()
    }

    override fun observeAllSubjectByOwner(id: String): LiveData<List<ModelSubject>?> {
        return subjectDAO.observeAllSubjectByOwner(id)
    }
}
