package com.saboon.myprograms.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.model.ModelEvent


@Dao
interface EventDAO {

    @Insert
    suspend fun insertEvent(event: ModelEvent)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteEvent(id: String)

    @Query("DELETE FROM events WHERE ownerSubjectId = :ownerSubjectId")
    suspend fun deleteAllEventByOwnerSubjectId(ownerSubjectId: String)

    @Query("DELETE FROM events WHERE ownerProgramId = :ownerProgramId")
    suspend fun deleteAllEventByOwnerProgramId(ownerProgramId: String)

    @Query("SELECT * FROM events WHERE id = :id")
    fun observeEvent(id: String): LiveData<ModelEvent>

    @Query("SELECT * FROM events")
    fun observeAllEvent(): LiveData<List<ModelEvent>?>

    @Query("SELECT * FROM events WHERE ownerSubjectId = :ownerSubjectId")
    fun observeAllEventByOwnerSubjectId(ownerSubjectId: String): LiveData<List<ModelEvent>?>

    @Query("SELECT * FROM events WHERE ownerProgramId = :ownerProgramId")
    fun observeAllEventByOwnerProgramId(ownerProgramId: String): LiveData<List<ModelEvent>?>

    @Query("UPDATE events SET dateModified= :dateModified, title= :title, description= :description, date= :date, timeStart= :timeStart, timeEnd= :timeEnd, place= :place, timeReminder= :timeReminder, repeat = :repeat WHERE id = :id")
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
    )
}