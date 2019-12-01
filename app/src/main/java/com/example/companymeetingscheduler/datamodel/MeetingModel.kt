package com.example.companymeetingscheduler.datamodel

data class MeetingModel(
    val description: String?= "",
    val end_time: String?= "",
    val participants: List<String>?= null,
    val start_time: String?=""
)