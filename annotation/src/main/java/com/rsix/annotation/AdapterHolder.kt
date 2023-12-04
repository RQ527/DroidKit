package com.rsix.annotation

import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class AdapterHolder (
    val viewType:String = "",
    val adapters:Array<KClass<out RecyclerView.Adapter<out RecyclerView.ViewHolder>>> = [],
    val layoutProvider:KClass<out LayoutProvider> = LayoutProvider::class,
)