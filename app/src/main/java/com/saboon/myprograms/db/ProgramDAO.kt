package com.saboon.myprograms.db

import android.graphics.ColorSpace.Model
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.model.ModelProgram

@Dao
interface ProgramDAO {

    @Insert
    suspend fun insertProgram(program: ModelProgram)

    @Query("DELETE FROM programs WHERE id = :id")
    suspend fun deleteProgram(id: String)

    @Query("SELECT * FROM programs")
    fun observeAllPrograms(): LiveData<List<ModelProgram>>

    @Query("SELECT * FROM programs WHERE id = :id")
    fun observeProgram(id: String): LiveData<ModelProgram>

    @Query("UPDATE programs SET title = :newTitle , dateModified = :newDate WHERE id = :id")
    suspend fun renameProgram(id: String, newTitle: String, newDate: Long)




}