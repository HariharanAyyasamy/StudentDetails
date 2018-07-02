package common

import com.example.studentdetails.model.StudentDetails
import java.util.*
import kotlin.collections.ArrayList

class MockApiController : RemoteDataSource
{
    val appExecutors = AppExecutors()

    override fun getEntityDetails(entityConstant: Int, entityId: String, callback: RemoteDataSource.DataLoadedCallback)
    {
        appExecutors.networkIO.execute{

            when(entityConstant)
            {
                EntityConstant.STUDENT_LIST ->
                {

                }
                EntityConstant.STUDENT_DETAILS ->
                {
                    Timer().schedule(object : TimerTask() {
                        override fun run()
                        {
                            appExecutors.mainThread.execute {

                                callback.onLoadingSuccess(StudentDetails().apply {
                                    setStudents(entityId.toInt(), entityId, "Student $entityId")
                                })
                            }
                        }
                    }, 4000)
                }
            }
        }
    }

    override fun getEntityList(entityConstant: Int, callback: RemoteDataSource.DataLoadedCallback)
    {
        appExecutors.networkIO.execute {

            when (entityConstant)
            {
                EntityConstant.STUDENT_LIST ->
                {
                    Timer().schedule(object : TimerTask()
                    {
                        override fun run()
                        {
                            val list = ArrayList<StudentDetails>()

                            for (i in 1..10000)
                            {
                                list.add(StudentDetails().apply {
                                    setStudents(i, sid = i.toString(), name = "Student $i")
                                })
                            }

                            appExecutors.mainThread.execute {

                                callback.onLoadingSuccess(list)
                            }
                        }
                    }, 4000)

                }
            }
        }
    }


    companion object {

        private var INSTANCE: MockApiController? = null

        @JvmStatic fun getInstance() : RemoteDataSource
        {
            if (INSTANCE == null)
            {
                INSTANCE = MockApiController()
            }

            return INSTANCE !!
        }

        @JvmStatic fun clearInstance()
        {
            INSTANCE = null
        }
    }

}