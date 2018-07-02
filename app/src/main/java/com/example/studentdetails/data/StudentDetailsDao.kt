package com.example.studentdetails.data

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.studentdetails.model.StudentDetails

@Dao
interface StudentDetailsDao
{
    @Query("SELECT * FROM student_details")
    fun getStudentList() : DataSource.Factory<Int, StudentDetails>

    @Query("SELECT * FROM student_details WHERE studentID = :studentId")
    fun getStudentDetails(studentId : String) : LiveData<StudentDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStudent(student : StudentDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun storeStudentList(studentList : ArrayList<StudentDetails>)
}