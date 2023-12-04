package com.rsix.annotation

import android.view.View
import android.view.ViewGroup

/**
 * ksp会new出一个对象将view传给holder，请保证实现类有一个无参构造函数！
 */
interface LayoutProvider {
    fun getLayoutView(parent: ViewGroup): View
}
