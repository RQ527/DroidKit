package com.rsix.annotation

import android.view.View
import android.view.ViewGroup

/**
 * ksp会new出一个对象将view传给holder，请保证实现类有一个无参构造函数！
 *
 * 使用方式：
 *
 * ```kotlin
 * @AdapterHolder(
 *     adapters = [TestAdapter::class],
 *     viewType = "text_type",
 *     layoutProvider = TestHolder.HolderLayoutProvider::class
 * )
 * class TestHolder(itemView: View) : BaseViewTypeHolder<TestBean>(itemView) {
 *
 *     private val mTextView: TextView = itemView.findViewById(R.id.rv_item_tv)
 *
 *     class HolderLayoutProvider() : LayoutProvider {
 *         override fun getLayoutView(parent: ViewGroup): View =
 *             LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
 *
 *     }
 *
 *     override fun onBind(data: TestBean) {
 *         mTextView.text = data.content
 *     }
 *
 * }
 * ```
 * HolderLayoutProvider不是必须写到Holder内部。
 */
interface LayoutProvider {
    fun getLayoutView(parent: ViewGroup): View
}
