package com.example.studentdetails.model

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import common.K

interface StudentDataSource
{
    interface StudentLoadedCallback
    {
        fun onStudentLoaded()
        fun onError()
    }

    interface StudentListLoadedCallback
    {
        fun onStudentListLoaded()
        fun onError()
    }

    fun refreshDB()
    fun getStudentDetails(studentId : String, callback: StudentLoadedCallback) : K
    fun getStudentList(callback: StudentListLoadedCallback) : K
    fun saveStudentDetails(studentDetails: StudentDetails, callback: StudentLoadedCallback ?= null)
}