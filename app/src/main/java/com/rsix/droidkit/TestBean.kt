package com.rsix.droidkit

data class TestBean(
    override val viewType: String,
    val content:String, override val id: Int
): com.rsix.library.BaseViewTypeItem()

data class TestBean1(
    override val viewType: String,
    val btnContent:String, override val id: Int
): com.rsix.library.BaseViewTypeItem()

data class TestBean2(
    override val viewType: String,
    val title:String, override val id: Int,val subtitle:String
): com.rsix.library.BaseViewTypeItem()

data class TestBean3(
    override val viewType: String,
    val content:String, override val id: Int,
    val src:Int
): com.rsix.library.BaseViewTypeItem()

data class TestBean4(
    override val viewType: String,
    override val id: Int,
    val title:String,
    val drawableSrc:Int
): com.rsix.library.BaseViewTypeItem()