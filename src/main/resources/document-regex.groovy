package resources

import gov.va.vinci.leo.bladder.types.DocumentType

import java.util.regex.Pattern
/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "DocumentTypeRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = DocumentType.class.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [Pattern.DOTALL] as Integer[]
    }

    /* An arbitrary name for this set of patterns/config. */
    "Relevant" {
        expressions = [

                'specimen',
                'biopsy',
                'segment',
                'anatomic\\s*site'
                //'diagnosis'

--
        ]
        concept_feature_value = "DocTypeAnchor"


    }
}