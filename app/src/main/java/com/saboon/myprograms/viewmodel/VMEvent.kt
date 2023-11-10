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

    fun observeEvent(id: String): LiveData<ModelEvent> {
        return repository.observeEvent(id)
    }

    fun observeAllEvent(): LiveData<List<ModelEvent>?> {
        return repository.observeAllEvent()
    }

    fun observeAllEventByOwnerId(ownerId: String): LiveData<List<ModelEvent>?> {
        return repository.observeAllEventByOwnerId(ownerId)
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
        timeReminder: Long
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
            timeReminder
        )
    }

}