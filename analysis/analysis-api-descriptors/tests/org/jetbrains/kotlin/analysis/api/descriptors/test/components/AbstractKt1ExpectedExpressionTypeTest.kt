/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.descriptors.test.components

import org.jetbrains.kotlin.analysis.api.descriptors.test.Kt1FrontendApiTestConfiguratorService
import org.jetbrains.kotlin.analysis.api.descriptors.test.analyzeTestFiles
import org.jetbrains.kotlin.analysis.api.impl.barebone.test.FrontendApiTestConfiguratorService
import org.jetbrains.kotlin.analysis.api.impl.base.test.components.AbstractExpectedExpressionTypeTest
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.TestServices

abstract class AbstractKt1ExpectedExpressionTypeTest : AbstractExpectedExpressionTypeTest() {
    override val testPrefix: String?
        get() = "descriptors"

    override val configurator: FrontendApiTestConfiguratorService
        get() = Kt1FrontendApiTestConfiguratorService

    override fun doTestByFileStructure(ktFile: KtFile, module: TestModule, testServices: TestServices) {
        analyzeTestFiles(listOf(ktFile), module, testServices)
        super.doTestByFileStructure(ktFile, module, testServices)
    }
}