package gov.va.vinci.leo.bladder.pipeline;

import gov.va.vinci.leo.bladder.ae.LogicAnnotator;
import gov.va.vinci.leo.bladder.types.Stage;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.regex.types.RegularExpressionType;
import gov.va.vinci.leo.types.TypeLibrarian;

import java.util.ArrayList;

public class StagePipeline extends BasePipeline {

    public StagePipeline() {
        /* List for holding our annotators. This list, and the order of the list created the annotator pipeline. */
        ArrayList<LeoAEDescriptor> annotators = new ArrayList<LeoAEDescriptor>();
        try {
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(),
                            "src/main/resources/stage_regex.groovy")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
/*
          .addDelegate(new AnnotationPatternAnnotator().getLeoAEDescriptor()
                      .setParameterSetting(AnnotationPatternAnnotator.Param.OUTPUT_TYPE.getName(), "gov.va.vinci.leo
                      .bladder.types.NegatedStage")
                      .setParameterSetting(AnnotationPatternAnnotator.Param.RESOURCE.getName(),
                      "src/main/resources/stage_apa.pattern")
                      .setTypeSystemDescription(getDepthTypeSystemDescription()))
*/
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setName("filterDepth")
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.Stage"})
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setName("filterStage_2")
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(), new String[]{"gov.va.vinci.leo.bladder.types.Stage"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.NegatedStage",
                                    "gov.va.vinci.leo.bladder.types.Exclude"})
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new LogicAnnotator().getLeoAEDescriptor()
                    .setName("StageLogic")
                    .setParameterSetting(LogicAnnotator.Param.TYPE_NAME.getName(), new String[]{Stage.class.getCanonicalName()})
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
            description.addType("gov.va.vinci.leo.bladder.types.Stage", "Stage", RegularExpressionType.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.NegatedStage", "", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }
}
