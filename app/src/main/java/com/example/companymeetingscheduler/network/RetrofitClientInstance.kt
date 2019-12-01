package com.example.companymeetingscheduler.network

import com.example.companymeetingscheduler.constants.Constants
import com.example.companymeetingscheduler.datamodel.MeetingModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url



interface RetrofitClientInstance {

    @GET
    fun getMeetings(@Url url: String): Call<Array<MeetingModel>>

    companion object RetroClient {
        fun create() : RetrofitClientInstance {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RetrofitClientInstance::class.java)
        }
    }
}