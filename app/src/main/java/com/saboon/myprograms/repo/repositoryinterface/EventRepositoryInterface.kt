package com.saboon.myprograms.repo.repositoryinterface

import androidx.lifecycle.LiveData
import com.saboon.myprograms.model.ModelEvent

interface EventRepositoryInterface {


    suspend fun insertEvent(event: ModelEvent)

    suspend fun deleteEvent(id: String)

    suspend fun deleteAllEventByOwnerId(ownerId: String)

    fun observeEvent(id: String): LiveData<ModelEvent>

    fun observeAllEvent(): LiveData<List<ModelEvent>?>

    fun observeAllEventByOwnerId(ownerId: String): LiveData<List<ModelEvent>?>

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
    )
}