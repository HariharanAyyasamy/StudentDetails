package studentlist

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studentdetails.R
import com.example.studentdetails.model.StudentDetails
import studentdetails.StudentDetailActivity

class StudentListViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    private val name : TextView = view.findViewById(R.id.student_name)
    private val id   : TextView = view.findViewById(R.id.student_id)
    private var details : StudentDetails ?= null

    init
    {
        view.setOnClickListener {

            val intent = Intent(view.context, StudentDetailActivity::class.java)
            intent.putExtra("student_id", details?.studentID)
            view.context.startActivity(intent)

            }
    }


    fun bind(details : StudentDetails)
    {
        this.details = details

        name.text = details.studentName
        id.text = details.studentID
    }


    companion object
    {
        fun create(parent: ViewGroup): StudentListViewHolder
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.student_list_item, parent, false)
            return StudentListViewHolder(view)
        }
    }
}