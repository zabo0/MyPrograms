package com.saboon.myprograms.repo.repositoryinterface

import androidx.lifecycle.LiveData
import com.saboon.myprograms.model.ModelSubject

interface SubjectRepositoryInterface {


    suspend fun insertSubject(subject: ModelSubject)
    suspend fun deleteSubject(id: String)

    fun observeSubject(id: String): LiveData<ModelSubject>

    fun observeAllSubject(): LiveData<List<ModelSubject>>

    fun observeAllSubjectByOwner(id: String): LiveData<List<ModelSubject>?>

}