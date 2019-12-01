package com.example.companymeetingscheduler.customViews

import android.widget.DatePicker
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import java.util.*


class CustomDatePicker(private val mContext: Context, val view : TextView?) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val yy = calendar.get(Calendar.YEAR)
        val mm = calendar.get(Calendar.MONTH)
        val dd = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(context!!, this, yy, mm, dd)
    }

    override fun onDateSet(view: DatePicker, yy: Int, mm: Int, dd: Int) {
        this.view?.text  = populateSetDate(yy, mm + 1, dd)
    }

    private fun populateSetDate(year: Int, month: Int, day: Int): String {
        return "$day-$month-$year"
    }
}