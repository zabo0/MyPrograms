package com.saboon.myprograms.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class ModelEvent(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "dateCreated")
    val dateCreated: Long,

    @ColumnInfo(name = "dateModified")
    val dateModified: Long,

    @ColumnInfo(name = "ownerId")
    val ownerId : String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description : String,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name= "timeStart")
    val timeStart :Long,

    @ColumnInfo(name = "timeEnd")
    val timeEnd: Long,

    @ColumnInfo(name = "place")
    val place : String,

    @ColumnInfo(name = "timeReminder")
    val timeReminder: Long
)
