package com.saboon.myprograms.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class DatabaseMigration(startVersion: Int, endVersion: Int): Migration(startVersion, endVersion) {


    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE `events` (`id` TEXT, `dateCreated` LONG, `dateModified` LONG, `ownerId` TEXT, `title` TEXT, `description` TEXT, `date` LONG, `timeStart` LONG, `timeEnd` LONG, `place` TEXT, `timeReminder` LONG)")
    }
}