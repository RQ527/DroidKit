package com.rsix.library

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewTypeHolder<T:Any>(itemView:View):RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(data:T)
}