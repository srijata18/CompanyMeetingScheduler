package com.example.companymeetingscheduler.utils

import android.content.Context
import android.view.View
import com.example.companymeetingscheduler.constants.Constants
import com.example.companymeetingscheduler.customViews.CustomSnackView
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {

        var noInternetConnectionSnackbar: CustomSnackView? = null

        fun hideNoInternetSnackBar() {
            noInternetConnectionSnackbar?.let{
                it.snackbar?.dismiss()
                noInternetConnectionSnackbar = null
            }
        }

        //display no internet snackbar
        fun displayNoInternetSnackBar(view: View?, context: Context): CustomSnackView {
            noInternetConnectionSnackbar = CustomSnackView(view!!, context)
            noInternetConnectionSnackbar!!.createSnackBar()
            noInternetConnectionSnackbar!!.displaySnackBar()
            noInternetConnectionSnackbar!!.dismissedClickedListner(object : CustomSnackView.DismissClicked {
                override fun onDismissedClicked() {
                    noInternetConnectionSnackbar = null
                }
            }
            )
            return noInternetConnectionSnackbar!!
        }

        //return false if date is Past from todays date
       fun checkPastDate(date : String?) : Boolean{
           return Date().before(SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).parse(date))
       }

        //fetches current date
        fun getTodaysDate() : String{
            val date = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
            return dateFormat.format(date)
        }

        //fetches required date based on count provided
        fun getRequiredDate(count : Int): String {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, count)
            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
            return dateFormat.format(cal.time)
        }

        //checks two time
        fun checkTime(inputTime : String, startTime : String, endTime : String , isCheckForStartTime : Boolean):Boolean{
            val time1 = SimpleDateFormat(Constants.TWENTY_FOUR_HR_PATTERN, Locale.getDefault()).parse(startTime)
            val calendar1 = Calendar.getInstance()
            calendar1.time = time1
            calendar1.add(Calendar.DATE, 1)

            val time2 = SimpleDateFormat(Constants.TWENTY_FOUR_HR_PATTERN, Locale.getDefault()).parse(endTime)
            val calendar2 = Calendar.getInstance()
            calendar2.time = time2
            calendar2.add(Calendar.DATE, 1)

            val d = SimpleDateFormat(Constants.TWENTY_FOUR_HR_PATTERN, Locale.getDefault()).parse(inputTime)
            val calendar3 = Calendar.getInstance()
            calendar3.time = d
            calendar3.add(Calendar.DATE, 1)

            val x = calendar3.time

            if (isCheckForStartTime) {
                return (x.after(calendar1.time) && x.before(calendar2.time)) || x.time.equals(calendar1.time)
            }else{
                return (x.after(calendar1.time) && x.before(calendar2.time)) || x.time.equals(calendar2.time)
            }
        }

        //converts 24hrs date string to 12hrs
        fun convert24hrsto12hrs(time : String?) : String?{
            val sdf = SimpleDateFormat(Constants.TWENTY_FOUR_HR_PATTERN)
            val dateObj = sdf.parse(time);
            return SimpleDateFormat(Constants.TWELVE_HR_PATTERN).format(dateObj).toUpperCase()
        }

        //converts 12hrs date string to 24hrs
        fun convert12hrsto24hrs(date : String?) : String?{
            val displayFormat = SimpleDateFormat(Constants.TWENTY_FOUR_HR_PATTERN)
            val parseFormat = SimpleDateFormat(Constants.TWELVE_HR_PATTERN)
            val date = parseFormat.parse(date)
            return displayFormat.format(date)
        }

    }
}