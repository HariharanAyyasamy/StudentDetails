package studentlist

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.example.studentdetails.model.StudentDetails

class StudentListPagedAdapter : PagedListAdapter<StudentDetails, StudentListViewHolder>(POST_COMPARATOR)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentListViewHolder
    {
        return StudentListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudentListViewHolder, position: Int)
    {
        return holder.bind(getItem(position)!!)
    }

    override fun onBindViewHolder(holder: StudentListViewHolder, position: Int, payloads: MutableList<Any>)
    {
        if (payloads.isNotEmpty())
        {
          // Need to handle case with payload
        }
        else
        {
            onBindViewHolder(holder, position)
        }
    }

    companion object
    {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<StudentDetails>()
        {
            override fun areContentsTheSame(oldItem: StudentDetails, newItem: StudentDetails): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: StudentDetails, newItem: StudentDetails): Boolean =
                    oldItem.studentID == newItem.studentID

            override fun getChangePayload(oldItem: StudentDetails, newItem: StudentDetails): Any?
            {
                return null
            }
        }
    }
}