package com.rsix.droidkit

import android.app.Application

class App:Application() {
    companion object{
        lateinit var mContext:App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}