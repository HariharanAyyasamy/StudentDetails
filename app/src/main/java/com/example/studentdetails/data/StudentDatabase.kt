package com.example.studentdetails.data

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.studentdetails.model.StudentDetails
import android.arch.persistence.room.Room
import android.content.Context


@Database (entities = [(StudentDetails::class)], version = 1)
abstract class StudentDatabase : RoomDatabase()
{

    //    DatabaseTables

   /*1*/ abstract fun studentDetailsDao() : StudentDetailsDao


    companion object
    {
        private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase
        {
            synchronized(Any())
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, StudentDatabase::class.java, "students_db").build()
                }
            }

            return INSTANCE as StudentDatabase
        }

    }


}