package common

import android.content.Context
import com.example.studentdetails.data.StudentDatabase
import com.example.studentdetails.model.StudentDetails

class DatabaseAccessor(private val mDB : StudentDatabase, private val appExecutors: AppExecutors) : LocalDataSource
{

    override fun getEntityDetails(entityConstant: Int, entityId: String, callback: LocalDataSource.DataLoadedCallback) : K
    {
        when(entityConstant)
        {
            EntityConstant.STUDENT_DETAILS ->
            {
                callback.onDataLoaded()

                return mDB.studentDetailsDao().getStudentDetails(entityId)
            }
        }

        return ""
    }

    override fun getEntityList(entityConstant: Int, callback: LocalDataSource.DataLoadedCallback) : K
    {
        when(entityConstant)
        {
            EntityConstant.STUDENT_LIST ->
            {
                callback.onDataLoaded()

                return mDB.studentDetailsDao().getStudentList()
            }
        }

        return ""
    }


    override fun saveEntityDetails(entityConstant: Int, data: K, callback: LocalDataSource.DataStoredCallback)
    {
        appExecutors.diskIO.execute{

            when(entityConstant)
            {
                EntityConstant.STUDENT_DETAILS ->
                {
                    mDB.studentDetailsDao().addStudent(data as StudentDetails)

                    appExecutors.mainThread.execute{  callback.onDataSaved() }
                }
            }
        }
    }

    override fun saveEntityList(entityConstant: Int, data: K, callback: LocalDataSource.DataStoredCallback)
    {
        appExecutors.diskIO.execute{

            when(entityConstant)
            {
                EntityConstant.STUDENT_LIST ->
                {
                    mDB.studentDetailsDao().storeStudentList(data as ArrayList<StudentDetails>)

                    appExecutors.mainThread.execute{  callback.onDataSaved() }
                }
            }
        }
    }


    companion object {

        private var INSTANCE: DatabaseAccessor? = null

        @JvmStatic fun getInstance(context : Context) : LocalDataSource
        {
            if (INSTANCE == null)
            {
                INSTANCE = DatabaseAccessor(StudentDatabase.getInstance(context), AppExecutors())
            }

            return INSTANCE !!
        }

        @JvmStatic fun clearInstance()
        {
            INSTANCE = null
        }
    }

}