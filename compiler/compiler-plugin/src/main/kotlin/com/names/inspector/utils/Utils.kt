package com.names.inspector.utils

import com.names.inspector.Dog
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference
import org.jetbrains.kotlin.fir.types.classId
import org.jetbrains.kotlin.fir.types.resolvedType

private val DOG_CLASS_NAME by lazy { Dog::class.qualifiedName }

 fun isDocDeclaration(functionCall: FirFunctionCall): Boolean {
    return functionCall.calleeReference is FirResolvedNamedReference &&
            functionCall.resolvedType.classId?.asFqNameString() == DOG_CLASS_NAME
}