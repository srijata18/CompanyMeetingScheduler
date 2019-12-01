package com.example.companymeetingscheduler.contracts

interface IAddScheduleContract {

    interface View : BaseContract.View{
        fun enableSubmitBtn(isEnabled : Boolean)
    }

    interface Presenter{
        fun checkForPastDate(date : String?)
        fun bookMeeting(date : String?, startTime : String?, endTime : String?)
    }
}