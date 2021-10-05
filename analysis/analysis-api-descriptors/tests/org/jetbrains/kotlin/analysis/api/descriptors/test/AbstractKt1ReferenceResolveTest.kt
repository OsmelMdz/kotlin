/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.descriptors.test

import org.jetbrains.kotlin.analysis.api.impl.barebone.test.FrontendApiTestConfiguratorService
import org.jetbrains.kotlin.analysis.api.impl.base.test.AbstractReferenceResolveTest
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.TestServices

abstract class AbstractKt1ReferenceResolveTest : AbstractReferenceResolveTest() {
    override val testPrefix: String?
        get() = "descriptors"

    override val configurator: FrontendApiTestConfiguratorService
        get() = Kt1FrontendApiTestConfiguratorService

    override fun doTestByFileStructure(ktFiles: List<KtFile>, module: TestModule, testServices: TestServices) {
        analyzeTestFiles(ktFiles, module, testServices)
        super.doTestByFileStructure(ktFiles, module, testServices)
    }
}