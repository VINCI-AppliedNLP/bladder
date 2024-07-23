package gov.va.vinci.leo.bladder.pipeline;
import gov.va.vinci.leo.bladder.ae.LogicAnnotator;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.regex.types.RegularExpressionType;
import gov.va.vinci.leo.types.TypeLibrarian;
import gov.va.vinci.leo.window.ae.WindowAnnotator;
import gov.va.vinci.leo.window.types.Window;

import java.util.ArrayList;

/**
 * Created by vhaslcalbap on 8/23/2016.
 */
public class DocumentTypePipeline extends BasePipeline {
    public DocumentTypePipeline() {
        ArrayList<LeoAEDescriptor> annotators = new ArrayList<LeoAEDescriptor>();
        try {
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/document-regex.groovy")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new WindowAnnotator().getLeoAEDescriptor()
                    .setName("Window")
                    .setParameterSetting(WindowAnnotator.Param.OUTPUT_TYPE.getName(), Window.class.getCanonicalName())
                    .setParameterSetting(WindowAnnotator.Param.WINDOW_RT.getName(), new Integer(10))
                    .setParameterSetting(WindowAnnotator.Param.WINDOW_LT.getName(), new Integer(5))
                    .setParameterSetting(WindowAnnotator.Param.INPUT_TYPE.getName(), new String[]{"gov.va.vinci.leo.bladder.types.DocumentType"})
                    .setParameterSetting(WindowAnnotator.Param.ANCHOR_FEATURE.getName(), "Anchor")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setName("DocTypeAnnotator")
                    .setParameterSetting(RegexAnnotator.Param.RESOURCE.getName(), "src/main/resources/ReleSpecimen.regex")
                    .setParameterSetting(RegexAnnotator.Param.INPUT_TYPE.getName(), new String[]{Window.class.getCanonicalName()})
                    .setParameterSetting(RegexAnnotator.Param.OUTPUT_TYPE.getName(), "gov.va.vinci.leo.bladder.types.ReleSpecimen")
                    .setParameterSetting(RegexAnnotator.Param.CONCEPT_FEATURE_NAME.getName(), "concept")
                    .setParameterSetting(RegexAnnotator.Param.CONCEPT_FEATURE_VALUE.getName(), "Relevant")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setName("DocTypeAnnotator")
                    .setParameterSetting(RegexAnnotator.Param.RESOURCE.getName(), "src/main/resources/IrreleSpecimenTypes.regex")
                    .setParameterSetting(RegexAnnotator.Param.INPUT_TYPE.getName(), new String[]{Window.class.getCanonicalName()})
                    .setParameterSetting(RegexAnnotator.Param.OUTPUT_TYPE.getName(), "gov.va.vinci.leo.bladder.types.IrreleSpecimen")
                    .setParameterSetting(RegexAnnotator.Param.CONCEPT_FEATURE_NAME.getName(), "concept")
                    .setParameterSetting(RegexAnnotator.Param.CONCEPT_FEATURE_VALUE.getName(), "Irrelevant")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    // .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(), new String[]{InvasionType.class.getCanonicalName()})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.IrreleSpecimen",
                                    "gov.va.vinci.leo.bladder.types.Exclude"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.ReleSpecimen"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), true)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{  "gov.va.vinci.leo.bladder.types.IrreleSpecimen",
                                    "gov.va.vinci.leo.bladder.types.ReleSpecimen"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), false)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new LogicAnnotator().getLeoAEDescriptor()
                    .setName("DocumentLogic")
                    .setParameterSetting(LogicAnnotator.Param.TYPE_NAME.getName(), new String[] {
                            "gov.va.vinci.leo.bladder.types.IrreleSpecimen",
                            "gov.va.vinci.leo.bladder.types.ReleSpecimen"
                            })
                    .setParameterSetting(LogicAnnotator.Param.CONCEPT_FEATURE_NAME.getName(), "concept")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));


        } catch (Exception e) {
            e.printStackTrace();
        }


        pipeline = new LeoAEDescriptor(annotators);
        pipeline.setTypeSystemDescription(getLeoTypeSystemDescription());
    }


    protected LeoTypeSystemDescription defineTypeSystem() {
        description = new LeoTypeSystemDescription();
        try {
            description.addTypeSystemDescription(getLeoTypeSystemDescription());
            description.addType(TypeLibrarian.getCSITypeSystemDescription());
            description.addType("gov.va.vinci.leo.bladder.types.DocumentType", "Document regex annotation", RegularExpressionType.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.IrreleSpecimen", "Document regex annotation", "gov.va.vinci.leo.bladder.types.DocumentType");
            description.addType("gov.va.vinci.leo.bladder.types.ReleSpecimen", "Document regex annotation",  "gov.va.vinci.leo.bladder.types.DocumentType")

            ;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }
}
