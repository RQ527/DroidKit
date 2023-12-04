package com.rsix.complier

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR

val HOLDER_FACTORY_CLASS = ClassName("com.rsix.droidkit", "HolderFactory")
val COMPOSE_VIEW_CLASS = ClassName.bestGuess("androidx.compose.ui.platform.ComposeView")
val RUNTIME_EXCEPTION_CLASS = ClassName("kotlin","RuntimeException")
val VIEW_GROUP_CLASS = ClassName("android.view", "ViewGroup")
val BASE_HOLDER_CLASS = ClassName("com.rsix.droidkit", "BaseViewTypeHolder").parameterizedBy(STAR)

class AdapterHolderException(message:String):Exception(message)