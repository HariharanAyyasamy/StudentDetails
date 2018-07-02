package studentdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentdetails.R
import common.MockApiController
import com.example.studentdetails.databinding.StudentDetailFragmentBinding
import data.StudentRepository
import common.DatabaseAccessor

class StudentDetailFragment : Fragment()
{
    private lateinit var viewDataBinding : StudentDetailFragmentBinding
    private lateinit var mStudentId      : String

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        mStudentId = arguments!!.getString("student_id")

        setupActionBar()
        loadData()

        viewDataBinding.viewmodel?.mStudentLiveData?.observe(this, Observer
        {
            viewDataBinding.viewmodel?.studentDetails?.set(it)

        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.student_detail_fragment, container, false)

        viewDataBinding = StudentDetailFragmentBinding.bind(view).apply{

            viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application).create(StudentDetailsViewModel::class.java).apply {

                setRepository(StudentRepository.getInstance(MockApiController.getInstance(), DatabaseAccessor.getInstance(context!!)))
            }
        }


//        viewDataBinding           = StudentDetailFragmentBinding.bind(view)
//        viewDataBinding.viewmodel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)!!.create(StudentDetailsViewModel::class.java)

        return  viewDataBinding.root
    }


    private fun setupActionBar()
    {

    }

    private fun loadData()
    {
        viewDataBinding.viewmodel!!.start(mStudentId)
    }

    override fun onDestroy()
    {
        StudentRepository.destroyInstance()
        super.onDestroy()
    }

    companion object
    {
        fun newInstance(bundle : Bundle) = StudentDetailFragment().apply {
            arguments = bundle
        }

//        fun newInstances(bundle: Bundle) : StudentDetailFragment
//        {
//            val frag = StudentDetailFragment()
//            frag.arguments = bundle
//
//            return frag
//        }
    }
}