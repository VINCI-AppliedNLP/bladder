package gov.va.vinci.leo.bladder.ae;

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.bladder.types.InvasionType;
import gov.va.vinci.leo.window.WindowService;
import org.apache.commons.lang.StringUtils;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * For each grade annotation validate that it is a valid grade assessment.  For numbers classify as one of the four
 * grades then change the concept value to that classification level.
 *
 * Created by thomasginter on 1/21/16.
 */
public class InvasionTypeVal extends LeoBaseAnnotator {

    protected Pattern numPattern = Pattern.compile("\\b(IV|[I]{1,3}|1|2|3|4)\\b");
    protected Pattern rangePattern = Pattern.compile("\\b(IV|[I]{1,3}|1|2|3|4).{0,3}\\b(to|-)\\b.{0,3}(IV|[I]{1,3}|1|2|3|4)");
    protected Pattern validPattern = Pattern.compile("histologic|carcinoma|pa(l)?pillary", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    protected WindowService windowService = new WindowService(10, 10, InvasionType.class.getCanonicalName());

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

        Collection<InvasionType> grades = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, InvasionType.type, false);
        for(InvasionType grade : grades) {
            //Validate
            try {
                if(validInvasionType(grade)) {
                    //Check to see if this is valid annotation is a number and get the value
                    classifyNum(grade);
                } else {
                    grade.removeFromIndexes();
                }
            } catch (Exception e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    /**
     * Create a window around the grade annotatoin and check for valid keywords surrounding it.
     *
     * @param grade
     * @return true if this is a valid grade annotation, false if not.
     * @throws Exception if there is an error building the window.
     */
    protected boolean validInvasionType(InvasionType grade) throws Exception {
        Annotation window = windowService.buildNonWhitespaceTokenWindow(grade);
        if(window == null) return false;
        return validPattern.matcher(window.getCoveredText()).find();
    }

    /**
     * If this is a "num" concept, first convert the roman numeral, then set the concept to one of the valid values
     * that the other grades are set to automagically in the regex config.
     *
     * @param grade
     */
    protected void classifyNum(InvasionType grade) {
        String covered = grade.getCoveredText();
        if(!"num".equals(grade.getConcept())) return;
        Matcher m = numPattern.matcher(grade.getCoveredText());
        //Grab the first value and convert it to a number
        if(!m.find()) return;
        String numText = covered.substring(m.start(), m.end());
        Matcher rangeMatcher = rangePattern.matcher(grade.getCoveredText());
        if(rangeMatcher.find()) {
            //This is a range so grab the second number instead
            if(m.find()) numText = covered.substring(m.start(), m.end());
        }
        Integer numVal;
        try {
            numVal = new Integer(numText);
        } catch (NumberFormatException nfe) {
            //If this isn't a digit number then normalize and classify the roman numeral
            numText = StringUtils.stripToEmpty(StringUtils.trimToEmpty(numText.toLowerCase()));
            if("i".equals(numText))
                numVal = new Integer(1);
            else if("ii".equals(numText))
                numVal = new Integer(2);
            else if("iii".equals(numText))
                numVal = new Integer(3);
            else if("iv".equals(numText))
                numVal = new Integer(4);
            else
                numVal = Integer.MAX_VALUE;
        }

        //Now set the concept value based on the numerical value
        String concept = "";
        switch (numVal) {
            case 1: concept = "g1";
                break;
            case 2: concept = "g2";
                break;
            case 3: concept = "g3";
                break;
            case 4: concept = "g4";
                break;
            default: concept = "num";
        }
        grade.setConcept(concept);
    }

    public static class Param {
        /** No params defined **/
    }
}
