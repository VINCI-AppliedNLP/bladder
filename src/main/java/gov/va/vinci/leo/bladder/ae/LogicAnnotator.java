package gov.va.vinci.leo.bladder.ae;

import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.bladder.types.GeneralType;
import gov.va.vinci.leo.tools.ConfigurationParameterImpl;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;

/**
 * For each grade annotation validate that it is a valid grade assessment.  For numbers classify as one of the four
 * grades then change the concept value to that classification level.
 * <p/>
 * Created by thomasginter on 1/21/16.
 */
public class LogicAnnotator extends LeoBaseAnnotator {
    String[] type_names = null;
    String concept_feature_name = "";

    @Override
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);
        concept_feature_name = (String) aContext.getConfigParameterValue(Param.CONCEPT_FEATURE_NAME.getName());
        type_names = (String[]) aContext.getConfigParameterValue(Param.TYPE_NAME.getName());
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

    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        super.process(aJCas);
        for (String type_name : type_names) {
            FSIterator<Annotation> iter = this.getAnnotationListForType(aJCas, type_name);
            while (iter.hasNext()) {
                Annotation i = iter.next();
                Feature feature = i.getType().getFeatureByBaseName(concept_feature_name);
                GeneralType newA = (GeneralType) this.addOutputAnnotation(GeneralType.class.getCanonicalName(), aJCas, i.getBegin(), i.getEnd());
                newA.setVariableType(type_name);
                newA.setVariableValue(i.getStringValue(feature));

            }
        }
    }


    public static class Param {
        public static ConfigurationParameter TYPE_NAME = new ConfigurationParameterImpl("type_names", "The type to convert to the general type", "String", true, true, new String[0]);
        public static ConfigurationParameter CONCEPT_FEATURE_NAME = new ConfigurationParameterImpl("concept_feature_name", "This is the feature name on the input annotation that will be used to populate the value set", "String", true, false, new String[0]);

    }
}
