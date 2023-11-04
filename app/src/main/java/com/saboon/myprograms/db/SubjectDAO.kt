package com.saboon.myprograms.db

import androidx.room.Dao
import androidx.room.Insert
import com.saboon.myprograms.model.ModelSubject

@Dao
interface SubjectDAO {

    @Insert
    suspend fun insertSubject(subject: ModelSubject)
}