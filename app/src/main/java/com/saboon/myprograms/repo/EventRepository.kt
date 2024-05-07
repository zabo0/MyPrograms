package com.saboon.myprograms.repo

import androidx.lifecycle.LiveData
import com.saboon.myprograms.db.EventDAO
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.repo.repositoryinterface.EventRepositoryInterface
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val eventDAO: EventDAO
) : EventRepositoryInterface {
    override suspend fun insertEvent(event: ModelEvent) {
        eventDAO.insertEvent(event)
    }

    override suspend fun deleteEvent(id: String) {
        eventDAO.deleteEvent(id)
    }

    override suspend fun deleteAllEventByOwnerSubjectId(ownerSubjectId: String) {
        eventDAO.deleteAllEventByOwnerSubjectId(ownerSubjectId)
    }

    override suspend fun deleteAllEventByOwnerProgramId(ownerProgramId: String) {
        eventDAO.deleteAllEventByOwnerProgramId(ownerProgramId)
    }

    override fun observeEvent(id: String): LiveData<ModelEvent> {
        return eventDAO.observeEvent(id)
    }

    override fun observeAllEvent(): LiveData<List<ModelEvent>?> {
        return eventDAO.observeAllEvent()
    }

    override fun observeAllEventByOwnerSubjectId(ownerSubjectId: String): LiveData<List<ModelEvent>?> {
        return eventDAO.observeAllEventByOwnerSubjectId(ownerSubjectId)
    }

    override fun observeAllEventByOwnerProgramId(ownerProgramId: String): LiveData<List<ModelEvent>?> {
        return eventDAO.observeAllEventByOwnerProgramId(ownerProgramId)
    }

    override suspend fun updateEvent(
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
        eventDAO.updateEvent(
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