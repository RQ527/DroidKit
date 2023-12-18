package com.rsix.complier

import com.google.auto.service.AutoService
import com.google.devtools.ksp.KSTypeNotPresentException
import com.google.devtools.ksp.KSTypesNotPresentException
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.rsix.annotation.AdapterHolder
import com.squareup.kotlinpoet.ksp.toClassName

class HolderProcessor(private val codeGenerator: CodeGenerator, val logger: KSPLogger) :
    SymbolProcessor {
    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(AdapterHolder::class.java.name)
        val map = mutableMapOf<String, HolderFactoryClass>()
        val ret = symbols.filter { it is KSClassDeclaration }
        ret.forEach { ksAnnotated ->
            ksAnnotated as KSClassDeclaration
            checkHolder(ksAnnotated)
        }
        ret.forEach { ksAnnotated ->
            ksAnnotated as KSClassDeclaration
            val test = ksAnnotated.getAnnotationsByType(AdapterHolder::class)//事实上只有一个
            test.forEach {
                try {
                    it.adapters
                } catch (e: KSTypesNotPresentException) {
                    e.ksTypes.forEach { ksType ->
                        val packageName = ksType.declaration.packageName.asString()
                        val className = ksType.declaration.simpleName.asString()
                        val factory = map.getOrPut("$packageName.$className") {
                            HolderFactoryClass(
                                packageName,
                                className
                            )
                        }
                        var layoutProvider: KSType? = null
                        try {
                            it.layoutProvider
                        } catch (e: KSTypeNotPresentException) {
                            layoutProvider = e.ksType
                        }
                        checkLayoutProvider(layoutProvider)
                        factory.addSupportHolder(
                            AdapterHolderInfo(
                                it.viewTypes,
                                layoutProvider,
                                ksAnnotated.toClassName().toString(),
                                isNeedComposeView(ksAnnotated)
                            )
                        )
                    }
                }
            }
        }
        map.forEach {
            it.value.createFactoryClass(codeGenerator)
        }
        return symbols.filter { it !is KSClassDeclaration }.toList()
    }

    private fun checkHolder(ksClass: KSClassDeclaration){
        ksClass.primaryConstructor?.let { if (it.parameters.size > 1) throw AdapterHolderException("adapterHolder can only has") }
        ksClass.getAllSuperTypes().forEach {
            if (it.toClassName().toString()=="com.rsix.library.BaseViewTypeHolder") return
        }
        throw AdapterHolderException("annotated holder didn't extend com.rsix.library.BaseViewTypeHolder")
    }

    private fun checkLayoutProvider(layoutProvider: KSType?){
        layoutProvider?.let {
            val hasTempConstructor = (it.declaration as? KSClassDeclaration)?.getConstructors()?.any { it.parameters.isEmpty() } ?: false
            if (!hasTempConstructor) throw AdapterHolderException("make sure your layoutProvider has no-argument constructor")
        }
    }

    private fun isNeedComposeView(ksAnnotated: KSClassDeclaration): Boolean {
        ksAnnotated.getConstructors().forEach {
            if (it.parameters.size==1){
                val name = it.parameters[0].type.toString()
                if (name == "ComposeView") return true
                else if (name == "View") return false
            }
        }
        throw AdapterHolderException("make sure you holder has one constructor whose parameter type is ComposeView or View")
    }
}

@AutoService(SymbolProcessorProvider::class)
class TestProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return HolderProcessor(environment.codeGenerator, environment.logger)
    }

}