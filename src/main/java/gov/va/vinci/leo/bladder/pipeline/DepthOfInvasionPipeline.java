package gov.va.vinci.leo.bladder.pipeline;

import gov.va.vinci.leo.annotationpattern.ae.AnnotationPatternAnnotator;
import gov.va.vinci.leo.bladder.ae.LogicAnnotator;
import gov.va.vinci.leo.bladder.types.DocVariable;
import gov.va.vinci.leo.conceptlink.types.Link;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.descriptors.TypeDescriptionBuilder;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.regex.types.RegularExpressionType;
import gov.va.vinci.leo.types.TypeLibrarian;

import java.util.ArrayList;

/**
 * Created by thomasginter on 2/8/16.
 */
public class DepthOfInvasionPipeline extends BasePipeline {

    public DepthOfInvasionPipeline() {
 /* List for holding our annotators. This list, and the order of the list created the annotator pipeline. */
        ArrayList<LeoAEDescriptor> annotators = new ArrayList<LeoAEDescriptor>();

        try {
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setName("ExcludeRegex")
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/exclude-regex.groovy")
                    .addParameterSetting(RegexAnnotator.Param.OUTPUT_TYPE.getName(), true, false, "String", "gov.va.vinci.leo.bladder.types.Exclude")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/depth_regex.groovy")

                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
/**/
            annotators.add(new AnnotationPatternAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(AnnotationPatternAnnotator.Param.OUTPUT_TYPE.getName(),
                            "gov.va.vinci.leo.bladder.types.NegatedDepth")
                    .setParameterSetting(AnnotationPatternAnnotator.Param.RESOURCE.getName(),
                            "src/main/resources/depth_apa.pattern")
                    .setTypeSystemDescription(getLeoTypeSystemDescription())) ;
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setName("filterDepth")
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.InvasionDepthMuscle",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthOther",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthSuperficial",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthPerivesical"})
                    .setParameterSetting(FilterAnnotator.Param.DELETE_CHILDREN.getName(), false)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setName("filterDepth_2")
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.InvasionDepthMuscle",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthOther",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthSuperficial",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthPerivesical"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.NegatedDepth"})
                    .setParameterSetting(FilterAnnotator.Param.DELETE_CHILDREN.getName(), false)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setName("filterDepth_None")
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.InvasionDepthMuscle",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthOther",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthSuperficial",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthPerivesical"
                            })
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.InvasionTypeNone", "gov.va.vinci.leo.bladder.types.Exclude"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), true)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{ "gov.va.vinci.leo.bladder.types.InvasionDepthMuscle",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthOther",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthSuperficial",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthPerivesical"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), false)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new LogicAnnotator().getLeoAEDescriptor()
                    .setName("DepthLogic")
                    .setParameterSetting(LogicAnnotator.Param.TYPE_NAME.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.InvasionDepthMuscle",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthOther",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthSuperficial",
                                    "gov.va.vinci.leo.bladder.types.InvasionDepthPerivesical"
                            })
                    .setParameterSetting(LogicAnnotator.Param.CONCEPT_FEATURE_NAME.getName(), "concept")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));



/**/
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
            description.addType("gov.va.vinci.leo.bladder.types.InvasionDepth", "depth of invasion", RegularExpressionType.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.InvasionDepthMuscle", "depth of invasion", "gov.va.vinci.leo.bladder.types.InvasionDepth");
            description.addType("gov.va.vinci.leo.bladder.types.InvasionDepthOther", "depth of invasion", "gov.va.vinci.leo.bladder.types.InvasionDepth");
            description.addType("gov.va.vinci.leo.bladder.types.InvasionDepthPerivesical", "depth of invasion", "gov.va.vinci.leo.bladder.types.InvasionDepth");
            description.addType("gov.va.vinci.leo.bladder.types.InvasionDepthSuperficial", "depth of invasion", "gov.va.vinci.leo.bladder.types.InvasionDepth");
            description.addType("gov.va.vinci.leo.bladder.types.DoiBase", "Base terms for depth of invasion", RegularExpressionType.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.DoiType", "Type of depth of invasion", RegularExpressionType.class.getCanonicalName());
            description.addType(TypeDescriptionBuilder.create("gov.va.vinci.leo.bladder.types.Doi", "Depth of Invasion", Link.class.getCanonicalName())
                    .addFeature("Value", "Depth of Invasion Value", "uima.cas.String").getTypeDescription());
            description.addType("gov.va.vinci.leo.bladder.types.DoiValue", "Depth of Invasion Doc Value", DocVariable.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.DoiFlatValue", "Depth of Invation flat value", DocVariable.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.NegatedDepth", "", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }

}