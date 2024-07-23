package gov.va.vinci.leo.bladder.ae;

import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.bladder.types.GeneralType;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.descriptors.TypeDescriptionBuilder;
import gov.va.vinci.leo.flat.types.Flat;
import gov.va.vinci.leo.refst.types.RefSt;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.impl.TypeDescription_impl;

import java.util.HashMap;

public class RefStAnnotator extends LeoBaseAnnotator {
    static HashMap<String, HashMap<String, String>> typeMap = getMap();

    @Override
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);
    }

    static HashMap<String, HashMap<String, String>> getMap() {
        typeMap = new HashMap<String, HashMap<String, String>>();
        //FeatureName	OptionName

        HashMap<String, String> a = new HashMap<String, String>();
        a.put("No", "gov.va.vinci.leo.refst.types.CIS_no");
        a.put("Yes", "gov.va.vinci.leo.refst.types.CIS_yes");
        typeMap.put("Carcinoma In Situ (CIS)", a);

        a = new HashMap<String, String>();
        a.put("Text", "gov.va.vinci.leo.refst.types.Comments");
        typeMap.put("Comments", a);

        a = new HashMap<String, String>();
        a.put("Outside Pathology", "gov.va.vinci.leo.refst.types.DocType_outside");
        a.put("Not Bladder Pathology", "gov.va.vinci.leo.refst.types.DocType_not_bladder");
        typeMap.put("Document Type", a);

        a = new HashMap<String, String>();
        a.put("Low (1)", "gov.va.vinci.leo.refst.types.Grade_1");
        a.put("Intermediate (2)", "gov.va.vinci.leo.refst.types.Grade_2");
        a.put("High (3)", "gov.va.vinci.leo.refst.types.Grade_3");
        a.put("Undifferentiated (4)", "gov.va.vinci.leo.refst.types.Grade_4");
        a.put("Not Stated", "gov.va.vinci.leo.refst.types.Grade_notStated");
        typeMap.put("Grade", a);

        a = new HashMap<String, String>();
        a.put("Other (Specify)", "gov.va.vinci.leo.refst.types.Histology_other");
        a.put("No Cancer", "gov.va.vinci.leo.refst.types.Histology_no_cancer");
        a.put("Urothelial", "gov.va.vinci.leo.refst.types.Histology_urothelial");
        a.put("PUNLMP", "gov.va.vinci.leo.refst.types.Histology_punlmp");
        a.put("Squamous", "gov.va.vinci.leo.refst.types.Histology_squamous");
        a.put("Adenocarcinoma", "gov.va.vinci.leo.refst.types.Histology_adenocarcinoma");
        typeMap.put("Histology", a);

        a = new HashMap<String, String>();
        a.put("Other Organ", "gov.va.vinci.leo.refst.types.InvasionDepth_other");
        a.put("Not Stated", "gov.va.vinci.leo.refst.types.InvasionDepth_not_stated");
        a.put("Muscle", "gov.va.vinci.leo.refst.types.InvasionDepth_muscle");
        a.put("Perivesical", "gov.va.vinci.leo.refst.types.InvasionDepth_perivesical");
        a.put("Superficial", "gov.va.vinci.leo.refst.types.InvasionDepth_superficial");
        typeMap.put("Invasion Depth", a);

        a = new HashMap<String, String>();
        a.put("Invasive", "gov.va.vinci.leo.refst.types.InvasionType_Invasive");
        a.put("Suspected", "gov.va.vinci.leo.refst.types.InvasionType_suspected");
        a.put("Not Stated", "gov.va.vinci.leo.refst.types.InvasionType_not_stated");
        a.put("None", "gov.va.vinci.leo.refst.types.InvasionType_none");
        typeMap.put("Invasion Type", a);

        a = new HashMap<String, String>();
        a.put("No", "gov.va.vinci.leo.refst.types.MuscleInSpeciment_no");
        a.put("Yes", "gov.va.vinci.leo.refst.types.MuscleInSpeciment_yes");
        a.put("Not Stated", "gov.va.vinci.leo.refst.types.MuscleInSpeciment_not_stated");
        typeMap.put("Muscle in Specimen", a);

        a = new HashMap<String, String>();
        a.put("Text", "gov.va.vinci.leo.refst.types.Other");
        typeMap.put("Other (Specify)", a);

        a = new HashMap<String, String>();
        a.put("Tis", "gov.va.vinci.leo.refst.types.Stage_tis");
        a.put("T2", "gov.va.vinci.leo.refst.types.Stage_t2");
        a.put("Other (Specify)", "gov.va.vinci.leo.refst.types.Stage_other");
        a.put("T4", "gov.va.vinci.leo.refst.types.Stage_t4");
        a.put("Ta", "gov.va.vinci.leo.refst.types.Stage_ta");
        a.put("T1", "gov.va.vinci.leo.refst.types.Stage_t1");
        a.put("T3", "gov.va.vinci.leo.refst.types.Stage_t3");
        typeMap.put("Stage", a);
        return typeMap;
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
        String type_name = "gov.va.vinci.leo.flat.types.Flat";
        String keys_feature_name = "keys";
        String values_feature_name = "values";

        FSIterator<Annotation> iter = this.getAnnotationListForType(aJCas, type_name);
        while (iter.hasNext()) {
            Flat f = (Flat) iter.next();
            String featureName = f.getValues(5);
            String optionName = f.getValues(6);
            String outType = (String) ((HashMap) typeMap.get(featureName)).get(optionName);

            RefSt newA = (RefSt) this.addOutputAnnotation(outType, aJCas, f.getBegin(), f.getEnd());
            newA.setVariableType(featureName);
            newA.setVariableValue(optionName);
        }
    }

    public LeoTypeSystemDescription getLeoTypeSystemDescription() {
        LeoTypeSystemDescription types = new LeoTypeSystemDescription();
        try {
            String refParent = "gov.va.vinci.leo.refst.types.RefSt";
            types.addType(TypeDescriptionBuilder.create(refParent, "Parent Type for instance level output", "uima.tcas.Annotation")
                    .addFeature("VariableType", "", "uima.cas.String")
                    .addFeature("VariableValue", "", "uima.cas.String").getTypeDescription());
            for (HashMap<String, String> k : typeMap.values()) {
                for (String t : k.values()) {
                    types.addType(t, "", refParent);
                }
            }
        } catch (Exception e) {
            logger.warn("Exception occurred generating RefSt types system", e);
            throw new RuntimeException(e);
        }//catch
        return types;
    }

    public static class Param extends LeoBaseAnnotator.Param {

    }
}
