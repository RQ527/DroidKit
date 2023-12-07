package com.rsix.droidkit

data class TestBean(
    override val viewType: String,
    val content:String, override val id: Int
): com.rsix.library.BaseViewTypeItem()

data class TestBean1(
    override val viewType: String,
    val content:String, override val id: Int
): com.rsix.library.BaseViewTypeItem()

data class TestBean2(
    override val viewType: String,
    val content:String, override val id: Int
): com.rsix.library.BaseViewTypeItem()