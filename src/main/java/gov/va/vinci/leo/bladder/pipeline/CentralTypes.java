package gov.va.vinci.leo.bladder.pipeline;

import gov.va.vinci.leo.annotationpattern.ae.AnnotationPatternAnnotator;
import gov.va.vinci.leo.bladder.ae.DocumentValue;
import gov.va.vinci.leo.bladder.cr.FlatAnnotationDatabaseReader;
import gov.va.vinci.leo.conceptlink.ae.ConceptLinkAnnotator;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.descriptors.TypeDescriptionBuilder;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.window.ae.WindowAnnotator;

/**
 * Created by thomasginter on 1/20/16.
 */
public class CentralTypes {
    public static LeoTypeSystemDescription getLeoTypeSystemDescription() {

        return new LeoTypeSystemDescription()
            .addTypeSystemDescription(new WindowAnnotator().getLeoTypeSystemDescription())
            .addTypeSystemDescription(new RegexAnnotator().getLeoTypeSystemDescription())
            .addTypeSystemDescription(new AnnotationPatternAnnotator().getLeoTypeSystemDescription())
            .addTypeSystemDescription(FlatAnnotationDatabaseReader.flatAnnotationTypeSystem())
            .addTypeSystemDescription(new ConceptLinkAnnotator().getLeoTypeSystemDescription())
            .addTypeSystemDescription(new DocumentValue().getLeoTypeSystemDescription())
            .addTypeSystemDescription(new AnnotationPatternAnnotator().getLeoTypeSystemDescription())

            .addType(TypeDescriptionBuilder.create("gov.va.vinci.leo.bladder.types.GeneralType", "Parent Type for instance level output", "uima.tcas.Annotation")
                .addFeature("VariableType", "",  "uima.cas.String")
                .addFeature("VariableValue", "",  "uima.cas.String").getTypeDescription());
    }
}
