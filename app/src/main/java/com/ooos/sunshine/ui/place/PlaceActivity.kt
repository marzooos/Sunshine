package com.ooos.sunshine.ui.place

import android.os.Build
import android.os.Bundle
import com.ooos.sunshine.BaseActivity
import com.ooos.sunshine.R

class PlaceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.purple_200)
        }
        setContentView(R.layout.activity_main)
    }
}