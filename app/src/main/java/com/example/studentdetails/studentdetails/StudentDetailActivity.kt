package studentdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studentdetails.R
import studentdetails.StudentDetailFragment

class StudentDetailActivity : AppCompatActivity()
{
    @Override
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_student_details)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().apply {

            replace(R.id.contentFrame, StudentDetailFragment.newInstance(Bundle().apply {
                putString("student_id", intent.getStringExtra("student_id"))
            }))

//            val bun : Bundle = Bundle()
//            bun.putString("student_id", intent.getStringExtra("student_id"))
//            replace(container, StudentDetailFragment.newInstance(bun))
        }.commit()
    }


//    private fun obtainViewFragment() = supportFragmentManager.findFragmentById(R.id.contentFrame) ?: StudentDetailFragment.newInstance().apply
//    {
////        arguments = Bundle().apply{ putString(
////                AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID,
////                intent.getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID))
////        }
//    }

//    fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
//        supportFragmentManager.transact {
//            replace(frameId, fragment)
//        }
//    }

//    fun obtainViewModel(): AddEditTaskViewModel = obtainViewModel(StudentDetailsViewModel::class.java)
}