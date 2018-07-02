package com.example.studentdetails.model

import android.arch.persistence.room.*
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = "student_details")
//, indices = [Index(name = "student_id"), Index(value = ["studentID"]), Index(unique = true)]
class StudentDetails
{
    @PrimaryKey(autoGenerate = true)
    var id              : Int = 0
//    var id              : String  =  UUID.randomUUID().toString()
    var studentName     : String  =  ""
    var studentID : String = ""


    @Ignore
    fun setStudents(iD : Int, sid: String, name :String)
    {
        id = iD
        studentName = name
        studentID = sid
    }
}