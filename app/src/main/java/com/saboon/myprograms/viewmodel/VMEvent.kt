package com.saboon.myprograms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.repo.repositoryinterface.EventRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VMEvent @Inject constructor(
    private val repository: EventRepositoryInterface
) : ViewModel() {

    suspend fun insertEvent(event: ModelEvent) {
        repository.insertEvent(event)
    }

    suspend fun deleteEvent(id: String) {
        repository.deleteEvent(id)
    }

    suspend fun deleteAllEventByOwnerId(ownerId: String){
        repository.deleteAllEventByOwnerSubjectId(ownerId)
    }

    fun observeEvent(id: String): LiveData<ModelEvent> {
        return repository.observeEvent(id)
    }

    fun observeAllEvent(): LiveData<List<ModelEvent>?> {
        return repository.observeAllEvent()
    }

    fun observeAllEventByOwnerSubjectId(ownerSubjectId: String): LiveData<List<ModelEvent>?> {
        return repository.observeAllEventByOwnerSubjectId(ownerSubjectId)
    }

    fun observeAllEventByOwnerProgramId(ownerProgramId: String): LiveData<List<ModelEvent>?> {
        return repository.observeAllEventByOwnerProgramId(ownerProgramId)
    }

    suspend fun updateEvent(
        id: String,
        dateModified: Long,
        title: String,
        description: String,
        date: Long,
        timeStart: Long,
        timeEnd: Long,
        place: String,
        timeReminder: Int,
        repeat: Int
    ) {
        repository.updateEvent(
            id,
            dateModified,
            title,
            description,
            date,
            timeStart,
            timeEnd,
            place,
            timeReminder,
            repeat
        )
    }

}