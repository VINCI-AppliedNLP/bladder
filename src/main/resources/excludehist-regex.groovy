package resources

import gov.va.vinci.leo.bladder.types.ExcludeHist

import java.util.regex.Pattern
/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "ExcludeHistTypeRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = ExcludeHist.class.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [ Pattern.DOTALL ] as Integer[]
    }



    /* An arbitrary name for this set of patterns/config. */
    "exclude" {
        expressions = [
                'history\\s*of\\s*transitional',
                'h/o\\s*TCC',
                'h/o\\s*transitional',
                'these\\s*results\\s*rule\\s*out\\s*(prostatic\\s*)?adenocarcinoma',
                'prostatic\\s*adenocarcinoma',\
                'prostate\\s*(\\w+\\s*){0,10}\\s*adenocarcinoma',
                'prostectomy:\\s*histologic\\s*type:\\s*adenocarcinoma',
                'Lymph(\\s*|-)nodes?\\s*(\\w+\\s*){0,5}\\s*no\\s*evidence',
                'no\\s*evidence\\s*(\\w+\\s*){0,5}\\s*nodes?',
                'risk\\s*of\\s*developing\\s*adenocarcinoma',
                'prostatectomy:\\s*histologic\\s*type:\\s*adenocarcinoma',
                'history\\s*of\\s*other(\\w+\\s*){0,10}\\w+',
                'preoperative\\s*diagnosis:(\\w+\\s*){0,10}\\w+',
                'postoperative\\s*diagnosis:(\\w+\\s*){0,10}\\w+',
                'gleason\'?s?\\s*grade',
                'no\\s*residual\\s*urothelial',
                'history:?\\s*(\\w+\\s*){0,15}\\w+',
                'no\\sevidence(\\w+\\s*){0,15}(TCC|transitional|carc\\w+|adeno\\w+)'


        ]
        concept_feature_value = "exclude"
        outputType = "gov.va.vinci.leo.bladder.types.ExcludeHist"
    }

}
