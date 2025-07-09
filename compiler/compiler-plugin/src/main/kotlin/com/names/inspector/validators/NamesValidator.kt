package com.names.inspector.validators


import com.names.inspector.extensions.getArgument
import com.names.inspector.extensions.getLiteralStringValue
import com.names.inspector.utils.ValidationErrors
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall


class NamesValidator(
    val context: CheckerContext,
    val reporter: DiagnosticReporter
) {

    fun validate(
        expression: FirFunctionCall,
    ) {
        val dogName = expression.getArgument<FirExpression>("dogName").getLiteralStringValue().orEmpty()
        if (dogName.endsWith("a", ignoreCase = true)) {
            reporter.reportOn(
                expression.source,
                ValidationErrors.WARNING_USE_A_STRONGER_DOG_NAME,
                "A strong dog name should not finish with 'a', instead of '$dogName', consider names like Hulk or Maximus",
                context,
                SourceElementPositioningStrategies.DEFAULT,
            )
        }
    }
}
