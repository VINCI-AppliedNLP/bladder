import gov.va.vinci.leo.bladder.types.Histology

import java.util.regex.Pattern

/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "MuscleRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = Histology.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [Pattern.DOTALL] as Integer[]
    }

    "MuscleYes" {
        expressions = [
                "musc\\w+(propria\\s*)?\\s*invasion\\s*(is\\s*)?identified",
                "muscu\\w+\\s*(propria\\s*)?(is\\s*|are)?(present)",
                "lesion\\s*is\\s*in\\s*situ",
                '(scant\\s*)?stroma\\s*is\\s*present',
                'musc\\w+\\s*(propria\\s*)?inva(s|d)\\w+',
                'musc\\w+\\s*(invasion|present)',
                'musc\\w+\\s*(propria\\s*)?:?\\s*(yes|present|negative|positive)',
                'musc\\w+\\s*(wall\\s*)?present',
                'bladder\\s*wall\\s*muscle\\s*present',
                'inva\\w+\\s*(through\\s*)?(the\\s*)?musc\\w+',
                'musc\\w+\\s*(propria\\s*)?(fibers|fragments)',
                'with\\s*extensive\\s*(muscular|muscular(is)?\\s*(propria)?|muscle)\\s*invasion',
                'musc\\w+(propria\\s*)?\\s*(tissue\\s*)?present',
                //'stains\\s*are\\s*pending\\s*to\\s*evaluate\\s*muscular\\s*invasion',
                'musc\\w+\\s*(propria\\s*)?(tissue|layer|wall)?\\s*(is\\s*|are\\s*)?(represented|involved|present|identified|seen|noted)',
                'invading\\s*musc\\w+',
                'muscle\\s*bundles\\s(are\\s*|is\\s*)?(also\\s*)?(included|present|identified)',
                'musc\\w+\\s*(propria\\s*)?is\\s*focally\\s*identified',
                '(infiltrat\\w+|invad\\w+)\\s*(through\\s*)?(the\\s*)?(deep)?\\s*muscle',
                'infiltrating\\s*musc\\w+',
                'invasion\\s*(to|into)\\s*lamina\\s*propria\\s*and\\s*musc\\w+',
                'musc\\w+\\s*(inva\\w+|infiltration)',
                'musc\\w+\\s*(propria\\s*)?(is\\s*|are\\s*)?(not\\s*)?(focally\\s*)?invaded',
                'sampling\\s*includes\\s*(urothelium|lamina\\s*propria|musc\\w+|submucosa\\s*|and\\s*|mucosa\\s*)?,?(urothelium|lamina\\s*propria|musc\\w+|submucosa\\s*|and\\s*|mucosa\\s*)?,?(urothelium|lamina\\s*propria|musc\\w+|submucosa\\s*|and\\s*|mucosa\\s*),?',
                'sampling\\s*includes\\s*(mucosa,?\\s*)?(submucosa,?\\s*)?(and,?\\s*)musc\\w+',
                'inva\\w+\\s*(into\\s*)?(lamina\\s*propria,?\\s*)?(focally\\s*)?musc\\w+',
                'inva\\w+\\s*(into\\s*|in\\s*the\\s*)?(lamina\\s*propria,?\\s*)?(focally\\s*)?(and\\s*)?musc\\w+',
                'inv\\w+\\s*(into\\s*)?(superficial\\s*)?musc\\w+',
                'inv\\w+\\s*(of\\s*)?(deep\\s*)?(smooth\\s*)musc\\w+',
                '(portions?|fragments?)\\s*of\\s*(smooth\\s*)?musc\\w+',
                //Muscularis propria (detrusor muscle) present
                'musc\\w+\\s*(propria\\s*)?(\\(detrusor\\s*muscles?\\))?\\s*(is\\s*|are\\s*)?(present|seen|identif(ied|iable)|involved)',
                'involving\\s*musc\\w+',
                'extending\\s*(to|into)\\s*(the\\s*)?musc\\w+',
                'deep\\s*muscle\\s*\\(muscularis\\s*propria\\)\\s*present',
                'bundles\\s*of\\s*smooth\\s*musc\\w+',
                'seen\\s*in\\s*the\\s*(smooth\\s*)musc\\w+',
                '(detrus?sor\\s*)?\\s*musc\\w+\\s*positive',
                'extending\\s*into\\s*(smooth\\s*|outer\\s*|detrussor)musc\\w+',
                'includes\\s*musc\\w+',
                //Negative for intrusion, however, some are questionable about their muscle in specimen status.
                'musc\\w+\\s*(propria\\s*)?,?\\s*(is\\s*|are\\s*)?negative\\s*for\\s*(malignancy|tumor|cancer|carcinoma)',
                'musc\\w+\\s*(propria\\s*)?(bundles\\s*|tissues?\\s*|wall\\s*|layer\\s*|fibers?\\s*)?(is\\s*|are\\s*)?(easily\\s*)?(identified|identifiable|seen)',
                'no\\s*invasion\\s*seen\\s*of\\s*musc\\w+',
                'musc\\w+\\s*(propria\\s*)?inv\\w+\\s*(is\\s*)(identified|seen|present)',
                'without\\s*invasion\\s*of\\s*musc\\w+',
                'without\\s*invasion\\s*of\\s*lamina\\s*propria\\s*(or|and)\\s*(smooth\\s*)?musc\\w+',
                'musc\\w+\\s*(propria\\s*)?:?\\s*(yes|present|negative)',
                'inv\\w+\\s*(of\\s*|the\\s*)?(smooth\\s*|detruss?or\\s*|outer\\s*)?musc\\w+',
                'musc\\w+\\s*(propria\\s*)?invasion',
                //Invasion types
                "no\\s*muscle\\s*invasion\\s*is\\s*identified",
                'without\\s*invasion\\s*(through|into)\\s*(muscularis|muscle)',
                'no\\s*evidence\\s*of\\s*smooth\\s*muscle\\s*invasion',
                'no\\s*invasion\\s*in\\s*the\\slamina\\s*propria\\s*and\\a*muscle',
                'musc\\w+\\s*(propria\\s*)?\\s*-?\\s*(involved|present|identified|seen)',
                'no\\s*invasion\\s*of\\s*tumor\\s*into\\s*subepithelial\\s*connective\\s*tissue\\s*or\\s*musc\\w+',
                'muscularis\\s*propria\\s*present',
                'fossa\\s*within\\s*(the\\s*)?(deep\\s*)?muscle',
                'small\\s*strip\\s*of\\s*the\\s*musc\\w+',
                'musc\\w+\\s*(propria\\s*)?is\\s*not\\s*involved\\s*by\\s*tumor',
                'musc\\w+\\s*(propria\\s*)?sampled',
                'musc\\w+\\s*(propria\\s*)?is\\s*not\\s*inv\\w+',
                '\\(musc\\w+\\s*propria\\)\\s*present',
                'invades\\s*into\\s*the\\s*stroma\\s*and\\s*the\\s*detrusor\\s*muscle',
                'presence\\s*of\\s*musc\\w+\\s*(propria)?',
                'no\\s*inv\\w+into\\s*(deep\\s*)?mus\\w+',
                'biopsy:\\s*-?musc\\w+',
                '(detrusor\\s*)?musc\\w+\\s+(propria\\s*)?(is\\s*)?free\\s*of',
                'no\\s*mus\\w+\\s*(propria\\s*)?invasion\\s*noted',
                'musc\\w+\\s*is\\s*easily\\s*identified',
                'included\\s*portions\\s*of\\s*(superficial\\s*)?\\s*musc\\w+',
                'musc\\w+\\s*shows\\s*no\\s*evidence\\s*of\\s*invasion',
                'no\\s*invas\\w+\\s*into\\s*(the\\s*)?deep\\s*muscle',
                'tumor\\s*biopsy:\\s*-\\s*musc\\w+\\s*',
                'no\\s*musc\\w+\\s*inva\\w+',
                'inva\\w+\\s*into\\s*(the\\s*)?musc\\w+',
                'with\\s*extension\\s*(through\\s*|into\\s*)the\\s*musc\\w+',
                'musc\\w+\\s*(is\\s*)?appreciated',
                'musc\\w+\\s*(is\\s*)?easily\\s*identified',
                'associated\\s*(smooth\\s*)?mus\\w+',
                'not\\s*into\\s*musc\\w+',
                'no\\s*inva\\w+\\s*of\\s*lamina\\s*propria\\s*or\\s*musc\\w+',
                'musc\\w+\\s*(propria\\s*)\\s*tissue\\s*with',
                'mus\\w+\\s*,?\\s*uninvolved',
                'inva\\w+\\s*into\\s*(deep\\s*)?(smooth\\s*)?musc\\w+',
                'inva\\w+\\s*into\\s*the\\s*laminar?\\s*propria\\s*and\\s*musc\\w+',
                'fragments\\s*of\\s*deep\\s*mus\\w+'







        ]
        concept_feature_value = "MuscleYes"
        outputType =  "gov.va.vinci.leo.bladder.types.MuscleYes"
    }


    "MuscleNo" {
        expressions = [
                'no\\s*musc\\w+\\s*(invasion|present)',
                "no\\s*(smooth\\s*)?muscu\\w+\\s*(propria\\s*)?(is\\s*|are)?(present)",
                'no\\s*(definite\\s*)musc\\w+\\s*(propria\\s*)?(\\(detrusor\\s*muscles?\\))?\\s*(is\\s*|are\\s*)?(present|seen|identif(ied|iable)|involved)',
                "muscularis\\s*propria\\s*(is\\s*)not\\s*(represented|present|seen|identified|identifiable|involved|noted)",
                "(smooth\\s*)?muscle\\s*(is\\s*)not\\s*(represented|present|seen|identified|identifiable|involved|noted)",
                "no\\s*musc\\w+\\s*(propria\\s*)?(are|is\\s*)?(represented|present|seen|identified|identifiable|involved|noted)",
                'muscle\\s*(wall\\s*)?(is\\s*)?not\\s*(represented|present|seen|identified|identifiable|involved)',
                'no\\s*lamina\\s*propria\\s*or\\s*musc\\w+(propria\\s*)?is\\s*(represented|present|seen|identified|identifiable|involved)',
                'no\\s*definite\\s*smooth\\s*muscle\\s*wall\\s*is\\s*(represented|present|seen|identified|identifiable|involved|noted)',
                'scant\\s*stroma\\s*is\\s*(represented|present|seen|identified|identifiable|involved|noted)',
                'muscularis\\s*(propria\\s*):?\\s*(absent|no\\w+)',
                'devoid\\s*of\\s*(muscularis|muscle)(?!invasion)',
                'no\\s*deep\\s*muscle\\s*(represented|present|seen|identified|identifiable|involved|noted)',
                'muscularis\\s*propria\\s*is\\s*absent',
                'muscularis\\s*(propria\\s*)?(is\\s*)?indeterminate',
                'no\\s*(definite\\s*)?lamina\\s*(propria\\s*)?\\s*or\\s*musc\\w+\\s*(represented|present|seen|identified|identifiable|involved|noted)',
                'no\\s*(mural\\s*|deep\\s*)?musc\\w+\\s*(represented|present|seen|identified|identifiable|involved|noted)',
                '(mural\\s*)?musc\\w+\\s*not\\s*sampled',
                'no\\s*submucosal\\s*tissue\\s*or\\s*muscularis\\s*propria\\s*seen',
                //'no\\s*musc\\w+\\s*(propria\\s*)?(?!invasion)',
                'musc\\w+\\s*(propria\\s*)?(is\\s*|are\\s*)?not\\s*(represented|present|seen|identified|identifiable|involved|noted)',
                'musc\\w+\\s*(propria\\s*)?(is\\s*|are\\s*)absent',
                'presence\\s*of\\s*muscularis\\s*propria\\s*indeterminate',
                'no\\s*musc\\w+\\s*(propria\\s*)?(is\\s*)?rep\\w+',
                'no\\s*musc\\w+\\s*(propria\\s*)?(is\\s*)?incl\\w*',
                'no\\s*mu\\w+\\s*propria\\s*(is\\s*)?(represented|present|seen|identified|identifiable|involved)\\s*for\\s*evaluation',
                'no\\s*deep\\s*muscle\\s*\\(media\\)\\s*(is\\s*)?(represented|present|seen|identified|identifiable|involved)',
                'no\\s*(smooth|deep)?\\s*mu\\w+\\s*(is\\s*)?(represented|present|seen|identified|identifiable|involved)',
                'no\\s*muscle\\s*(or\\s*lamina\\s*propria\\s*)?submitted',
                'no\\s*deep\\s*muscle\\s*layer\\s*(represented|present|seen|identified|identifiable|involved|noted)',
                'no\\s*deep\\s*muscle\\s*is\\s*(represented|present|seen|identified|identifiable|involved|noted)\\s*for\\s*evaluation',
                'no\\s*(detrus(e|o)r\\s*)?musc\\w+\\s*(propria\\s*)?(bundles\\s*|tissues?\\s*|wall\\s*|layer\\s*|fibers?\\s*)?(is\\s*|are\\s*)?(represented|present|seen|identified|identifiable|involved|noted)',
                'musc\\w+\\s*(propria\\s*)?(\\(detrusor\\s*muscles?\\))?\\s*(is\\s*|are\\s*)?not\\s*(represented|present|seen|identified|identifiable|involved|noted)'
        ]
        concept_feature_value = "MuscleNo"
        outputType = "gov.va.vinci.leo.bladder.types.MuscleNo"
    }


}
