package com.saboon.myprograms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.saboon.myprograms.model.ModelEvent
import com.saboon.myprograms.model.ModelProgram
import com.saboon.myprograms.model.ModelSubject

@Database(entities = [ModelProgram::class, ModelSubject::class, ModelEvent::class], version = 3, exportSchema = false)
abstract class MyProgsDatabase():RoomDatabase() {
    abstract fun programDao(): ProgramDAO
    abstract fun subjectDao(): SubjectDAO
    abstract fun eventDao(): EventDAO




}