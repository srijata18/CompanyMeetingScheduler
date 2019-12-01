package com.example.companymeetingscheduler.contracts

import com.example.companymeetingscheduler.datamodel.MeetingModel
import java.util.*

interface IHomeContract {
    interface View : BaseContract.View{
        fun updateAdapter(value : Array<MeetingModel>)
    }

    interface Presenter{
        fun loadData(date : String)
    }
}