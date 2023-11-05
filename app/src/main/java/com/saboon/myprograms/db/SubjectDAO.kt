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
}