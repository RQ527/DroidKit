package com.rsix.droidkit

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.ClassCastException

abstract class BaseViewTypeAdapter<B :BaseViewTypeItem> :RecyclerView.Adapter<BaseViewTypeHolder<B>>(){

    private var mDataList = mutableListOf<B>()

    /**
     * 获取holderFactory，默认反射获取，可build后重写获取实例
     * 例：
     * class TestAdapter(
     *     override val holderFactory: HolderFactory = TestAdapterFactory.instance
     * ) :
     *     BaseViewTypeAdapter<BaseViewTypeItem>() {
     * }
     */
    open val holderFactory:HolderFactory
        get() {
            val clazz = Class.forName("${this::class.qualifiedName}Factory")
            val ob = clazz.getDeclaredField("Companion").get(null)
            return ob::class.java.getDeclaredMethod("getInstance").invoke(ob) as HolderFactory
        }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewTypeHolder<B> {
        return holderFactory.create(parent, viewType) as BaseViewTypeHolder<B>
    }

     final override fun onBindViewHolder(holder: BaseViewTypeHolder<B>, position: Int) {
         mDataList.getOrNull(position)?.let {
             try {
                 holder.onBind(it)
             }catch (e:ClassCastException){
                 throw ClassCastException("adapter's data can't cast to holder's data ")
             }
         }
     }

    final override fun getItemViewType(position: Int): Int {
        return mDataList.getOrNull(position)?.viewType?.hashCode() ?: 0
    }

    final override fun getItemCount(): Int = mDataList.size

    open fun addItem(item: B?, notifyDataSetChange: Boolean = true) {
        mDataList.add(item ?: return)
        if (notifyDataSetChange) {
            notifyItemInserted(itemCount - 1)
        }
    }

    open fun setItemList(itemList: List<B>?, notifyDataSetChange: Boolean = true) {
        if (itemList.isNullOrEmpty()) return
        mDataList.clear()
        mDataList.addAll(itemList)
        if (notifyDataSetChange) {
            notifyDataSetChanged()
        }
    }

    open fun addItemList(itemList: List<B>?, notifyDataSetChange: Boolean = true) {
        if (itemList.isNullOrEmpty()) return
        val insertPosition = itemCount
        mDataList.addAll(itemList)
        if (notifyDataSetChange) {
            notifyItemRangeInserted(insertPosition, itemList.size)
        }
    }

    open fun updateData(newData: MutableList<B>?) {
        val old = mDataList
        mDataList = newData ?: mutableListOf()
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = old.size
            override fun getNewListSize(): Int = mDataList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition].id == mDataList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return old[oldItemPosition] == mDataList[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }

    open fun getItem(position: Int): B? {
        return mDataList.getOrNull(position)
    }

    open fun replaceItem(position: Int, item: B?) {
        if (item != null) {
            mDataList[position] = item
            notifyItemChanged(position)
        }
    }

    open fun indexOfItem(item: B): Int {
        return mDataList.indexOf(item)
    }

    open fun removeItem(item: B): Int {
        val position = mDataList.indexOf(item)
        if (position >= 0) {
            removeItem(position)
        }
        return position
    }

    open fun removeItem(position: Int) {
        if (position >= 0) {
            mDataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    open fun clear() {
        mDataList.clear()
        notifyDataSetChanged()
    }
}