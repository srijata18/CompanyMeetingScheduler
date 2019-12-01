package com.example.companymeetingscheduler.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.companymeetingscheduler.R
import com.example.companymeetingscheduler.constants.Constants
import com.example.companymeetingscheduler.contracts.IHomeContract
import com.example.companymeetingscheduler.datamodel.MeetingModel
import com.example.companymeetingscheduler.extensions.gone
import com.example.companymeetingscheduler.extensions.visible
import com.example.companymeetingscheduler.presenter.HomePresenter
import com.example.companymeetingscheduler.utils.Utils
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import okhttp3.internal.Util


class HomeFragment  : BaseFragment() , IHomeContract.View{

    var presenter : HomePresenter? = null
    var adapter : MeetingListAdapter? = null
    var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = HomePresenter(this)
        adapter = MeetingListAdapter(arrayListOf())
        setToolbar()
        setClickListeners()
        setAdapter()
        getMeetingsForDate(Utils.getTodaysDate())
    }

    private fun getMeetingsForDate(date : String){
        rv_meetingList?.gone()
        if((activity as? MainActivity?)?.isNetworkAvailable() == true){
            presenter?.loadData(date)
            tv_nodata?.gone()
        }else {
            (activity as? MainActivity?)?.applicationContext?.let {
                Utils.displayNoInternetSnackBar(
                    home_toolbar,
                    it
                )
            }
            tv_nodata?.visible()
        }
    }

    private fun setAdapter(){
        val mLinearLayoutManager = LinearLayoutManager(activity)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_meetingList?.apply {
            layoutManager = mLinearLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    this@HomeFragment.activity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = this@HomeFragment.adapter
        }
    }

    override fun setToolbar(){
        home_toolbar?.apply {
            back?.text = getString(R.string.prev)
            toolbar_title?.text = Utils.getTodaysDate()
            next?.visible()
            iv_next?.visible()
        }
    }

    override fun setClickListeners() {
        home_toolbar?.apply {
            iv_back?.setOnClickListener(this@HomeFragment)
            back?.setOnClickListener(this@HomeFragment)
            next?.setOnClickListener(this@HomeFragment)
            iv_next?.setOnClickListener(this@HomeFragment)
        }
        btn_schedule?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view){
            home_toolbar?.iv_back , home_toolbar?.back -> {
                calculatePrevDateMeetings()
            }
            home_toolbar?.iv_next , home_toolbar?.next -> {
                calculateNextDateMeetings()
            }
            btn_schedule -> {
                (activity as? MainActivity)?.openFragment(AddNewMeetingFragment(), Constants.addNewMeetingFragment)
            }
        }
    }

    private fun calculateNextDateMeetings(){
        val date = Utils.getRequiredDate(++count)
        home_toolbar?.toolbar_title?.text = date
        getMeetingsForDate(date)
    }

    private fun calculatePrevDateMeetings(){
        val date =  Utils.getRequiredDate(--count)
        home_toolbar?.toolbar_title?.text = date
        getMeetingsForDate(date)
    }

    override fun updateAdapter(value: Array<MeetingModel>) {
        val listOFMeetings = arrayListOf<MeetingModel>()
        listOFMeetings.addAll(value)
        adapter?.updateItem(listOFMeetings)
        if (value?.size>0)
        rv_meetingList?.visible()
        else{
            rv_meetingList?.gone()
            tv_nodata?.visible()
        }
    }

    override fun hideProgress() {
        pb_home?.gone()
    }

    override fun onBookingResponse(msg: String) {
        (activity as? MainActivity)?.displayMsg(msg)
    }

    override fun showProgress() {
        pb_home?.visible()
    }

}