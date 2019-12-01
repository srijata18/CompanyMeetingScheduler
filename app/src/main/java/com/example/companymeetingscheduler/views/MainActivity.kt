package com.example.companymeetingscheduler.views

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.companymeetingscheduler.R
import com.example.companymeetingscheduler.constants.Constants


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openFragment(fragmentName: Fragment, tag: String) {
        val manager = supportFragmentManager
        val fragment = manager.findFragmentByTag(tag) ?: fragmentName
        manager.beginTransaction()
            .replace(R.id.container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun displayMsg(msg : String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager?.findFragmentByTag(Constants.addNewMeetingFragment) as? AddNewMeetingFragment?
        if(fragment != null) {
            supportFragmentManager?.popBackStack()
        }else{
            finish()
        }
    }
}
