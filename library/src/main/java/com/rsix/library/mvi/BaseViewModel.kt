package com.rsix.library.mvi

import androidx.lifecycle.ViewModel
import com.rsix.library.NetworkMonitor

abstract class BaseViewModel : ViewModel() {
    protected fun filterNetwork() = NetworkMonitor.instance.isNetActive

}