package com.example.companymeetingscheduler.presenter

import android.util.Log
import com.example.companymeetingscheduler.constants.Constants
import com.example.companymeetingscheduler.contracts.IAddScheduleContract
import com.example.companymeetingscheduler.datamodel.MeetingModel
import com.example.companymeetingscheduler.network.RetrofitClientInstance
import com.example.companymeetingscheduler.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AddSchedulePresenter (private val view : IAddScheduleContract.View) : IAddScheduleContract.Presenter {

    private val service = RetrofitClientInstance.create()//getRetrofitInstance()?.create(GetDetails::class.java)
    private val url = "api/schedule?date="

    override fun checkForPastDate(date: String?) {
        if (Utils.checkPastDate(date)){
            view.enableSubmitBtn(true)
        }else{
            view.enableSubmitBtn(false)
        }
    }

    override fun bookMeeting(date : String?, startTime : String?, endTime : String?){
        view.showProgress()
        date?.let {
            getMeetings(date, startTime?:"", endTime?:"")
        }
    }


    private fun getMeetings(date: String, startTime : String, endString : String){
        val call = service.getMeetings("$url$date")
        call.enqueue(object : Callback<Array<MeetingModel>> {
            override fun onResponse(call: Call<Array<MeetingModel>>, response: Response<Array<MeetingModel>>) {
                if (response.body() != null && response.body() is Array<MeetingModel>) {
                    var isSlotAvailable = true
                    for (data in response.body()!!.indices) {
                        val valueForStartTime = response.body()!![data].start_time?.let {
                            response.body()!![data].end_time?.let { it1 ->
                            Utils.checkTime(startTime, it,
                                it1 , true) } }
                        val valueForEndTime = response.body()!![data].end_time?.let {
                            response.body()!![data].end_time?.let { it1 ->
                                Utils.checkTime(endString, it,
                                    it1 , false) } }
                        if (valueForEndTime == true || valueForStartTime == true ){
                            view.onBookingResponse("Slots not available")
                            isSlotAvailable = false
                            break
                        }
                    }
                    if (isSlotAvailable){
                        view.onBookingResponse("Slots available")
                    }
                }
                Log.i("INFO::","Success : ${response.body()}")
                view.hideProgress()
            }

            override fun onFailure(call: Call<Array<MeetingModel>>, t: Throwable) {
                view.apply {
                    hideProgress()
                    onBookingResponse("Error while fetching data")
                }
                Log.i("INFO::","Error : ${t.stackTrace}")
            }
        })
    }
}