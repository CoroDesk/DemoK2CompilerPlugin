package com.names.inspector.checker

import com.names.inspector.utils.isDocDeclaration
import com.names.inspector.validators.NamesValidator
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirFunctionCallChecker
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall


class NamesChecker() : FirFunctionCallChecker(MppCheckerKind.Common) {

    override fun check(
        expression: FirFunctionCall,
        context: CheckerContext,
        reporter: DiagnosticReporter,
    ) {
        if (canHandleFunctionCall(expression)) {
            NamesValidator( context, reporter).validate(expression,)
        }
    }

    private fun canHandleFunctionCall(functionCall: FirFunctionCall): Boolean {
        return isDocDeclaration(functionCall)
    }
}
