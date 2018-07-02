package studentdetails

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.example.studentdetails.model.StudentDataSource
import com.example.studentdetails.model.StudentDetails
import android.arch.lifecycle.LiveData


class StudentDetailsViewModel : ViewModel()
{
    lateinit var mDataSource: StudentDataSource

    var studentDetails : ObservableField<StudentDetails> = ObservableField()
    var isLoading      = ObservableBoolean(false)
    var mStudemtID : String = ""
    var isDataCached = false

    var mStudentLiveData: LiveData<StudentDetails> ?= null
    get()
    {
        isLoading.set(true)

        return mDataSource.getStudentDetails(mStudemtID, object : StudentDataSource.StudentLoadedCallback
        {
            override fun onStudentLoaded()
            {
                isLoading.set(false)
                isDataCached = true
            }

            override fun onError()
            {
                // Handle Error
                isLoading.set(false)
            }
        }) as LiveData<StudentDetails>
    }

    fun start(studentId : String)
    {
        mStudemtID = studentId
        onLoadData()
    }

    fun onLoadData()
    {
        if(!isDataCached)
        {
            isLoading.set(true)
            mDataSource.refreshDB()
        }
    }

    fun setRepository(datasource: StudentDataSource)
    {
        mDataSource = datasource
    }
}