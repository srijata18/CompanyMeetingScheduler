package com.example.companymeetingscheduler.presenter

import android.util.Log
import com.example.companymeetingscheduler.contracts.IHomeContract
import com.example.companymeetingscheduler.datamodel.MeetingModel
import com.example.companymeetingscheduler.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomePresenter (val view : IHomeContract.View) : IHomeContract.Presenter {

    private val service = RetrofitClientInstance.create()//getRetrofitInstance()?.create(GetDetails::class.java)
    private val url = "api/schedule?date="

    override fun loadData(date : String){
        view.showProgress()
        getMeetings(date)
    }

    private fun getMeetings(date: String){
        val call = service?.getMeetings("$url$date")
        call?.enqueue(object : Callback<Array<MeetingModel>> {
            override fun onResponse(call: Call<Array<MeetingModel>>, response: Response<Array<MeetingModel>>) {
                if (response.body() != null && response.body() is Array<MeetingModel>) {
                    view.updateAdapter(response.body() as Array<MeetingModel>)
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