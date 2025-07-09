package com.names.inspector.extensions

import com.names.inspector.checker.NamesChecker
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.SessionConfiguration
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class NamesValidationExtensionRegistrar(private val configuration: CompilerConfiguration) : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::NamesValidationExtension
    }

    @OptIn(SessionConfiguration::class)
    class NamesValidationExtension(session: FirSession) : FirAdditionalCheckersExtension(session) {
        override val expressionCheckers =
            object : ExpressionCheckers() {
                override val functionCallCheckers = setOf(NamesChecker())
            }
    }
}
