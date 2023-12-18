package com.rsix.library

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewTypeHolder<T : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * 在onBindViewHolder里回调
     */
    abstract fun onBind(data: T)
}

abstract class BaseBindingTypeHolder<B : ViewDataBinding, T : Any>(itemView: View) :
    BaseViewTypeHolder<T>(itemView) {
    protected val binding by lazy<B> {
        checkNotNull(DataBindingUtil.bind(itemView)) { "${this::class.java.canonicalName}'s itemView didn't has ViewDataBinding class" }
    }
}