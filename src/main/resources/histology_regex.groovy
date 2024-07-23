import gov.va.vinci.leo.bladder.types.Histology

import java.util.regex.Pattern

/* An arbitrary name for this annotator. Used in the pipeline for the name of this annotation. */
name = "HistologyRegexAnnotator"

configuration { /* All configuration for this annotator. */
    defaults {
        /* Global for all configrations below if a property specified here is not overridden in a section below. */
        outputType = Histology.canonicalName
        case_sensitive = false
        matchedPatternFeatureName = "pattern"
        concept_feature_name = "concept"
        patternFlags = [Pattern.DOTALL] as Integer[]
    }

    "Urothelial" {
        expressions = [
                "\\btcc",
                "intra-?uroth\\w+\\s*neoplasia",
                "pap\\w+\\s*ca(r|n)\\w*",
                "pap\\w+\\s*uroth\\w+\\s*neoplasm",
                "trans\\w+\\s*ca(r|n)\\w*",
                "trans\\w+\\s*cells?",
                "trans\\w+\\s*cells?\\s*ca(r|n)\\w*",
                "tumor\\s*type:?\\s*trans\\w+",
                "tumor\\s*type:?\\s*uroth\\w+",
                "ureo\\w+\\s*ca(r|n)\\w*",
                "uroth\\w+\\s*\\(trans\\w+\\s*cells?\\)\\s*ca(r|n)\\w*",
                "uroth\\w+\\s*100%",
                "uroth\\w+\\s*ca(r|n)\\w*",
                "uroth\\w+\\s*cells?\\s*ca(r|n)\\w*",
                "uroth\\w+\\s*cells?\\s*strongly\\s*suggestive\\s*[for]+\\s*ca(r|n)\\w*",
                "uroth\\w+\\s*neoplasm",
                "uroth\\w+\\s*pap\\w+\\s*ca(r|n)\\w*",
                "uroth\\w+ca(r|n)\\w*"
        ]
        concept_feature_value = "urothelial"
        outputType = "gov.va.vinci.leo.bladder.types.HistologyUrothelial"


    }

    "Squamous" {
        expressions = [
                "squamous\\s*cell\\s*ca(r|n)\\w+"
        ]
        concept_feature_value = "squamous"
        outputType = "gov.va.vinci.leo.bladder.types.HistologySquamous"
    }

    "PUNLMP" {
        expressions = [
                "PUNLMP",
                "(papillary\\s*)?urothelial\\s*neoplasm\\s*of\\s*low\\s*malignant\\s*potential"
        ]
        concept_feature_value = "PUNLMP"
        outputType = "gov.va.vinci.leo.bladder.types.HistologyPunlmp"
    }

    "Adenocarcinoma" {
        expressions = [
                "adenocarcinoma"
        ]
        concept_feature_value = "adenocarcinoma"
        outputType = "gov.va.vinci.leo.bladder.types.HistologyAdenocarcinoma"
    }

    "Other" {
        expressions = [
                "poorly\\s*differentiated\\s*carcinoma",
                "undifferentiated\\s*carcinoma",
                "malignant\\s*neoplasm",
                "small\\s*cell\\s*carcinoma",
                "crushed\\s*malignant\\s*cells",
                "necrotic\\s*tumor",
                "neuroendocrine\\s*carcinoma",
                "urothelium\\s*insufficient\\s*for\\s*evaluation",
                "pending\\s*special\\s*stains"

        ]
        concept_feature_value = "other"
        outputType =  "gov.va.vinci.leo.bladder.types.HistologyOther"
    }


    "NoCancer" {
        expressions = [
                "(negative|no|not|without)\\s*(tumor|malign\\w+|ca(r|n)\\w+)\\s*(seen|present|identified|found)",
                "(negative|no|not|without)\\s*(tumor|malign\\w+|ca(r|n)\\w+)\\s*\\w+\\s*(seen|present|identified|found)",
                "(negative|no|not|without)\\s*\\w+\\s*evidence\\s*of\\s*(tumor|malign\\w+|ca(r|n)\\w+)\\s*",
                "(negative|no|not|without)\\s*diagn\\w+\\s*abnor\\w+",
                "(negative|no|not|without)\\s*evidence\\s*of\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "(negative|no|not|without)\\s*evidence\\s*of\\s*dysplasia\\s*or\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "(negative|no|not|without)\\s*evidence\\s*of\\s*high(-|\\s*)?grade\\s*or\\s*invasive\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "(negative|no|not|without)\\s*for\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "(negative|no|not|without)\\s*invasive\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "(negative|no|not|without)\\s*invasive\\s*(tumor|malign\\w+|ca(r|n)\\w+)\\s*(seen|present|identified|found)",
                "(negative|no|not|without)\\s*residual\\s*invasive\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "(negative|no|not|without)\\s*significant\\s*pathologic\\s*alteration",
                "(negative|no|not|without)\\s*viable\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "(negative|no|not|without)\\s*viable\\s*(tumor|malign\\w+|ca(r|n)\\w+)\\s*(\\w\\s*)?(seen|present|identified|found)",
                "acute\\s*and\\s*chronic\\s*cystitis",
                "acute\\s*and\\s*chronic\\s*inflam\\w+",
                "acute\\s*inflam\\w+",
                "acute\\s*inflam\\w+\\s*exudate",
                "areas\\s*of\\s*acute\\s*inflam\\w+",
                "atypia",
                "atypia\\s*of\\s*(unknown|undetermined)\\s*significance",
                "atypical\\s*uroth\\w+",
                "atypical\\s*uroth\\w+\\s*cells",
                "benign\\s*denuded\\s*uroth\\w+",
                "benign\\s*detruser\\s*muscle",
                "benign\\s*fibromusclar\\s*stroma",
                "benign\\s*muscularis\\s*propria\\s*fragments",
                "benign\\s*transitional\\s*mucosa",
                "benign\\s*uroth\\w+",
                "benign\\s*uroth\\w+\\s*cells",
                "benign\\s*uroth\\w+\\s*mucosa",
                "chronic\\s*and\\s*follicular\\s*cystitis",
                "chronic\\s*cystitis",
                "chronic\\s*inflam\\w+",
                "chronic\\s*inflam\\w+\\s*infiltrate",
                "chronic\\s*inflam\\w+\\s*with\\s*fibroblastic\\s*proliferation",
                "chronically\\s*inflam\\w+\\s*stroma",
                "cluster\\s*of\\s*atypical\\s*cells",
                "cystitis",
                "cystitis\\s*cystica",
                "cystitis\\s*cystica(-|/|\\s*)?glandularis",
                "denuded\\s*mucosa",
                "denuded\\s*uroth\\w+",
                "does\\s*(negative|no|not|without)\\s*appear\\s*to\\s*be\\s*malignant",
                "double(-|\\s*)?j\\s*stent",
                "dysplasia",
                "dysplastic\\s*uroth\\w+\\s*cells",
                "edema",
                "edematous\\s*uroth\\w+\\s*with\\s*squamous\\s*metaplasia\\s*and\\s*chronic\\s*inflam\\w+",
                "focal\\s*mild\\s*uroth\\w+\\s*atypia",
                "focal\\s*papillary\\s*uroth\\w+\\s*hyperplasia",
                "free\\s*of\\s*tumor",
                "giant\\s*cell\\s*reaction",
                "granulomatous\\s*inflam\\w+",
                "high(-|\\s*)?grade\\s*dysplasia",
                "high(-|\\s*)?grade\\s*dysplasia\\s*associated\\s*with\\s*extensive\\s*ulceration(-|/|\\s*)?eroison",
                "hyperplasia",
                "inflam\\w+",
                "malakoplakia",
                "marked\\s*inflam\\w+",
                "marked\\s*mixed\\s*inflam\\w+\\s*and\\s*giant\\s*cell\\s*reaction",
                "mild\\s*acute\\s*and\\s*chronic\\s*cystitis",
                "mild\\s*chronic\\s*cystitis",
                "mild\\s*uroth\\w+\\s*dysplasia",
                "mildly\\s*reactive\\s*epithelium",
                "mucinous\\s*uroth\\w+\\s*metaplasia",
                "mucosa(:|,)?\\s*(negative|no|not|without)\\s*significant\\s*abnor\\w+",
                "mucosa\\s*with\\s*focal\\s*severe\\s*uroth\\w+\\s*atypia",
                "multifocal\\s*fibrosis",
                "necrotic\\s*cellular\\s*debris",
                "necrotic\\s*material\\s*and\\s*lymphoid\\s*cells",
                "necrotic\\s*tissue",
                "papilloma",
                "ranulomatous\\s*cystitis",
                "reactive\\s*atypia",
                "reactive\\s*epithelium",
                "reactive\\s*uroth\\w+",
                "similar\\s*histologic\\s*changes\\s*with\\s*high\\s*grade\\s*dysplasia",
                "slight\\s*inflam\\w+",
                "squamous\\s*metaplasia",
                "there\\s*is\\s*(negative|no|not|without)\\s*evidence\\s*of\\s*dysplasia\\s*or\\s*(tumor|malign\\w+|ca(r|n)\\w+)",
                "uroth\\w+\\s*atypia",
                "uroth\\w+\\s*atypia\\s*of\\s*undetermined\\s*significance",
                "uroth\\w+\\s*dysplasia",
                "uroth\\w+\\s*has\\s*a\\s*benign\\s*reactive\\s*appearance",
                "uroth\\w+\\s*hyperplasia",
                "uroth\\w+\\s*mocusa\\s*with\\s*severe\\s*chronic\\s*inflam\\w+",
                "uroth\\w+\\s*papilloma",
                "uroth\\w+\\s*proliferation",
                "uroth\\w+\\s*with\\s*mild\\s*chronic\\s*inflam\\w+",
                "uroth\\w+\\s*with\\s*reactive\\s*atypia",
                "viable\\s*(tumor|malign\\w+|ca(r|n)\\w+)\\s*is\\s*(negative|no|not|without)\\s*(seen|present|identified|found)"]
        concept_feature_value = "no_cancer"
        outputType = "gov.va.vinci.leo.bladder.types.HistologyNocancer"
    }


}
