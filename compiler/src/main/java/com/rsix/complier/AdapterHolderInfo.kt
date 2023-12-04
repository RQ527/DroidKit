package com.rsix.complier

import com.google.devtools.ksp.symbol.KSType

data class AdapterHolderInfo(val viewType:String,val layoutProvider:KSType?,val holderQualifiedName:String,val needComposeView:Boolean)