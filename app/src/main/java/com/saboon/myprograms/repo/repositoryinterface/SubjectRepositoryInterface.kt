package com.saboon.myprograms.repo.repositoryinterface

import androidx.lifecycle.LiveData
import com.saboon.myprograms.model.ModelSubject

interface SubjectRepositoryInterface {


    suspend fun insertSubject(subject: ModelSubject)
    suspend fun deleteSubject(id: String)

    fun observeSubject(id: String): LiveData<ModelSubject>

    fun observeAllSubject(): LiveData<List<ModelSubject>>

    fun observeAllSubjectByOwner(ownerId: String): LiveData<List<ModelSubject>?>

    suspend fun updateSubject(
        id: String,
        dateModified: String,
        title: String,
        person1: String,
        person2: String,
        person3: String,
        color: String
    )

}