/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.descriptors.symbols.psiBased

import org.jetbrains.kotlin.analysis.api.descriptors.Kt1AnalysisSession
import org.jetbrains.kotlin.analysis.api.descriptors.symbols.descriptorBased.base.toKtTypeAndAnnotations
import org.jetbrains.kotlin.analysis.api.descriptors.symbols.pointers.Kt1NeverRevivingSymbolPointer
import org.jetbrains.kotlin.analysis.api.descriptors.symbols.psiBased.base.Kt1PsiSymbol
import org.jetbrains.kotlin.analysis.api.descriptors.symbols.psiBased.base.createErrorTypeAndAnnotations
import org.jetbrains.kotlin.analysis.api.descriptors.utils.cached
import org.jetbrains.kotlin.analysis.api.symbols.KtLocalVariableSymbol
import org.jetbrains.kotlin.analysis.api.symbols.markers.KtSymbolKind
import org.jetbrains.kotlin.analysis.api.symbols.markers.KtTypeAndAnnotations
import org.jetbrains.kotlin.analysis.api.symbols.pointers.KtPsiBasedSymbolPointer
import org.jetbrains.kotlin.analysis.api.symbols.pointers.KtSymbolPointer
import org.jetbrains.kotlin.analysis.api.withValidityAssertion
import org.jetbrains.kotlin.descriptors.VariableDescriptor
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtVariableDeclaration
import org.jetbrains.kotlin.resolve.BindingContext

internal class Kt1PsiLocalVariableSymbol(
    override val psi: KtVariableDeclaration,
    override val analysisSession: Kt1AnalysisSession
) : KtLocalVariableSymbol(), Kt1PsiSymbol<KtVariableDeclaration, VariableDescriptor> {
    override val descriptor: VariableDescriptor? by cached {
        val bindingContext = analysisSession.analyze(psi, Kt1AnalysisSession.AnalysisMode.PARTIAL)
        bindingContext[BindingContext.VARIABLE, psi]
    }

    override val name: Name
        get() = withValidityAssertion { psi.nameAsSafeName }

    override val annotatedType: KtTypeAndAnnotations
        get() = withValidityAssertion { descriptor?.type?.toKtTypeAndAnnotations(analysisSession) ?: createErrorTypeAndAnnotations() }

    override val isVal: Boolean
        get() = withValidityAssertion { !psi.isVar }

    override val symbolKind: KtSymbolKind
        get() = withValidityAssertion { KtSymbolKind.LOCAL }

    override fun createPointer(): KtSymbolPointer<KtLocalVariableSymbol> = withValidityAssertion {
        return KtPsiBasedSymbolPointer.createForSymbolFromSource(this) ?: Kt1NeverRevivingSymbolPointer()
    }
}