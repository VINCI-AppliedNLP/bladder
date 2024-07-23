import gov.va.vinci.leo.bladder.types.DoiBase
import gov.va.vinci.leo.bladder.types.DoiType

import java.util.regex.Pattern

/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "DiscoveryRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = DoiType.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [Pattern.DOTALL] as Integer[]
    }

    "base" {
        expressions = [
                'involv(e(s)?|ment|ing)',
                'invasi(ve|on)',
                'invade(s)?',
                'infiltrate(d|s)?',
                'exten(d(s|ed)?|sion|ing)',
                'tumor',
                'TCC'
        ]
        concept_feature_value = "base"
        outputType = DoiBase.canonicalName
    }

    "other_organ" {
        expressions = [
                'stroma(l)?',
                'prostatic.{1,5}(urethra(l)?|stroma|parenchyma)',
                'perivesic.{1,10}fat',
                'prostate',
                'uterus',
                'vagina',
                'pelvic',
                'abdominal',
                'rectum'
        ]
        concept_feature_value = "other_organ"
    }

    "perivesical" {
        expressions = [
                'peri(-)?vesic.{1,25}(fat|tissue)',
                'fibrofat(ty)?',
                'focally.{1,25}(tissue|fat)',
                'peripelvic(tissue|fat)'
        ]
        concept_feature_value = "perivesical"
    }

    "muscle" {
        expressions = [
                'muscularis(.{1,10}propria)?',
                'muscular.{1,10}tissue',
                'muscle',
                'detrusor',
                '\\bmyo'
        ]
        concept_feature_value = "muscle"
    }

    "superficial" {
        expressions = [
                'superficial',
                'lamina.{1,10}propria',
                'submucosa(l)?',
                'subepithelial(.{1,20}tissue)?',
                'focal',
            'micro'

        ]
        concept_feature_value = "superficial"
    }
}