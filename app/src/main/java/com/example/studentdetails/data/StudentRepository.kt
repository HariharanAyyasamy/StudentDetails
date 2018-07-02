package data

import com.example.studentdetails.model.StudentDataSource
import com.example.studentdetails.model.StudentDetails
import common.EntityConstant
import common.K
import common.LocalDataSource
import common.RemoteDataSource


class StudentRepository(
        private val mDBDataSource   : LocalDataSource,
        private val mApiService     : RemoteDataSource)   : StudentDataSource
{

    private val DETAILS     = EntityConstant.STUDENT_DETAILS
    private val LIST        = EntityConstant.STUDENT_LIST
    private var refreshDB   = false
//    private var studentDetailsLD : LiveData<StudentDetails> = MutableLiveData()

    init { }

//    override fun getObservableDetails() = studentDetailsLD


    override fun refreshDB()
    {
        refreshDB = true
    }

    override fun getStudentDetails(studentId: String, callback: StudentDataSource.StudentLoadedCallback) : K
    {
        if(refreshDB)
        {
            getDetailsFromRemote(studentId, callback)
        }

        return getStudentDetailsFromDB(studentId, callback)

    }

    override fun getStudentList(callback: StudentDataSource.StudentListLoadedCallback) : K
    {
        if (refreshDB)
        {
            mApiService.getEntityList(LIST, object : RemoteDataSource.DataLoadedCallback
            {
                override fun onLoadingSuccess(data: K)
                {
                    storeStudentListInDb(data as ArrayList<StudentDetails>, callback)
                }

                override fun onLoadingError(errorCode: Int, errorMsg: String)
                {

                }
            })
        }

        return getStudentListFromDB(callback)
    }


    private fun getDetailsFromRemote(studentId: String, callback: StudentDataSource.StudentLoadedCallback)
    {
        mApiService.getEntityDetails(DETAILS, studentId, object : RemoteDataSource.DataLoadedCallback
        {
            override fun onLoadingSuccess(data: K)
            {
                saveStudentDetails(data as StudentDetails, callback)
            }

            override fun onLoadingError(errorCode: Int, errorMsg: String)
            {

            }

        })

        refreshDB = false
    }

    private fun getStudentDetailsFromDB(studentId: String, callback: StudentDataSource.StudentLoadedCallback) : K
    {
        return mDBDataSource.getEntityDetails(EntityConstant.STUDENT_DETAILS, studentId, object : LocalDataSource.DataLoadedCallback
        {
            override fun onDataLoaded()
            {


//                studentDetailsLD    = Transformations.switchMap(data)
//                { details ->
//
//                    data as LiveData<StudentDetails>// Returns Student Details
//                }

                callback.onStudentLoaded()
            }

            override fun onLoadingError(errorCode: Int, errorMsg: String)
            {
                refreshDB = true
                getDetailsFromRemote(studentId, callback)
            }

        })
    }

    private fun getStudentListFromDB(callback: StudentDataSource.StudentListLoadedCallback) : K
    {
        return mDBDataSource.getEntityList(LIST, object : LocalDataSource.DataLoadedCallback
        {
            override fun onDataLoaded()
            {
//                // create a data source factory from Room
//                val dataSourceFactory = data as DataSource.Factory<Int, StudentDetails>
//                val builder = LivePagedListBuilder(dataSourceFactory, 50)
////                        .setBoundaryCallback(boundaryCallback)

                callback.onStudentListLoaded()
            }

            override fun onLoadingError(errorCode: Int, errorMsg: String)
            {
                refreshDB = true
                getStudentList(callback)
            }

        })
    }

    override fun saveStudentDetails(studentDetails: StudentDetails, callback: StudentDataSource.StudentLoadedCallback?) {

        mDBDataSource.saveEntityDetails(DETAILS, studentDetails, object : LocalDataSource.DataStoredCallback
        {
            override fun onDataSaved()
            {
                if(callback != null)
                {
                    getStudentDetailsFromDB(studentDetails.studentID, callback)
                }
            }

            override fun onSaveError()
            {

            }
        })
    }



    fun storeStudentListInDb(studentList: ArrayList<StudentDetails>, callback: StudentDataSource.StudentListLoadedCallback)
    {
        mDBDataSource.saveEntityList(LIST, studentList, object : LocalDataSource.DataStoredCallback
        {
            override fun onDataSaved()
            {
                if(callback != null)
                {
                    getStudentListFromDB(callback)
                }
            }

            override fun onSaveError()
            {

            }
        })
    }

    companion object {

        private var INSTANCE: StudentRepository? = null

        @JvmStatic fun getInstance(tasksRemoteDataSource: RemoteDataSource, tasksLocalDataSource: LocalDataSource) = INSTANCE
                ?: synchronized(StudentRepository::class.java)
        {
            if (INSTANCE == null)
            {
                INSTANCE = StudentRepository(tasksLocalDataSource, tasksRemoteDataSource)
            }

            return INSTANCE!!
        }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

}