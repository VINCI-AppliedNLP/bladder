package gov.va.vinci.leo.bladder.ae;

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.bladder.types.Doi;
import gov.va.vinci.leo.bladder.types.DoiBase;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Doi Base annotations not covered by Link annotations are Doi annotations with not_stated as the value.
 *
 * Created by thomasginter on 2/17/16.
 */
public class DoiVal extends LeoBaseAnnotator {

    Pattern excludePattern = Pattern.compile("tumor", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

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

        Collection<DoiBase> bases = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, DoiBase.type, false);
        for(DoiBase base : bases) {
            try {
                if(AnnotationLibrarian.getAllContainingAnnotationsOfType(base, Doi.type, false).size() < 1 &&
                        !excludePattern.matcher(base.getCoveredText()).find()) {
                    Doi doi = new Doi(aJCas, base.getBegin(), base.getEnd());
                    doi.setValue("not_stated");
                    FSArray linked = new FSArray(aJCas, 1);
                    linked.set(0, base);
                    doi.setLinked(linked);
                    doi.addToIndexes();
                }
            } catch (CASException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    public static class Param {
        /** No Parameters for this Annotator **/
    }
}
