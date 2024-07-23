import gov.va.vinci.leo.bladder.types.Grade

import java.util.regex.Pattern

/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "DiscoveryRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = Grade.class.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [ Pattern.DOTALL ] as Integer[]
    }

    /* An arbitrary name for this set of patterns/config. */
    "grade_low" {
        expressions = [
                'low.{1,5}grade',
                'low\\s*grade',
                'grade.{1,25}low',
                'well.{1,25}differentiated',
                '\\[x\\] +G1 +Well +differentiated'
        ]
        concept_feature_value = "g1"
    }

    "grade_mod" {
        expressions = [
                'intermediate.{1,5}grade',
                '(intermediate|moderate).{1,25}differentiated',
                'moderately(\\s*|-)differentiated'
        ]
        concept_feature_value = "g2"
    }

    "grade_high" {
        expressions = [
                'high.{1,5}grade',
                'grade.{1,25}high',
                'poorly.{1,25}differentiated'
        ]
        concept_feature_value = "g3"
    }

    "grade_undiff" {
        expressions = [
                'undifferentiated'
        ]
        concept_feature_value = "g4"
    }

    "grade_num" {
        expressions = [
                'grade.{1,15}\\b(IV|[I]{1,3}|1|2|3|4)\\b'
        ]
        concept_feature_value = "num"
    }
}