package com.names.inspector.extensions

import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall
import org.jetbrains.kotlin.fir.expressions.FirLiteralExpression
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression
import org.jetbrains.kotlin.fir.expressions.FirStatement
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList
import org.jetbrains.kotlin.fir.references.toResolvedPropertySymbol
import org.jetbrains.kotlin.fir.types.classId
import org.jetbrains.kotlin.fir.types.resolvedType

fun FirFunctionCall?.name() = this?.calleeReference?.name?.asString()

infix fun FirFunctionCall.isCalled(name: String) = name() == name

fun FirStatement.getFunctionCallName() = (this as? FirFunctionCall)?.name()

/**
 * Obtains the classId returned from an expression `Dog::class` -> "com/example/Dog"
 */
fun FirExpression.getClassCallId() = (this as? FirGetClassCall)?.argument?.resolvedType?.classId

/**
 * Obtains the property symbol name returned from an expression `Dog::name` -> "name"
 */
fun FirExpression.getPropertyName() =
    (this as? FirQualifiedAccessExpression)?.calleeReference?.toResolvedPropertySymbol()?.name?.asString()
/**
 * Obtains the property symbol name returned from an expression `Dog::name` -> String
 */
fun FirExpression.getPropertyType() =
    (this as? FirQualifiedAccessExpression)?.calleeReference?.toResolvedPropertySymbol()?.resolvedReturnType?.classId?.asFqNameString()

/**
 * Obtains the property symbol parent ClassId from an expression `Dog::name` -> "com/package/Dog"
 */
fun FirExpression.getPropertyClassId() =
    (this as? FirQualifiedAccessExpression)?.calleeReference?.toResolvedPropertySymbol()?.dispatchReceiverType?.classId

/**
 * Obtains the string literal value `"Bailey"` -> "Bailey"
 */
fun FirExpression?.getLiteralStringValue() = getLiteralParsed<String>()

/**
 * Obtains the number literal value `10` -> 10
 */
fun FirExpression?.getLiteralIntValue() = getLiteralParsed<Number>()

private inline fun <reified T> FirExpression?.getLiteralParsed(): T? {
    return when (this) {
        is FirLiteralExpression -> {
            value as? T
        }

        is FirPropertyAccessExpression -> {
            extractLiteralFromPropertyAccess(this)
        }

        else -> null
    }
}

/**
 * Extracts and parses a value from a FirPropertyAccessExpression with a resolved initializer
 */
private inline fun <reified T> extractLiteralFromPropertyAccess(propertyAccess: FirPropertyAccessExpression): T? {
    return try {
        // Get the resolved property symbol
        val resolvedSymbol = propertyAccess.calleeReference.toResolvedPropertySymbol()

        // Get the resolved initializer if it exists
        var initializer = resolvedSymbol?.resolvedInitializer
        while (initializer is FirPropertyAccessExpression) {
            initializer = initializer.calleeReference.toResolvedPropertySymbol()?.resolvedInitializer
        }

        // Check if the initializer is a FirLiteralExpression
        if (initializer is FirLiteralExpression && initializer.value is T) {
            initializer.value as? T
        } else {
            null
        }
    } catch (e: Exception) {
        // If anything goes wrong with symbol resolution, return null
        null
    }
}


// Get the argument mapped to the config parameter
inline fun <reified T> FirFunctionCall.getArgument(name: String): T? =
    (argumentList as FirResolvedArgumentList).mapping.entries.firstOrNull {
        it.value.name.asString() == name && it.key is T
    }?.key as? T?
