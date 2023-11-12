package com.saboon.myprograms.util.generator

import java.util.UUID

class IdGenerator {


    fun generateProgramId(date: Long): String{
        val uuid = UUID.randomUUID()
        val d = DateTimeGenerator().convertLongToDateTime(date, "yyyy_MM_dd_HH_mm_ss")
        return "${d}_${uuid}"
    }

    fun generateSubjectId(date: Long, ownerProgramTitle: String): String{
        val uuid = UUID.randomUUID()
        val d = DateTimeGenerator().convertLongToDateTime(date, "yyyy_MM_dd_HH_mm_ss")
        return "${d}_${ownerProgramTitle}_${uuid}"
    }

    fun generateEventId(date: Long, ownerSubjectTitle: String): String{
        val uuid = UUID.randomUUID()
        val d = DateTimeGenerator().convertLongToDateTime(date, "yyyy_MM_dd_HH_mm_ss")
        return "${d}_${ownerSubjectTitle}_${uuid}"
    }


}