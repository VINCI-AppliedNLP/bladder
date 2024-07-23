package gov.va.vinci.leo.bladder.ae;

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.bladder.comparators.BaseValueComparator;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.descriptors.TypeDescriptionBuilder;
import gov.va.vinci.leo.tools.ConfigurationParameterImpl;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This annotator uses a variable class and associated interpreter to calculate the document level classification of
 * the variable in question.  The inputTypes variable is the list of annotations to work on and the outputType is the
 * document level output type that should be created for the variable.  The outputType should be the default type or a
 * child type of the default type.
 *
 * Created by thomasginter on 1/26/16.
 */
public class DocumentValue extends LeoBaseAnnotator {

    BaseValueComparator comparator = null;

    /**
     * Name of the comparator class that will be used for comparison.
     */
    protected String comparatorClass = "";

    /**
     * Default constructor for initialization by the UIMA Framework.
     */
    public DocumentValue() {
    }

    /**
     * Constructor setting the initial values for the inputTypes and outputType parameters.
     *
     * @param outputType Annotation type to be used as output for this annotator
     * @param inputTypes One or more annotation types on which processing will occur
     */
    public DocumentValue(String outputType, String... inputTypes) {
        super(outputType, inputTypes);
    }

    /**
     * Constructor setting the initial values for the number of instances, inputTypes, and outputType parameters.
     *
     * @param numInstances Number of replication instances for this annotator in the pipeline
     * @param outputType   Annotation type to be used as output for this annotator
     * @param inputTypes   One or more annotation types on which processing will occur
     */
    public DocumentValue(int numInstances, String outputType, String... inputTypes) {
        super(numInstances, outputType, inputTypes);
    }

    @Override
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);

        /**
         * Get the variable and interpreter classes from the parameters.
         */
        try {
            Class<?> interpreterClassVar = Class.forName(comparatorClass);
            Constructor<?> con1 = interpreterClassVar.getConstructor();
            comparator = (BaseValueComparator) con1.newInstance();
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public String getComparatorClass() {
        return comparatorClass;
    }

    public DocumentValue setComparatorClass(String comparatorClass) {
        this.comparatorClass = comparatorClass;
        return this;
    }

    /**
     * Called once by the UIMA Framework for each document being analyzed (each
     * CAS instance). Acts on the parameters given by <initialize> method. Main
     * method to implement the annotator logic. In the base class, this simply
     * increments to numberOfCASesProcessed
     *
     * @param aJCas the CAS to process
     * @throws AnalysisEngineProcessException if an exception occurs during processing.
     * @see JCasAnnotator_ImplBase#process(JCas)
     */
    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        super.process(aJCas);

        for(String type : inputTypes) {
            try {
                Collection<Annotation> annotations = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, type, false);
                if(annotations.size() > 0) {
                    Optional<Annotation> optAnnotation = annotations.stream().sorted(comparator).findFirst();
                    Map<String, Object> features = new HashMap<String, Object>();
                    features.put("Value", comparator.getValueString(optAnnotation.get()));
                    features.put("Src", optAnnotation.get());
                    this.addOutputAnnotation(outputType, aJCas, 0, aJCas.getDocumentText().length(), features);
                }
            } catch (CASException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    /**
     * Return the type system description for this annotator.  Extending methods can add or set the variable
     * <code>typeSystemDescription</code> to set a default type system description unique to that annotator.
     *
     * @return the default type system for this annotator.
     */
    @Override
    public LeoTypeSystemDescription getLeoTypeSystemDescription() {
        return new LeoTypeSystemDescription()
                .addType(TypeDescriptionBuilder.create("gov.va.vinci.leo.bladder.types.DocVariable",
                        "Document Level Variable classification", "uima.tcas.Annotation")
                    .addFeature("Value", "Value of the document classification", "uima.cas.String")
                    .addFeature("Src", "Source annotation for the value", "uima.tcas.Annotation")
                    .getTypeDescription());
    }

    public static class Param extends LeoBaseAnnotator.Param {
        public static ConfigurationParameter COMPARATOR_CLASS
            = new ConfigurationParameterImpl("comparatorClass", "Canonical name of the variable class",
                ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
    }
}
