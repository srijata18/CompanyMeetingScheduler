package com.example.companymeetingscheduler.contracts

interface BaseContract {

    interface View{
        fun onBookingResponse(msg : String)
        fun showProgress()
        fun hideProgress()
    }
}
