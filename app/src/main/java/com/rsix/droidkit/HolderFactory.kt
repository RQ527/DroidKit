package com.rsix.droidkit

import android.view.ViewGroup

abstract class HolderFactory {
    abstract fun create(parent: ViewGroup, viewType: Int):BaseViewTypeHolder<*>

    open fun getViewTypeByString(viewType: String) = viewType.hashCode()
}