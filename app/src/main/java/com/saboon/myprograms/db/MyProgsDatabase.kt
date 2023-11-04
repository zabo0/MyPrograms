package com.saboon.myprograms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject

@Database(entities = [ModelProgram::class, ModelSubject::class], version = 1, exportSchema = false)
abstract class MyProgsDatabase():RoomDatabase() {
    abstract fun programDao(): ProgramDAO
    abstract fun subjectDao(): SubjectDAO

}