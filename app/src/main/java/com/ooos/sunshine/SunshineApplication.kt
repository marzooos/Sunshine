package com.ooos.sunshine

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunshineApplication : Application() {

    // 创建一个全局的静态 Context 并延迟初始化.
    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "s9WQUEfoQppcAjNN"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}