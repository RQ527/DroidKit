package com.rsix.library

abstract class BaseViewTypeItem{
    /**
     * data类与holder匹配的viewType，当匹配不到时会自动过滤
     */
    abstract val viewType:String

    /**
     * item的id，用与diffUtil
     */
    abstract val id:Int
}