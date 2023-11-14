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

    @Query("DELETE FROM events WHERE ownerId = :ownerId")
    suspend fun deleteAllEventByOwnerId(ownerId: String)

    @Query("SELECT * FROM events WHERE id = :id")
    fun observeEvent(id: String): LiveData<ModelEvent>

    @Query("SELECT * FROM events")
    fun observeAllEvent(): LiveData<List<ModelEvent>?>

    @Query("SELECT * FROM events WHERE ownerId = :ownerId")
    fun observeAllEventByOwnerId(ownerId: String): LiveData<List<ModelEvent>?>

    @Query("UPDATE events SET id= :id, dateModified= :dateModified, title= :title, description= :description, date= :date, timeStart= :timeStart, timeEnd= :timeEnd, place= :place, timeReminder= :timeReminder")
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