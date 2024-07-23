import gov.va.vinci.leo.bladder.types.InvasionDepth

import java.util.regex.Pattern
/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "DiscoveryRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = InvasionDepth.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [Pattern.DOTALL] as Integer[]
    }

    "muscle" {
        expressions = [
                "extending\\s*into\\s*outer\\s*musc\\w+\\s*propria",
                "extending\\s*into(\\s*the)?\\s*musc\\w+\\s*propria",
                "extending\\s*to\\s*musc\\w+\\s*propria",
                "focai\\w+\\s*suspicious\\s*for\\s*invas\\w+\\s*into\\s*deep\\s*smooth\\s*musc\\w+",
                "focai\\w+\\s*suspicious\\s*for\\s*invas\\w+\\s*into\\s*the\\s*musc\\w+\\s*propria",
                'focally\\s*muscularis',
                "infilt\\w+\\s*extensively\\s*the\\s*musc\\w+\\s*propria",
                "infilt\\w+\\s*extensively\\s*throughout\\s*the\\s*musc\\w+\\s*propria",
                "infilt\\w+\\s*musc\\w+\\s*propria",
                "infilt\\w+\\s*the\\s*deep\\s*musc\\w+",
                "infilt\\w+\\s*the\\s*lamina\\s*propria\\s*and\\s*smooth\\s*musc\\w+",
                "infilt\\w+\\s*through\\s*the\\s*deep\\s*musc\\w+",
                "inva\\w+\\s*into\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*into\\s*the\\s*laminar\\s*propria\\s*and\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*into\\s*the\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*musc\\w+",
                "inva\\w+\\s*small\\s*musc\\w+\\s*fibers",
                "inva\\w+\\s*smooth\\s*musc\\w+",
                "inva\\w+\\s*superficial\\s*musc\\w+",
                "inva\\w+\\s*the\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*through(\\s*the)?\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*into(\\s*the)?\\s*musc\\w+\\s*propria\\s*present",
                "inva\\w+\\s*into(\\s*the)?\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*of\\s*lamina\\s*propria\\s*and\\s*musc\\w+\\s*propria",
                "inva\\w+\\s*of\\s*musc\\w+\\s*propria\\s*present",
                "inva\\w+\\s*of\\s*musc\\w+\\s*(propria)?",
                "inva\\w+\\s*of\\s*smooth\\s*musc\\w+",
                "inva\\w+\\s*of\\s*the\\s*lamina\\s*propria\\s*and\\s*smooth\\s*musc\\w+",
                "inva\\w+\\s*to\\s*lamina\\s*propria\\s*and\\s*musc\\w+\\s*propria",
                "inva\\w+,\\s*involv\\w+\\s*detrusor\\s*musc\\w+",
                "involv\\w+\\s*detrusor",
                "involv\\w+\\s*musc\\w+\\s*propria",
                "involv\\w+\\s*musc\\w+\\s*tissue",
                "involv\\w+\\s*musc\\w+",
                "involv\\w+\\s*of\\s*musc\\w+",
                "involv\\w+\\s*the\\s*musc\\w+\\s*propria",
                "musc\\w+\\s*invas\\w+\\s*(is\\s*)?identified",
                "musc\\w+\\s*invas\\w+",
                "musc\\w+\\s*positive\\s*for\\s*invas\\w+\\s*carcinoma",
                "musc\\w+\\s*(propria\\s*)?\\(detrusor\\s*musc\\w+\\)\\s*present\\s*and\\s*invad\\w+\\s*by\\s*tumor",
                "musc\\w+\\s*(propria\\s*)?\\(detrusor\\s*musc\\w+\\)\\s*present\\s*and\\s*invad\\w+",
                "musc\\w+\\s*(propria\\s*)?invas\\w+\\s*identified",
                "musc\\w+\\s*(propria\\s*)?invas\\w+\\s*present",
                "musc\\w+\\s*(propria\\s*)?invas\\w+",
                "musc\\w+\\s*(propria\\s*)?(is\\s*)?involv\\w+",
                "musc\\w+\\s*(propria\\s*)?(is\\s*)?present\\s*and\\s*infilt\\w+\\s*by\\s*tumor",
                "musc\\w+\\s*(propria\\s*)?(is\\s*)?present\\s*and\\s*is\\s*invad\\w+\\s*by\\s*tumor",
                "musc\\w+\\s*(propria\\s*)?with\\s*scant\\s*infilt\\w+\\s*transitional\\s*cell\\s*carcinoma",
                "musc\\w+\\s*(propria\\s*)?with\\s*scant\\s*infilt\\w+",
                "suspicious\\s*for\\s*invas\\w+\\s*into\\s*deep\\s*smooth\\s*musc\\w+",
                "suspicious\\s*for\\s*invas\\w+\\s*into\\s*the\\s*musc\\w+\\s*propria",
                "suspicious\\s*for\\s*musc\\w+\\s*invas\\w+",
                "tumor\\s*infilt\\w+\\s*the\\s*deep\\s*musc\\w+",
                "tumor\\s*invad\\w+\\s*lamina\\s*propria,\\s*focai\\w+\\s*musc\\w+\\s*propria",
                "tumor\\s*invad\\w+\\s*musc\\w+\\s*propria",
                "tumor\\s*invad\\w+\\s*musc\\w+",
                "tumor\\s*invad\\w+\\s*superficial\\s*musc\\w+",
                "tumor\\s*invas\\w+\\s*through\\s*lamina\\s*propria\\s*into\\s*deep\\s*smooth\\s*musc\\w+",
                "tumor\\s*penetrates\\s*musc\\w+\\s*propria",
                "which\\s*focai\\w+\\s*involv\\w+\\s*musc\\w+",
                "with\\s*extensive\\s*musc\\w+\\s*infilt\\w+",
                "with\\s*focai\\w+\\s*invas\\w+\\s*of\\s*smooth\\s*musc\\w+",
                "with\\s*invas\\w+\\s*into\\s*musc\\w+\\s*propria",
                "with\\s*invas\\w+\\s*of\\s*lamina\\s*propria\\s*and\\s*musc\\w+\\s*propria",
                "with\\s*invas\\w+\\s*of\\s*the\\s*lamina\\s*propria\\s*and\\s*smooth\\s*musc\\w+.",
                "with\\s*involv\\w+\\s*of\\s*musc\\w+",
                "with\\s*lamina\\s*propria\\s*and\\s*musc\\w+\\s*propria\\s*invas\\w+",
                "with\\s*musc\\w+\\s*propria\\s*invas\\w+",
                'include(d|s)\\s*portion(s)?\\s*of\\s*(the\\s*)?(superficial\\s*)?\\s*mus\\w+',
                'Invasion:\\s*musc\\w+\\s*muc\\w+:\\s*positive',
                'inva\\w+\\s*lamina\\s*propria',
                'submucosal\\s*inva\\w+\\s*can\\s?not\\s*be\\s*entirely\\s*ruled\\s*out',


        ]
        concept_feature_value = "muscle"
            outputType = "gov.va.vinci.leo.bladder.types.InvasionDepthMuscle"
    }
 /**/
    "Other" {
        expressions = [
                'Involvement\\s*of\\s*other\\s*tissues',
                "invading\\s*through\\s*muscle\\s*wall",
                'stromal\\s*invasion:\\s*(positive|yes)',
                'invasion\\s*into\\s*(the\\s*)?stroma'
        ]
        concept_feature_value = "other_organ"
            outputType = "gov.va.vinci.leo.bladder.types.InvasionDepthOther"
    }
    /**/
    "Perivesical" {
        expressions = [
                "into\\bperi-?v\\w+\\s*(fat|tissue|adipose)"
                ,  '(inva(d|s)\\w+|extension\\s*through)\\s+(\\w+\\s+){0,8}\\bperi-?v\\w+\\s+(fat|tissue|adipose)?'  //TODO:fix
                ,"(fat|tissue|adipose)\\s*infiltrated",
                'tumor\\s*invades\\s*peri\\w+\\s*tissue',
                'focally\\s*into\\s*adipose\\s*tissue',
                'invades\\s*through\\s*muscle\\s*wall\\s*into\\s*peri\\w+\\s*fat',
                'invasion\\s*in\\s*the\\s*peri\\w+',
                'extension\\s*into\\s*peri\\w+',
                '(infiltrating|infiltrates|extension|extending)\\s*(into\\s*)?(the\\s*)?peri\\w+',
                'into\\s*the\\s*adjacent\\s*inner\\s*third\\s*of\\s*the\\s*(perivesical|peripelvic)'
        ]
        concept_feature_value = "perivesical"
            outputType = "gov.va.vinci.leo.bladder.types.InvasionDepthPerivesical"
    }


    /**/
    "Superficial" {
        expressions = [
                //infiltrate|invade|invasive
                "cords\\s*(of\\s*(the)?)\\s*tumor\\s*in\\w+\\s*between\\s*cords\\s*(of\\s*(the)?)\\s*fibromyxoid\\s*stroma",
                "foc(a|i)\\w+\\s*lamina\\s*propria\\s*inva(s|d)\\w+",
                "foc(a|i)\\w+\\s*inva(s|d)\\w+\\s*lamina\\s*propria",
                "in\\w+\\s*lamina\\s*propria",
                "in\\w+\\s*(\\s*into)?(\\s*the)?\\s*lamina\\s*propria",
                "in\\w+\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "in\\w+\\s*urothelial\\\\s*carcinoma,?\\s*(\\w+\\s*){0,10}\\s*no\\s*muscularis\\s*propria",
                "in\\w+\\s*(\\w+\\s+){0,10}\\s*no\\s*muscularis\\s*propria",
                'in\\w+\\s*lamina\\s*propria',
                'in\\w+(\\s*into)?(\\s*the)?\\s*lamina\\s*propria',
                'in\\w+(\\s*into)?(\\s*the)?\\s*stroma',
                'in\\w+\\s*is\\s*present(\\s*into)?(\\s*the)?\\s*lamina\\s*propria',
                'limited\\s*to\\s*the\\s*lamina',

                //New
                'ex\\w+\\s*(\\s*into)?(\\s*the)?\\s*lamina',
                "in\\w+\\s*foc(a|i)\\w+\\s*lamina\\s*propria",
                "in\\w+\\s*(into)?(\\s*the)?\\s*lamina\\s*propria",
                "in\\w+\\s(into)?(\\s*the)?\\s*lamina\\s*propria\\s*and\\s*superficial\\s*muscularis\\s*mucosae",
                "in\\w+\\s*is\\s*present\\s*into_the_\\s*lamina\\s*propria",
                "in\\w+\\s*(of\\s*(the)?)\\s*subepithelial\\s*connective\\s*tissue\\s*can\\s*not\\s*be\\s*ruled\\s*out",
                "in\\w+\\s*through\\s*muscularis\\s*mucosa",
                "in\\w+\\s*through\\s*muscularis\\s*mucosa\\s*is\\s*identified",
                "in\\w+:\\s*superficial",
                "in\\w+\\s*(into)?(\\s*the)?\\s*subepithelial\\s*connective\\s*tissue",
                "in\\w+\\s*(into)?(\\s*the)?\\s*stroma",
                "in\\s*(of\\s*(the)?)\\s*submucosa",
                "lamina\\s*propria\\s*in\\w+",
                "lamina\\s*propria\\s*in\\w+\\s*is\\s*seen",
                "microinva(s|d)\\w+\\s*(into)?(\\s*the)?\\s*superficial\\s*lamina\\s*propria",
                "microinva(s|d)\\w+\\s*is\\s*identified",
                "nests\\s*(of\\s*(the)?)\\s*urothelial\\s*cells\\s*extending\\s*(into)?(\\s*the)?\\s*lamina\\s*propria",
                "present\\s*in\\s*(of\\s*(the)?)\\s*subepithelial\\s*connective\\s*tissue",
                "supe\\w+\\s*inva(s|d)\\w+",
                "super\\w+\\s*inva(s|d)\\w+\\s*cannot\\s*be\\s*ruled\\s*out",
                "super\\w+\\s*lamina\\s*propria\\s*inva(s|d)\\w+",
                "suspicious\\s*for\\s*lamina\\s*propria\\s*inva(s|d)\\w+",
                "tumor\\s*cells\\s*infiltr\\w+\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "tumor\\s*inva(s|d)\\w+\\s*lamina\\s*propria",
                "tumor\\s*inva(s|d)\\w+\\s*subepithelial\\s*connective\\s*tissue",
                "in\\w+\\s*(into)?(\\s*the)?\\s*lamina\\s*propria",
                "foc(a|i)\\w+\\s*lamina\\s*propria\\s*inva(s|d)\\w+",
                "lamina\\s*propria\\s*inva(s|d)\\w+",
                "cannot\\s*exclude\\s*superficial\\s*infiltr\\w+\\s*(of\\s*(the)?)\\s*subepithelial\\s*connective\\s*tissue",
                "carcinoma\\s*in-situ\\s*involving\\s*(of\\s*(the)?)\\s*submucosa",
                "foc(a|i)\\w+\\s*inva(s|d)\\w+\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "foc(a|i)\\w+\\s*submucosal\\s*inva(s|d)\\w+\\s*identified",
                "in\\w+\\s*laminal\\s*propria",
                "in\\w+\\s*,?\\s*(uclerated)?\\.?\\s*no\\s*muscularis\\s*propria",
                "in\\w+\\s*cannot\\s*be\\s*ruled\\s*out",
                "in\\w+\\s*(into)?(\\s*the)?\\s*lamina\\s*propria\\s*is\\s*identified",
                "in\\w+\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "in\\w+\\s*urothelial\\s*carcinoma\\s*to\\s*lamina\\s*propria",
                "in\\w+\\s*(the\\s*)?lamina\\s*propria",
                "in\\w+\\s*(the\\s*)?submucosa",
                "INVASIVE\\s*HIGH\\s*GRADE\\s*UROTHELIAL\\s*CARCINOMA,\\s*NON-PAPILLARY;\\s*NO\\s*DEEP\\s*MUSCLE\\s*PRESENT\\s*IN\\s*THE\\s*BIOPSY\\s*SPECIMEN",
                "lamina\\s*propria:\\s*positive",
                "inva(s|d)\\w+:\\s*lamina\\s*propria:\\s*positive",
                'noted\\s*in(\\s*the)?\\s*lamina',
                'noted\\s*in(\\s*the)?\\s*stroma',
                "stromal\\s*inva(s|d)\\w+",
                "sub\\w+\\s*inva(s|d)\\w+",
                "sub\\w+\\s*inva(s|d)\\w+\\s*can\\s*not\\s*be\\s*entirely\\s*ruled\\s*out",
                "sub\\w+\\s*inva(s|d)\\w+\\s*status:\\s*present",
                "suggestion\\s*(of\\s*(the)?)\\s*foc(a|i)\\w+\\s*superficial\\s*inva(s|d)\\w+\\s*(into)?(\\s*the)?\\s*stroma",
                "suggestive\\s*(of\\s*(the)?)\\s*superficial\\s*lamina\\s*propria\\s*inva(s|d)\\w+",
                "tumor\\s*are\\s*noted\\s*in\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "tumor\\s*cells\\s*are\\s*noted\\s*in\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "tumor\\s*infiltr\\w+\\s*lamina\\s*propria",
                "tumor\\s*infiltr\\w+\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "tumor\\s*inva(s|d)\\w+\\s*(into)?(\\s*the)?\\s*subepithelial\\s*connective\\s*tissue",
                "tumor\\s*inva(s|d)\\w+\\s*subepithelial\\s*connective",
                "tumor\\s*inva(s|d)\\w+\\s*subepithelial\\s*connective\\s*tissue\\s*(lamina\\s*propria)",
                "tumor\\s*is\\s*noted\\s*in\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "tumor\\s*is\\s*present\\s*in\\s*(of\\s*(the)?)\\s*subepithelial\\s*connective\\s*tissue",
                "tumor\\s*limited\\s*to\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "foc(a|i)\\w+\\s*inva(s|d)\\w+\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "sub\\w+\\s*inva(s|d)\\w+",
                "ex\\w+\\s*inva(s|d)\\w+\\s*(into)?(\\s*the)?\\s*lamina\\s*propria",
                "ex\\w+\\s*tumor\\s*necrosis\\s*and\\s*lamina\\s*propria\\s*inva(s|d)\\w+",
                "foc(a|i)\\w+\\s*infiltr\\w+\\s*(of\\s*(the)?)\\s*lamina\\s*propria",
                "stromal\\s*inva(s|d)\\w+",
                "submucosal\\s*inva(s|d)\\w+",
                "super\\w+\\s*inva(s|d)\\w+",
                "super\\w+\\s*lamina\\s*propria\\s*inva(s|d)\\w+",
                "In\\w+:?\\s*lamina\\s*propria:?\\s*positive",
                "no\\s*muscularis\\s*propria\\s*(seen|present|represented)",
                "super\\w+\\s*invasion\\s*cannot\\s*be\\s*ruled\\s*out",
                "sub\\w+\\s*invasion\\s*cannot\\s*be\\s*ruled\\s*out",
                "present\\s*in\\s*the\\s*sub\\w+\\s*connective\\s*tissue",
                'in\\w+\\s*deep\\s*(into\\s*|to\\s*)?(the\\s*)?bladder\\s*mucosa',
                'in\\w+\\s*,?\\s*with\\s*neoplasm\\s*extending\\s*adjacent\\s*to,\\s*not\\s*not'
                 , 'superf\\w+\\b(\\w+|\\s+|:){1,15}\\bin\\w+'
                , '\\binvas\\w+\\b(.|\\s){1,15}\\bsuperf\\w+'
                , '\\bex\\w+\\b(\\w+|\\s|:){1,15}\\bsuperf\\w+'

        ]
        concept_feature_value = "superficial"
            outputType = "gov.va.vinci.leo.bladder.types.InvasionDepthSuperficial"
    }
/**/
}