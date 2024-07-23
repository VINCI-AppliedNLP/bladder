package gov.va.vinci.leo.bladder.pipeline;

import gov.va.vinci.leo.bladder.ae.LogicAnnotator;
import gov.va.vinci.leo.bladder.types.Histology;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.regex.types.RegularExpressionType;
import gov.va.vinci.leo.types.TypeLibrarian;

import java.util.ArrayList;

public class HistologyPipeline extends BasePipeline {


    public HistologyPipeline() {
        /* List for holding our annotators. This list, and the order of the list created the annotator pipeline. */
        ArrayList<LeoAEDescriptor> annotators = new ArrayList<LeoAEDescriptor>();
        try {
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/histology_regex.groovy")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            /*
          .addDelegate(new AnnotationPatternAnnotator().getLeoAEDescriptor()
              .setParameterSetting(AnnotationPatternAnnotator.Param.OUTPUT_TYPE.getName(), "gov.va.vinci.leo.bladder.types.NegatedHistology")
              .setParameterSetting(AnnotationPatternAnnotator.Param.RESOURCE.getName(), "src/main/resources/histology_apa.pattern")
              .setTypeSystemDescription(getDepthTypeSystemDescription()))
              */
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setName("ExcludeRegex")
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/excludeHist-regex.groovy")
                    .addParameterSetting(RegexAnnotator.Param.OUTPUT_TYPE.getName(), true, false, "String", "gov.va.vinci.leo.bladder.types.ExcludeHist")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(), new String[]{Histology.class.getCanonicalName()})
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    // .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(), new String[]{InvasionType.class.getCanonicalName()})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.ExcludeHist"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(),
                            new String[]{ "gov.va.vinci.leo.bladder.types.HistologyUrothelial",
                                    "gov.va.vinci.leo.bladder.types.HistologySquamous",
                                    "gov.va.vinci.leo.bladder.types.HistologyPunlmp",
                                    "gov.va.vinci.leo.bladder.types.HistologyAdenocarcinoma",
                                    "gov.va.vinci.leo.bladder.types.HistologyOther",
                                    "gov.va.vinci.leo.bladder.types.HistologyNocancer"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), true)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    // .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(), new String[]{InvasionType.class.getCanonicalName()})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.HistologyPunlmp"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(),
                            new String[]{ "gov.va.vinci.leo.bladder.types.HistologyUrothelial"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), true)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));


            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setName("filterDepth")
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(), new String[]{"gov.va.vinci.leo.bladder.types.Histology"})
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setName("filterHistology_2")
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(), new String[]{"gov.va.vinci.leo.bladder.types.Histology"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(), new String[]{"gov.va.vinci.leo.bladder.types.NegatedHistology"})
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{ "gov.va.vinci.leo.bladder.types.HistologyUrothelial",
                                    "gov.va.vinci.leo.bladder.types.HistologySquamous",
                                    "gov.va.vinci.leo.bladder.types.HistologyPunlmp",
                                    "gov.va.vinci.leo.bladder.types.HistologyAdenocarcinoma",
                                    "gov.va.vinci.leo.bladder.types.HistologyOther",
                                    "gov.va.vinci.leo.bladder.types.HistologyNocancer"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), false)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new LogicAnnotator().getLeoAEDescriptor()
                    .setName("HistologyLogic")
                    .setParameterSetting(LogicAnnotator.Param.TYPE_NAME.getName(), new String[] {
                            "gov.va.vinci.leo.bladder.types.HistologyUrothelial",
                            "gov.va.vinci.leo.bladder.types.HistologySquamous",
                            "gov.va.vinci.leo.bladder.types.HistologyPunlmp",
                            "gov.va.vinci.leo.bladder.types.HistologyAdenocarcinoma",
                            "gov.va.vinci.leo.bladder.types.HistologyOther",
                            "gov.va.vinci.leo.bladder.types.HistologyNocancer"})
                    .setParameterSetting(LogicAnnotator.Param.CONCEPT_FEATURE_NAME.getName(), "concept")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));


            } catch (Exception e) {
            e.printStackTrace();
        }

    /* Create aggregate descriptor and add type system */
        pipeline = new LeoAEDescriptor(annotators);
        pipeline.setTypeSystemDescription(getLeoTypeSystemDescription());
    }

    @Override
    public LeoTypeSystemDescription defineTypeSystem() {
        description = new LeoTypeSystemDescription();
        try {
            description.addTypeSystemDescription(CentralTypes.getLeoTypeSystemDescription());
            description.addType(TypeLibrarian.getCSITypeSystemDescription());
            description.addType("gov.va.vinci.leo.bladder.types.Histology", "Histology", RegularExpressionType.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.HistologyUrothelial", "Histology regex annotation","gov.va.vinci.leo.bladder.types.Histology") ;
            description.addType("gov.va.vinci.leo.bladder.types.HistologySquamous", "Histology regex annotation", "gov.va.vinci.leo.bladder.types.Histology") ;
            description.addType("gov.va.vinci.leo.bladder.types.HistologyPunlmp", "Histology regex annotation", "gov.va.vinci.leo.bladder.types.Histology") ;
            description.addType("gov.va.vinci.leo.bladder.types.HistologyAdenocarcinoma", "Histology regex annotation", "gov.va.vinci.leo.bladder.types.Histology") ;
            description.addType("gov.va.vinci.leo.bladder.types.HistologyOther", "Histology regex annotation","gov.va.vinci.leo.bladder.types.Histology") ;
            description.addType("gov.va.vinci.leo.bladder.types.HistologyNocancer", "Histology regex annotation", "gov.va.vinci.leo.bladder.types.Histology")
                    .addType("gov.va.vinci.leo.bladder.types.ExcludeHist", "Exclude Type regex annotation", "gov.va.vinci.leo.bladder.types.Histology");

            description.addType("gov.va.vinci.leo.bladder.types.NegatedHistology", "", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }
}
