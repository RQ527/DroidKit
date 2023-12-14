package com.rsix.library

import android.view.ViewGroup

abstract class HolderFactory {
    private val mTypes = mutableMapOf<String, Int>()
    abstract fun create(parent: ViewGroup, viewType: Int): BaseViewTypeHolder<*>

    fun getIntViewType(viewType: String): Int? {
        return mTypes[viewType]
    }

    fun getStrViewType(viewType: Int): String {
        mTypes.forEach {
            if (it.value == viewType) return it.key
        }
        throw RuntimeException(" int viewType(value is $viewType) isn't in mTypes")
    }

    fun addViewType(viewType: String, value: Int): Boolean =
        viewType.takeIf { mTypes.containsKey(viewType) }?.let { mTypes[it] = value
            true
        } ?: false

    protected fun addViewType(viewType: String) {
        mTypes[viewType] = Int.MIN_VALUE
    }
}