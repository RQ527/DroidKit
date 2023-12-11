package com.rsix.library

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseViewTypeListAdapter<T : BaseViewTypeItem> :
    ListAdapter<T, BaseViewTypeHolder<T>>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }) {
    open val holderFactory: HolderFactory
        get() {
            val clazz = Class.forName("${this::class.qualifiedName}Factory")
            val ob = clazz.getDeclaredField("Companion").get(null)
            return ob::class.java.getDeclaredMethod("getInstance").invoke(ob) as HolderFactory
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewTypeHolder<T> {
        return holderFactory.create(parent, viewType) as BaseViewTypeHolder<T>
    }

    override fun onBindViewHolder(holder: BaseViewTypeHolder<T>, position: Int) {
        try {
            holder.onBind(getItem(position))
        } catch (e: ClassCastException) {
            throw ClassCastException("adapter's data can't cast to holder's data ")
        }
    }

    final override fun getItemViewType(position: Int): Int {
        val viewType = getItem(position).viewType
        return getIntViewType(viewType)
    }

    override fun submitList(list: MutableList<T>?) {
        super.submitList(list?.filter {
            holderFactory.addViewType(
                it.viewType,
                getIntViewType(it.viewType)
            )
        })
    }

    override fun submitList(list: MutableList<T>?, commitCallback: Runnable?) {
        super.submitList(list?.filter {
            holderFactory.addViewType(
                it.viewType,
                getIntViewType(it.viewType)
            )
        }, commitCallback)
    }

    open fun getIntViewType(viewType: String) = viewType.hashCode()
}