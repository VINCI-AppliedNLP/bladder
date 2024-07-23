package resources
import gov.va.vinci.leo.bladder.types.InvasionType

import java.util.regex.Pattern
/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "InvasionTypeRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = InvasionType.class.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [ Pattern.DOTALL ] as Integer[]
    }

    /**
     * None
     NULL
     Suspected
     Not Stated
     Invasive
     */

    /* An arbitrary name for this set of patterns/config. */
    "invasion_none" {
        expressions = [
            'NO\\s*INVAS(ION|ive)',
            'NOT\\s*INVAS(ION|ive)',
            'NO\\s*Infiltrat(ion|ive|ed)',
            'NOT\\s*Infiltrat(ion|ive|ed)',
            '(No|negative|ruled\\s*out|without|not|non)\\s+(\\w+\\s*):?{0,6}\\s*invas(ion|ive)',
                'negative\\s*for\\s*invasion',
                'without\\s*invasion',
                'ruled\\s*out\\s*invasion',
            '(No|negative|ruled\\s*out|without|not|non)\\s+(\\w+\\s*):?{0,6}\\s*infiltra(tion|tive|ted)',
            '(No|negative|ruled\\s*out|without|not|non)\\s+(\\w+\\s*):?{0,6}\\s*invas(ion|ive)\\s*or\\s*infiltra(tion|tive)',
            '(without|no)\\s*evidence\\s*of\\s*invas(ion|ive)',
            '(without|no)\\s*evidence\\s*of\\s*infiltrat(ion|ive|ed)',
            'NON(-|\\s*)?INVASIV\\w*',
               // 'no\\s*muscularis\\s*(propria\\s*)?(invasion)?',
                'non invasive',
            'muscularis\\s+(\\w+\\s*){0,4}\\s*negative',
            'uninvolved\\s*muscularis',
            'invas\\w+\\s*(\\w+\\s*){0,4}\\s*(negative|\\bnot|none|no|absent)\\b',
            'Laminal?\\s*propria(:|\\s*is)\\s*(Negative|no|\\bnot|absent)',
            'invasion(\\s*status)?:\\s*(Negative|no|not|absent)',
            'invasion\\s*not\\s*present',
            '(without|no)\\s*(definite\\s*)?laminal?\\s*propria\\s*(invasion)?',
                'no\\s*definite\\s*lamina\\s*propria\\s*invasion',
            '(without|no)\\s*invasion\\s*(into)?\\s*laminal?\\s*propria',
            'negative\\s*for\\s*laminal?\\s*propria',
                '(without|no)\\s*muscularis?\\s*propria\\s*invasion',
                'no\\s*lamina\\s*propria\\s*or\\s*muscularis\\s*propria\\s*invasion',
                'no\\s*stromal\\s*or\\s*muscle\\s*invas\\w+',
                'without\\s*invas(ion|ive)',
                '(without|no)\\s*evidence\\s*of\\s*musc\\w+\\s*invas\\w+',
                'surface\\s*involvement',
                'no\\s*evidence\\s*of\\s(either\\s*)?lamina\\s*propria\\s*or\\s*muscularis\\s*propria\\s*invas\\w+',
                'no\\s*laminal?\\s*propria\\s*or\\s*muscularis\\s*propria\\s*invas\\w+',
                'not\\s*show\\s*definite(\\w+\\s*){0,5}\\s*invas\\w+',
                'lamina\\s*propria\\s*and\\s*detrusor\\s*muscle\\s*are\\s*negative\\s*for\\s*tumor',\
                'no\\s*obvious\\s*submucosal\\s*invasion',
                'no\\s*definite\\s*stromal\\s*invasion',
                'no\\s*apparent\\s*stromal\\s*invasion',
                'no\\s*apparent\\s*submucosal\\s*invasion',
            'negative\\s*for\\s*submucosal\\s*invasion',
            'no\\s*stromal\\s*invasion',
            'no\\s*submucosal\\s*invasion',
                'no\\s*evidence\\s*of\\s*mucosal\\s*or\\s*muscular\\s*invasion',
                'muscle\\s*present\\s*and\\s*free\\s*of\\s*(carcinoma|tumor|invasion)',
                'muscle\\s*identified,?\\s*negative\\s*for\\s*(carcinoma|tumor|invasion)',
                'no\\s*(deep\\s*)?muscle\\s*invasion',
            'invasion:\\s*indeterminant',
            'rule\\s*out\\s*superficial\\s*invasion',
            'clinically\\s*no\\s*muscle\\s*invading',
                'no\\s*evidence\\s*of\\s*high-grade\\s*or\\s*invasive',
                'no\\s*evidence\\s*of\\s*smooth\\s*muscle\\s*invasion',
                'no\\s*evidence\\s*of\\s*tumor\\s*infiltration',
                'invasion\\s*cannot\\s*be\\s*assesed',
                'muscular\\s*invasion\\s*cannot\\s*be\\s*assessed',
                'no\\s*diagnostic\\s*evidence\\s*of\\s*invasion',
                'no\\s*definitive\\s*evidence\\s*of\\s*muscularis\\s*propria\\s*invasion',
            'no\\s*stromal\\s*or\\s*muscular\\s*invasion',
            //'no\\s*musc\\w+\\s*(invasion|present)',
            'no\\s*invasion\\s*seen\\s*of\\s*musc\\w+',
            'without\\s*invasion\\s*of\\s*musc\\w+',
            "no\\s*muscle\\s*invasion\\s*is\\s*identified",
            'without\\s*invasion\\s*(through|into)\\s*(lamina|muscularis|muscle)',
            'no\\s*evidence\\s*of\\s*smooth\\s*muscle\\s*invasion',
                'no\\s*definit\\w+\\s*evidence\\s*of\\s*lamina\\s*propria\\s*inva\\w+',
            'devoid\\s*of\\s*(muscularis|muscle)(?!invasion)',
                'no\\s*definite\\s*stromal\\s*inva\\w+',
                'does\\s*not\\s*invade\\s*musc\\w+',
                'no\\s*(diagnostic\\s*)?stromal\\s*invasion',
                'indefinite\\s*for\\s*lamina\\s*propria\\s*invasion',
                'no\\s*lamina\\s*propria\\s*or\\s*vascular\\s*invasion',
                'no\\s*diagnostic\\s*changes\\s*of\\s*invasion',
                'no\\s*definite\\s*submucosal\\s*invasion',
                'cells\\s*do\\s*not\\s*invade',
                'no\\s*lamina\\s*propria\\s*or\\s*deep\\s*muscle\\s*invasion',
                'no\\s*tumor\\s*is\\s*(noted|present)\\s*in\\s*the\\s*(lamina|muscle|subepithelial|connective)',
                'tumor\\s*is\\s*not\\s*(noted|present)\\s*in\\s*the\\s*(lamina|muscle|subepithelial|connective)'
        ]
        concept_feature_value = "none"
        outputType = "gov.va.vinci.leo.bladder.types.InvasionTypeNone"
    }

    "invasion_suspected" {
        expressions = [
            'suspicious for\\s+(\\w+\\s*){0,4}\\s*(infiltration|invasion)',
            'suggest\\w*\\s+(\\w+\\s*){0,4}\\s*(infiltration|invasion)',
            '(infiltration|invasion)\\s+(\\w+\\s*){0,4}\\s*can\\s*not\\s*be\\s*(entirely\\s*)?ruled out',
            'can\\s*not\\s*exclude\\s(\\w+\\s*){0,4}\\s*(infiltration|invasion)',
                'cannot\\s*exclude\\s*superficial\\s*infiltration'
        ]
        concept_feature_value = "suspected"
        outputType = "gov.va.vinci.leo.bladder.types.InvasionTypeSuspected"
    }

    "invasion_invasive" {
        expressions = [
            'INFILTRATING THE LAMINA PROPRIA',
                'invasive',
                'invasion',
                'Tumor\\s*invades',
                'tumor\\s*penetrates\\s(lamina|muscular)',
                'infiltr\\w+\\s*',
                'invades?\\b',
                'invading\\s*into',
                'superficial\\s*inv\\w+',
                'invading',
                'involving\\s*(the\\s*)?muscularis',
                'involving\\s*(the\\s*)?lamina',
                'involving\\s*detrusar',
                'extending\\s*into',
                'tumor\\s*is\\s*(noted|present)\\s*in\\s*the\\s*(lamina|muscle|subepithelial|connective)'

        ]
        concept_feature_value = "invasive"
        outputType = "gov.va.vinci.leo.bladder.types.InvasionTypeInvasive"
    }


}
