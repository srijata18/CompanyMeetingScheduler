package com.example.companymeetingscheduler.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.companymeetingscheduler.R
import com.example.companymeetingscheduler.contracts.IAddScheduleContract
import com.example.companymeetingscheduler.extensions.gone
import com.example.companymeetingscheduler.extensions.visible
import com.example.companymeetingscheduler.presenter.AddSchedulePresenter
import com.example.companymeetingscheduler.utils.Utils
import kotlinx.android.synthetic.main.fragment_addmeeting.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import java.util.*
import android.text.Editable
import android.text.TextWatcher
import com.example.companymeetingscheduler.constants.Constants
import com.example.companymeetingscheduler.customViews.CustomDatePicker
import com.example.companymeetingscheduler.customViews.CustomTimePicker


class AddNewMeetingFragment : BaseFragment() , View.OnClickListener , IAddScheduleContract.View{

    private var addSchedulePresenter : AddSchedulePresenter?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_addmeeting, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addSchedulePresenter = AddSchedulePresenter(this)
        enableSubmitBtn(false)
        setToolbar()
        setClickListeners()
    }

    override fun setToolbar(){
        addmeeting_toolbar?.apply {
            back?.text = getString(R.string.back)
            toolbar_title?.text = getString(R.string.schedule_a_meeting)
            next?.gone()
            iv_next?.gone()
        }
    }

    override fun setClickListeners(){
        addmeeting_toolbar?.iv_back?.setOnClickListener(this)
        addmeeting_toolbar?.back?.setOnClickListener(this)
        btn_submit?.setOnClickListener(this)
        spinner_meetingDate?.setOnClickListener(this)
        spinner_startTime?.setOnClickListener(this)
        spinner_endTime?.setOnClickListener(this)
        spinner_meetingDate?.addTextChangedListener(onTextChanged)
    }

    override fun onClick(view: View?) {
        when(view){
            addmeeting_toolbar?.back , addmeeting_toolbar?.iv_back ->{
                (activity as? MainActivity)?.supportFragmentManager?.popBackStack()
            }
            btn_submit -> {
                if ((activity as? MainActivity?)?.isNetworkAvailable() == true) {
                    addSchedulePresenter?.bookMeeting(spinner_meetingDate?.text?.toString(),
                        Utils.convert12hrsto24hrs(spinner_startTime?.text?.toString()),
                        Utils.convert12hrsto24hrs(spinner_endTime?.text?.toString()))
                }else{
                    activity?.applicationContext?.let{ Utils.displayNoInternetSnackBar(btn_submit,it)}
                }
            }
            spinner_meetingDate -> {
                val date = activity?.applicationContext?.let{
                    CustomDatePicker(
                        it,
                        spinner_meetingDate
                    )
                }
                date?.show(fragmentManager, Constants.DATEPICKER)
            }
            spinner_startTime -> {
                val time = activity?.applicationContext?.let{
                    CustomTimePicker(
                        it,
                        spinner_startTime
                    )
                }
                time?.show(fragmentManager, Constants.TIMEPICKER)
            }
            spinner_endTime -> {
                val time = activity?.applicationContext?.let{
                    CustomTimePicker(
                        it,
                        spinner_endTime
                    )
                }
                time?.show(fragmentManager, Constants.TIMEPICKER)
            }
        }
    }

    val onTextChanged = object : TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
            addSchedulePresenter?.checkForPastDate(p0?.toString())
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun onBookingResponse(msg : String) {
        (activity as? MainActivity)?.displayMsg(msg)
    }

    override fun hideProgress() {
        pb_schedule?.gone()
    }

    override fun showProgress() {
        pb_schedule?.visible()
    }
    override fun enableSubmitBtn(isEnabled : Boolean){
        btn_submit?.apply {
            isClickable = isEnabled
            this.isEnabled = isEnabled
            alpha = if (isEnabled)
                Constants.ALPHA_FULL
            else
                Constants.ALPHA_REDUCED
        }
    }
}