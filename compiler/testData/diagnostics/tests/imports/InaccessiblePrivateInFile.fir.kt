// MODULE: m1
// FILE: f11.kt
package api

interface ApplicabilityResult

// FILE: f12.kt
package api

interface ArgumentMapping {
    fun highlightingApplicabilities(): ApplicabilityResult
}

// MODULE: m2(m1)
// FILE: f21.kt
package impl

private data class ApplicabilityResult(val applicable: Boolean)

// FILE: f22.kt
package impl

import api.*

class NullArgumentMapping : ArgumentMapping {
    // Return type should be api.ApplicabilityResult, in fact it's impl.ApplicabilityResult
    override fun highlightingApplicabilities(): ApplicabilityResult = <!INVISIBLE_REFERENCE, RETURN_TYPE_MISMATCH!>object : ApplicabilityResult {
    }<!>
}
