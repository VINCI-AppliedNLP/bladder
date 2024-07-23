import gov.va.vinci.leo.bladder.types.Histology

import java.util.regex.Pattern

/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "CisRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = Histology.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [Pattern.DOTALL] as Integer[]
    }

    "CisNo" {
        expressions = [

               "No\\s*carcinoma(\\s|-)in(\\s|-)*situ",
               "No\\s*in(\\s|-)*situ(\\s|-)carcinoma",
               "negative\\s*(for\\s*)?\\s*carcinoma(\\s|-)in(\\s|-)*situ",
               "negative\\s*(for\\s*)?\\s*in(\\s*|-)situ(\\s*|-)carcinoma",
                "Carcinoma\\s*in\\s*situ:?\\s*Absent",
                "lesion\\s*is\\s*in\\s*situ",

        ]
        concept_feature_value = "CisNo"
        outputType =  "gov.va.vinci.leo.bladder.types.CisNo"
    }


    "CisYes" {
        expressions = [


                "carcinoma(\\s*|-)in(\\s|-)*situ",
                "in(\\s*|-)situ(\\s*|-)carcinoma"


        ]
        concept_feature_value = "CisYes"
        outputType = "gov.va.vinci.leo.bladder.types.CisYes"
    }


}
