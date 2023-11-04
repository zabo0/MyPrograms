package com.saboon.myprograms.repo

import com.saboon.myprograms.db.SubjectDAO
import com.saboon.myprograms.model.ModelSubject
import javax.inject.Inject

class SubjectRepository @Inject constructor(
    private val subjectDAO: SubjectDAO
) {

    suspend fun insertSubject(subject: ModelSubject){
        subjectDAO.insertSubject(subject)
    }
}
