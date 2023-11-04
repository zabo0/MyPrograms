package com.saboon.myprograms.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "subjects")
data class ModelSubject(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "dateCreated")
    val dateCreated: Long,

    @ColumnInfo(name = "dateModified")
    val dateModified: Long,

    @ColumnInfo(name = "ownerId")
    val ownerId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "person1")
    val person1: String,

    @ColumnInfo(name = "person2")
    val person2: String,

    @ColumnInfo(name = "person3")
    val person3: String
)
