package resources

import gov.va.vinci.leo.bladder.types.Exclude

import java.util.regex.Pattern
/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "ExcludeTypeRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = Exclude.class.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [ Pattern.DOTALL ] as Integer[]
    }



    /* An arbitrary name for this set of patterns/config. */
    "exclude" {
        expressions = [
               // '\\n *Invasion: *\\r?\\n',
                //^^This could be big
                'lymphovascular\\s*(space\\s*)?invasion',
                'lymph(-|\\s*)vascular\\s*invasion',
                'history:?\\s*(\\w+\\s*){0,10}\\s*inva(s|d)\\w+',
                'Preoperative\\s*diagnosis:?\\s*(\\w+\\s*){0,10}\\s*inva(s|d)\\w+',
                'no\\s*(definite\\s*)?stromal\\s*invasion',
                'perinerual\\s*invasion',
                'lymphatic\\s*invasion',
                'perineural\\s*space\\s*invasion',
                'perinerual\\s*invasion',
                'perineural\\s*invasion',
                'invasion\\s*(of\\s*muscularis\\s*propria\\s*)?was\\s*documented\\s*on\\s*prior',
                'evaluation\\s*of\\s*possible\\s*muscularis\\s*propria\\s*invasion',
                'Adequacy\\s*of\\s*material\\s*for\\s*determining\\s*muscularis\\s*propria\\s*invasion:',
                'Preoperative\\d*diagnosis:\\s*Bladder\\s*Cancer\\s*w/Muscle\\s*invasion',
                'pending\\s*to\\s*evaluate\\s*muscular\\s*invasion',
                'not\\s*possible\\s*to\\s*determine\\s*if\\s*the\\s*',
                'prior\\s*biopsy\\s*of\\s*the\\s*bladder',
                'right\\s*kidney,\\s*right\\s*ureter,\\s*bladder\\s*cuff',
                'history\\s*of\\s*p',
                'hx\\s*of\\s*tcc',
                'angiolymphatic\\s*invasion:',
                'no\\s*suggestion\\s*of\\s*invasion',
                'negative\\s*for\\s*lamina\\*propria\\s*invasion',
                'inflammatory\\s*infiltra\\w+',
                'invasion\\s*cannot\\*be\\s*assesed',
                'negative\\s*for\\s*lamina\\s*propria\\s*invasion',
                'invasion:\\s*anterior\\s*wall\\s*and\\s*lateral\\s*wall.\\s*lamina\\s*propria:\\s*negative',
                'invasion:\\s*lamina\\s*propria:\\s*negative',
                '\\d\\d\\s*showed\\s*high\\sgrade\\s*transitional\\s*cell\\s*with\\s*invasion',
               'history\\s*of\\s*pt2',
                'prostatic\\s*biopsy:?\\s*fragments\\s*of\\s*smooth',
                'preoperative\\s*diagnosis:(\\w+\\s*){0,10}\\w+(inva\\w+|musc\\w+)',
                'postoperative\\s*diagnosis:(\\w+\\s*){0,10}\\w+(inva\\w+|musc\\w+)',
                'gleason\'?s?\\s*grade',
                'recently\\s*diagnosed\\s*with\\s*inva\\w+',
                'no\\s*significant\\s*submucosa\\s*to\\s*assess\\s*invasion',
                'no\\s*diagnostic\\s*evidence\\s*of\\s*lamina\\s*propria\\s*invasion',
                'invasion\\s*cannot\\s*be\\s*assesed',
                'identify\\s*potential\\s*inva\\w+',
                'infiltrated\\s*by\\s*(acute\\s*)?inflammatory\\s*cells',
                'material\\s*for\\s*determining\\s*musc\\w+\\s*(propria\\s*)?invasion',
                'evaluation\\s*for\\s*carcinoma,\\s*muscle\\s*invasion',
               'prior\\s*biopsy\\s*material(\\w+\\s*){0,20}\\s*invasive',
                '\\)\\s*and\\s*confirm\\s*the\\s*diagnoses\\s*of\\s*invas\\w+'




        ]
        concept_feature_value = "exclude"
    }

}
