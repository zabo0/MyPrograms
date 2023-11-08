package com.saboon.myprograms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saboon.myprograms.model.ModelSubject
import com.saboon.myprograms.repo.repositoryinterface.SubjectRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VMFragmentSubject @Inject constructor(
    private val repository: SubjectRepositoryInterface
):ViewModel(){

    suspend fun insertSubject(subject: ModelSubject){
        repository.insertSubject(subject)
    }

    suspend fun deleteSubject(id: String){
        repository.deleteSubject(id)
    }

    fun observeSubject(id: String): LiveData<ModelSubject>{
        return repository.observeSubject(id)
    }

    fun observeAllSubject():LiveData<List<ModelSubject>>{
        return repository.observeAllSubject()
    }

    fun observeAllSubjectByOwner(id: String):LiveData<List<ModelSubject>?>{
        return repository.observeAllSubjectByOwner(id)
    }

    suspend fun updateSubject(newSubject: ModelSubject){

        return repository.updateSubject(
            newSubject.id,
            newSubject.dateModified.toString(),
            newSubject.title,
            newSubject.person1,
            newSubject.person2,
            newSubject.person3,
            newSubject.color)
    }
}