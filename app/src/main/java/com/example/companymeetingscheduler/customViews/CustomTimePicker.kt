package com.example.companymeetingscheduler.customViews

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*
import android.text.format.DateFormat.is24HourFormat
import com.example.companymeetingscheduler.utils.Utils
import java.text.DateFormat


class CustomTimePicker(private val mContext: Context, val view : TextView?) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(context, this, hour, minute,true)
    }

    override fun onTimeSet(view: TimePicker, hh: Int, mm: Int) {
        this.view?.text  = Utils.convert24hrsto12hrs(populateSetDate(hh, mm ))
    }

    private fun populateSetDate(hr: Int, mm: Int): String {
        return "$hr:$mm"
    }


}