package com.saboon.myprograms.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "programs")
data class ModelProgram(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "dateCreated")
    val dateCreated: Long,

    @ColumnInfo(name = "dateModified")
    val dateModified: Long,

    @ColumnInfo(name = "title")
    val title: String
)
