//package studentlist
//
//import android.arch.paging.LivePagedListProvider
//import com.example.studentdetails.model.StudentDataSource
//import com.example.studentdetails.model.StudentDetails
//
//object StudentListprovider
//{
//    private val dataSource = object: StudentDataSource
//    {
//        override fun refreshDB() {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun getStudentDetails(studentId: String, callback: StudentDataSource.StudentLoadedCallback) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun getStudentList(callback: StudentDataSource.StudentListLoadedCallback) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun saveStudentDetails(studentDetails: StudentDetails, callback: StudentDataSource.StudentLoadedCallback?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun convertToItems(items: KitsuResponse, size: Int): List<StudentDetails>
//        {
//            return List(size, { index ->
//                items.data.elementAtOrElse(index, { KitsuItem(0, null, null) })
//            })
//        }
//    }
//
//    fun allKitsu(): LivePagedListProvider<Int, StudentDetails>
//    {
//        return object : LivePagedListProvider<Int, StudentDetails>()
//        {
//            override fun createDataSource(): KitsuLimitOffsetNetworkDataSource<StudentDetails> = dataSource
//        }
//    }
//
//    fun setQueryFilter(queryFilter: String)
//    {
//        dataSource.queryFilter = queryFilter
//    }
//}