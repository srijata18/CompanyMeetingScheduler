package com.example.companymeetingscheduler.customViews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.companymeetingscheduler.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_snackbar.view.*


class CustomSnackView(private var view: View, context: Context) {

    var _mContext: Context? = context
    var layoutInflater : LayoutInflater? = null
    var layout : View? = null
    var snackbar : Snackbar? = null
    var snackView : View? = null
    var ondismissed : DismissClicked?= null

    init {
        layoutInflater = _mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layout = layoutInflater?.inflate(R.layout.custom_snackbar, null)
        snackView = layoutInflater?.inflate(R.layout.custom_snackbar, null)
    }

    fun createSnackBar()
    {
        if(view != null) {
            snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
            val layout = snackbar?.view as Snackbar.SnackbarLayout
            layout.addView(snackView, 0)
            snackView?.apply {
                btn_dismiss?.setOnClickListener {
                    snackbar?.dismiss()
                    ondismissed?.onDismissedClicked()
                }
            }
        }
    }

    fun dismissedClickedListner(dismissClicked: DismissClicked){
        ondismissed = dismissClicked
    }

    fun displaySnackBar()
    {
        snackbar!!.show()
    }

    interface DismissClicked{
        fun onDismissedClicked()
    }
}