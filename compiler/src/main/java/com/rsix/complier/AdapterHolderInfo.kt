package com.rsix.complier

import com.google.devtools.ksp.symbol.KSType

class AdapterHolderInfo(val viewTypes:Array<String>,val layoutProvider:KSType?,val holderQualifiedName:String,val needComposeView:Boolean)