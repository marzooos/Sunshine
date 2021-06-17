package com.ooos.sunshine

import android.content.Intent
import android.os.Bundle
import com.ooos.sunshine.ui.place.PlaceActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, PlaceActivity::class.java)
        startActivity(intent)
        finish()
    }
}