package studentlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentdetails.R
import common.MockApiController
import com.example.studentdetails.databinding.FragmentStudentListBinding
import common.DatabaseAccessor
import kotlinx.android.synthetic.main.fragment_student_list.*
import data.StudentRepository

/**
 * A placeholder fragment containing a simple view.
 */
class StudentListFragment : Fragment()
{
    private lateinit var viewDataBinding : FragmentStudentListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)

        viewDataBinding = FragmentStudentListBinding.bind(view).apply{

            viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application).create(StudentListViewModel::class.java).apply {

                setRepository(StudentRepository.getInstance(MockApiController.getInstance(), DatabaseAccessor.getInstance(context!!)))
            }
        }


        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        val mStudentListAdapter = StudentListPagedAdapter()
        viewDataBinding.viewmodel!!.mListLD!!.observe(this, Observer
        {
            mStudentListAdapter.submitList(it)
        })

        student_list_recycler_view.apply{

            layoutManager = (LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
            adapter = mStudentListAdapter
        }


        setupActionBar()
        loadData()
    }

    private fun setupActionBar()
    {

    }

    private fun loadData()
    {
        viewDataBinding.viewmodel!!.start()
    }

    override fun onDestroy()
    {
        super.onDestroy()
    }

    companion object {

        fun newInstance(bundle : Bundle) = StudentListFragment().apply {
            arguments = bundle
        }
    }
}
