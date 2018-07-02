package studentlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.databinding.ObservableBoolean
import com.example.studentdetails.model.StudentDataSource
import com.example.studentdetails.model.StudentDetails

class StudentListViewModel : ViewModel()
{
    private lateinit var mDataSource: StudentDataSource

    var isLoading      = ObservableBoolean(false)
    var isDataCached   = false

    private var listLD : LiveData<PagedList<StudentDetails>> ?= null
    var mListLD        : LiveData<PagedList<StudentDetails>> ?= null

        get()
        {
            if(listLD == null)
            {
                isLoading.set(true)
                isDataCached = true

                mDataSource.refreshDB()

                val data = mDataSource.getStudentList(object : StudentDataSource.StudentListLoadedCallback
                {
                    override fun onStudentListLoaded()
                    {
                        isLoading.set(false)
                    }
                    override fun onError()
                    {
                        isLoading.set(false)

                        listLD = null
                    }
                }) as DataSource.Factory<Int, StudentDetails>


                listLD = LivePagedListBuilder(data, PagedList.Config.Builder()
                        .setPageSize(50)
                        .setInitialLoadSizeHint(50)
                        .setEnablePlaceholders(false)
                        .build()
                ).build()
            }

            return listLD ?: throw AssertionError("Check if data is properly stored and fetched from Database")
        }

    fun start()
    {

    }

    fun setRepository(datasource: StudentDataSource)
    {
        mDataSource = datasource
    }
}