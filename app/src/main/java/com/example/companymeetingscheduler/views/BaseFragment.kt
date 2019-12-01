package com.example.companymeetingscheduler.views

import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() , View.OnClickListener{

    abstract fun setClickListeners()

    abstract fun setToolbar()
}