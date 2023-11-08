package com.saboon.myprograms.db

import android.graphics.ColorSpace.Model
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.model.ModelSubject

@Dao
interface SubjectDAO {

    @Insert
    suspend fun insertSubject(subject: ModelSubject)

    @Query("DELETE FROM subjects WHERE id = :id")
    suspend fun deleteSubject(id:String)

    @Query("SELECT * FROM subjects WHERE id = :id")
    fun observeSubject(id: String):LiveData<ModelSubject>

    @Query("SELECT * FROM subjects")
    fun observeAllSubjects(): LiveData<List<ModelSubject>>

    @Query("SELECT * FROM subjects WHERE ownerId = :id")
    fun observeAllSubjectByOwner(id: String): LiveData<List<ModelSubject>?>

    @Query("UPDATE subjects SET dateModified = :dateModified, title = :title, person1 = :person1, person2 = :person2, person3 = :person3, color = :color WHERE id = :id")
    suspend fun updateSubject(id: String, dateModified: String, title: String, person1: String, person2: String, person3: String, color: String)
}