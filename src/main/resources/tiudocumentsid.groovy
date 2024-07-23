

import java.util.regex.Pattern

/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "DiscoveryRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = "gov.va.vinci.leo.regex.types.RegularExpressionType"
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [ Pattern.DOTALL ] as Integer[]
    }

    /* An arbitrary name for this set of patterns/config. */
    "tiu" {
        expressions = [
            '(?<=TIU Document SID: )\\d+'
        ]
        concept_feature_value = "TIU"
    }


}