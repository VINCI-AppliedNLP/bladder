import gov.va.vinci.leo.bladder.types.*

import java.util.regex.Pattern

/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "DiscoveryRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = Stage.class.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [Pattern.DOTALL] as Integer[]
    }

    /* An arbitrary name for this set of patterns/config. */
    "stage_T4" {
        expressions = [
                '\\[ *x *\\] +T4 +Invs prostate/uterus/vagina/pelvic or abdom wall',
                '\\[ *x *\\] +T4a +Tumor invs prostate, uterus, vagina ',
                '\\[ *x *\\] +T4b +Tumor invs pelvic wall or abdominal wall',
                '(stage|staging)( * \\((ptnm|ajcc)\\) *)?:? * p?t4(a|b)?\\b',
                '\\bpt4',
                'T4.?NXMX'
        ]
        concept_feature_value = "t4"
    }

    "stage_T3" {
        expressions = [
                '\\[ *x *\\] +T3 +Tumor invs deep muscle or perivesical fat',
                '\\[ *x *\\] +T3a +Microscopically',
                '\\[ *x *\\] +T3b +Macroscopically \\(extravesical mass\\)',
                '(stage|staging)( * \\((ptnm|ajcc)\\) *)?:? *p?t3(a|b)?\\b',
                '\\bpt3',
                'T3.?NXMX',
                't3a',
                'pt3(N0Mx)?'

        ]
        concept_feature_value = "t3"
    }

    "stage_T2" {
        expressions = [
                '\\[ *x *\\] +T2 +Tumor invs superficial muscle (inner half)',
                '\\[ *x *\\] +T2a +Invs superficial (inner half) muscle',
                '\\[ *x *\\] +T2b +Invs deep muscle (outer half)',
                '(stage|staging)( * \\((ptnm|ajcc)\\) *)?:? *p?t2(a|b)?\\b',
                '\\bpt2'
        ]
        concept_feature_value = "t2"
    }

    "stage_T1" {
        expressions = [
                '\\[ *x *\\] +T1 +Tumor invs subepithelial connective tissue',
                '(stage|staging)( * \\((ptnm|ajcc)\\) *)?:? *p?t1(a|b)?\\b',
                '\\bpt1',
                't1\\)',
                'superficially\\s*inva\\w+\\s*',
                'infiltrating\\s*lamina'

        ]
        concept_feature_value = "t1"
    }

    "stage_Ta" {
        expressions = [
                '\\[ *x *\\] +Ta +Non invasive papillary carcinoma',
                '(stage|staging)( * \\((ptnm|ajcc)\\) *)?:? *(p?ta\\b|\\b.?t1/.?tis\\b)',
                '\\bpta\\b',
                '\\(ta\\)'


        ]
        concept_feature_value = "ta"
    }

    "stage_Tis" {
        expressions = [
                '\\[ *x *\\] +Tis +Carcinoma in situ \\(flat tumor\\)',
                '(stage|staging)( * \\((ptnm|ajcc)\\) *)?:? *p?tis\\b' ,
                '\\bptis\\b',
                '\\btis\\b,'

        ]
        concept_feature_value = "tis"
    }

    "stage_none" {
        expressions = [
                '\\[ *x *\\] +TX +Primary tumor cannot be assessed'

        ]
        concept_feature_value = "not stated"
    }

}