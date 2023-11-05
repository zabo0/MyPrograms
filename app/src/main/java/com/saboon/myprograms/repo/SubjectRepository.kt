package com.saboon.myprograms.repo

import androidx.lifecycle.LiveData
import com.saboon.myprograms.db.SubjectDAO
import com.saboon.myprograms.model.ModelSubject
import javax.inject.Inject

class SubjectRepository @Inject constructor(
    private val subjectDAO: SubjectDAO
) {

    suspend fun insertSubject(subject: ModelSubject){
        subjectDAO.insertSubject(subject)
    }

    suspend fun deleteSubject(id:String){
        subjectDAO.deleteSubject(id)
    }

    fun observeSubject(id: String): LiveData<ModelSubject>{
        return subjectDAO.observeSubject(id)
    }

    fun observeAllSubject():LiveData<List<ModelSubject>>{
        return subjectDAO.observeAllSubjects()
    }
}
