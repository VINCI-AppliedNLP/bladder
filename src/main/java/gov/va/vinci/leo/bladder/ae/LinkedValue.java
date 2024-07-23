package gov.va.vinci.leo.bladder.ae;

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.tools.ConfigurationParameterImpl;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.metadata.ConfigurationParameter;

import java.util.Collection;

/**
 * Created by thomasginter on 2/11/16.
 */
public class LinkedValue extends DocumentValue {


    protected String valueFeatureName = "Value";

    /**
     * Default constructor for initialization by the UIMA Framework.
     */
    public LinkedValue() {
        super();
    }

    /**
     * Constructor setting the initial values for the inputTypes and outputType parameters.
     *
     * @param outputType Annotation type to be used as output for this annotator
     * @param inputTypes One or more annotation types on which processing will occur
     */
    public LinkedValue(String outputType, String... inputTypes) {
        super(outputType, inputTypes);
    }

    /**
     * Constructor setting the initial values for the number of instances, inputTypes, and outputType parameters.
     *
     * @param numInstances Number of replication instances for this annotator in the pipeline
     * @param outputType   Annotation type to be used as output for this annotator
     * @param inputTypes   One or more annotation types on which processing will occur
     */
    public LinkedValue(int numInstances, String outputType, String... inputTypes) {
        super(numInstances, outputType, inputTypes);
    }

    /**
     * Called once by the UIMA Framework for each document being analyzed (each
     * CAS instance). Acts on the parameters given by <initialize> method. Main
     * method to implement the annotator logic. In the base class, this simply
     * increments to numberOfCASesProcessed
     *
     * @param aJCas the CAS to process
     * @throws AnalysisEngineProcessException if an exception occurs during processing.
     */
    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        for(String type : inputTypes) {
            try {
                for(Annotation a : AnnotationLibrarian.getAllAnnotationsOfType(aJCas, type, false)) {
                    String value = comparator.getValueString(a);
                    Feature valueFeature = a.getType().getFeatureByBaseName(valueFeatureName);
                    if(valueFeature != null)
                        a.setFeatureValueFromString(valueFeature, value);
                }
            } catch (CASException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    public String getValueFeatureName() {
        return valueFeatureName;
    }

    public LinkedValue setValueFeatureName(String valueFeatureName) {
        this.valueFeatureName = valueFeatureName;
        return this;
    }

    public static class Param extends DocumentValue.Param {
        public static ConfigurationParameter VALUE_FEATURE_NAME
            = new ConfigurationParameterImpl("valueFeatureName",
                "Name of the feature whose value we will set",
                ConfigurationParameter.TYPE_STRING, false, false, new String[0]);
    }
}
