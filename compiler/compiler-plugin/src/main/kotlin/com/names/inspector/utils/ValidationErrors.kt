@file:Suppress("PropertyName")

package com.names.inspector.utils

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
import org.jetbrains.kotlin.diagnostics.rendering.Renderer
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory
import org.jetbrains.kotlin.diagnostics.warning1

object ValidationErrors : BaseDiagnosticRendererFactory() {
    init {
        RootDiagnosticRendererFactory.registerFactory(ValidationErrors)
    }

   val WARNING_USE_A_STRONGER_DOG_NAME by warning1<PsiElement, String>()

    override val MAP: KtDiagnosticFactoryToRendererMap =
        rendererMap { map ->
            map.put(
                factory = WARNING_USE_A_STRONGER_DOG_NAME,
                message = "''{0}''",
                rendererA = Renderer { t: String -> t },
            )
        }

    private fun rendererMap(block: (KtDiagnosticFactoryToRendererMap) -> Unit): KtDiagnosticFactoryToRendererMap =
        KtDiagnosticFactoryToRendererMap("NameValidationErrors").also(block)
}
