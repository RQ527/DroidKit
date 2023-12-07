package com.rsix.complier

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class HolderFactoryClass(
    private val packageName: String,
    private val className: String,
) {

    private val mSupportHolders = mutableListOf<AdapterHolderInfo>()

    fun addSupportHolder(info: AdapterHolderInfo) {
        mSupportHolders.add(info)
    }

    fun createFactoryClass(codeGenerator: CodeGenerator) {
        val factoryClass = ClassName(packageName, "${className}Factory")
        val file = FileSpec.builder(factoryClass)
        val classBuilder = TypeSpec.classBuilder(factoryClass)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            )
            .superclass(HOLDER_FACTORY_CLASS)
        val companionObject = generateInstanceFun(factoryClass)
        val createMethodBuilder = generateCreateFun()
        classBuilder.addType(companionObject)
            .addFunction(createMethodBuilder.build())
        file.addType(classBuilder.build())
        file.build().writeTo(codeGenerator, Dependencies(true))
    }

    private fun generateInstanceFun(factoryClass: ClassName) = TypeSpec.companionObjectBuilder()
        .addProperty(
            PropertySpec.builder("instance", factoryClass)
                .delegate("lazy(LazyThreadSafetyMode.SYNCHRONIZED) { %T() }", factoryClass)
                .build()
        )
        .build()

    private fun generateCreateFun(): FunSpec.Builder {
        val createMethod = FunSpec.builder("create")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("parent", VIEW_GROUP_CLASS)
            .addParameter("viewType", ClassName("kotlin", "Int"))
            .returns(BASE_HOLDER_CLASS)
        mSupportHolders.forEachIndexed { index, info ->
            createMethod.apply {
                if (index == 0) beginControlFlow(
                    "if(viewType == getIntViewType(%S))",
                    info.viewType
                )
                else beginControlFlow(
                    "else if(viewType == getIntViewType(%S))",
                    info.viewType
                )
                if (info.needComposeView)
                    addStatement(
                        "return %T(%T(parent.context))",
                        ClassName.bestGuess(info.holderQualifiedName),
                        COMPOSE_VIEW_CLASS
                    )
                else info.layoutProvider?.let {
                    addStatement(
                        "return %T(%T().getLayoutView(parent))",
                        ClassName.bestGuess(info.holderQualifiedName),
                        ClassName.bestGuess(
                            (it.declaration as KSClassDeclaration).toClassName().toString()
                        )
                    )
                } ?: throw AdapterHolderException("holder need itemView but layoutProvider is null")
                endControlFlow()
            }
        }
        createMethod.addStatement(
            "throw %T(\"can't find viewType: \${getStrViewType(viewType)}\")",
            RUNTIME_EXCEPTION_CLASS
        )
        return createMethod
    }
}