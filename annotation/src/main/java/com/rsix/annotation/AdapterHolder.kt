package com.rsix.annotation

import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class AdapterHolder (
    /**
     * holder在adapter里所属类型，当与data类匹配不到时会自动过滤不显示
     */
    val viewType:String = "",
    /**
     * 所适用的adapter
     */
    val adapters:Array<KClass<out RecyclerView.Adapter<out RecyclerView.ViewHolder>>> = [],
    /**
     * 布局文件提供器，使用方式见[LayoutProvider]
     */
    val layoutProvider:KClass<out LayoutProvider> = LayoutProvider::class,
)