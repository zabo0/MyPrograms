package com.saboon.myprograms.util

import java.util.UUID

class IdGenerator {


    fun generateProgramId(date: Long): String{
        val uuid = UUID.randomUUID()
        val d = DateGenerator().convertLongDateToString(date, "yyyy_MM_dd_HH_mm_ss")
        return "${d}_${uuid}"
    }

    fun generateSubjectId(date: Long, ownerProgramTitle: String): String{
        val uuid = UUID.randomUUID()
        val d = DateGenerator().convertLongDateToString(date, "yyyy_MM_dd_HH_mm_ss")
        return "${d}_${ownerProgramTitle}_${uuid}"
    }


}