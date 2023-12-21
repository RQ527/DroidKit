package com.rsix.droidkit

import android.app.Application
import android.widget.Toast
import com.rsix.library.NetworkMonitor

class App:Application() {
    companion object{
        lateinit var mContext:App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        mContext = this
        NetworkMonitor.instance.init(this)
        NetworkMonitor.instance.addNetWorListener{
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}