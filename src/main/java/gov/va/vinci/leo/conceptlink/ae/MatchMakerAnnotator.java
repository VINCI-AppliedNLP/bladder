package gov.va.vinci.leo.conceptlink.ae;

import gov.va.vinci.leo.conceptlink.ConceptLinkService;
import gov.va.vinci.leo.tools.ConfigurationParameterImpl;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Create a linking relationship by iterating through the sorted lisst and then merging each concept type with a value
 * type.  By default checks first to the right and if no match is found checks to the left.  However the order of
 * comparison is configurable with the peekRightFirst parameter.
 * <p/>
 * Created by thomasginter on 12/22/15.
 */
public class MatchMakerAnnotator extends ConceptLinkAnnotator {

    /**
     * Name of the concept type for the relationship.
     */
    protected String conceptTypeName = null;

    /**
     * Name of the value type for the relationship.
     */
    protected String valueTypeName = null;

    /**
     * Class reference for the value type.
     */
    protected Class<?> valueClass = null;

    /**
     * Class reference for the concept type.
     */
    protected Class<?> conceptClass = null;

    /**
     * Check for values to merge on the right first.
     */
    protected boolean peekRightFirst = true;

    /**
     * Default constructor for initialization by the UIMA Framework.
     */
    public MatchMakerAnnotator() {
    }

    /**
     * Constructor setting the initial values for the inputTypes and outputType parameters.
     *
     * @param outputType Annotation type to be used as output for this annotator
     * @param inputTypes One or more annotation types on which processing will occur
     */
    public MatchMakerAnnotator(String outputType, String... inputTypes) {
        super(outputType, inputTypes);
    }

    /**
     * Constructor setting the initial values for the number of instances, inputTypes, and outputType parameters.
     *
     * @param numInstances Number of replication instances for this annotator in the pipeline
     * @param outputType   Annotation type to be used as output for this annotator
     * @param inputTypes   One or more annotation types on which processing will occur
     */
    public MatchMakerAnnotator(int numInstances, String outputType, String... inputTypes) {
        super(numInstances, outputType, inputTypes);
    }

    @Override
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);
        try {
            valueClass = Class.forName(valueTypeName);
        } catch (ClassNotFoundException e) {
            throw new ResourceInitializationException(e);
        }

        try {
            conceptClass = Class.forName(conceptTypeName);
        } catch (ClassNotFoundException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    protected ArrayList<Annotation> getTypesList(JCas jCas) throws AnalysisEngineProcessException {
        try {
            Predicate<Annotation>[] predicateArray = (predicates.size() < 1)? null
                    : predicates.toArray(new Predicate[predicates.size()]);
            return ConceptLinkService.getSortedList(jCas,
                    new String[]{conceptTypeName, valueTypeName},
                    includeChildren, predicateArray);
        } catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    /**
     * Merge the inputTypes that are proximal.  Extending classes can override this method in order to implement a
     * different merging algorithm.
     *
     * @param aJCas CAS from with to retrieve the annotations.
     * @throws AnalysisEngineProcessException
     */
    @Override
    protected void linkTypes(JCas aJCas) throws AnalysisEngineProcessException {
        String docText = aJCas.getDocumentText();
        //Get the list of annotations
        ArrayList<Annotation> list = getTypesList(aJCas);
        if (list.size() < 2) return;
        //Iterate through the list and look for concepts with a value match
        for (int i = 0; i < list.size(); i++) {
            Annotation concept = list.get(i);
            if (!isConcept(concept))
                continue;
            Annotation link = null;
            if (peekRightFirst) {
                link = peek(docText, concept, list, i, 1);
                if (link == null)
                    link = peek(docText, concept, list, i, -1);
            } else { //peek left first
                link = peek(docText, concept, list, i, -1);
                if (link == null) {
                    link = peek(docText, concept, list, i, 1);
                }
            }
            if (link != null) {
                createLink(aJCas, concept, link);
                i++;
            }
        }
    }

    /**
     * Peeks in the direction specified to check for a match.  Returns null if no match is found, else returns the
     * annotation that matched.
     *
     * @param docText text of the document for a potential link
     * @param src     source concept to check
     * @param list    list of annotations which may be a linking match
     * @param index   index of the concept annotation
     * @param next    direction to check for a match
     * @return annotation that is linked or null if no match is found
     */
    protected Annotation peek(String docText, Annotation src, ArrayList<Annotation> list, int index, int next) {
        int p = index + next;
        if (p >= list.size() || p < 0)
            return null;
        Annotation dest = list.get(p);
        if (isValue(dest) && linkService.isANextToB(docText, src, dest))
            return dest;
        return null;
    }

    /**
     * Returns true if this is a concept annotation.
     *
     * @param a annotation to check
     * @return true if this is a concept, false otherwise
     */
    protected boolean isConcept(Annotation a) {
        if (includeChildren)
            return conceptClass.isAssignableFrom(a.getClass());
        return conceptClass.isInstance(a);
    }

    /**
     * Returns true if this is a value annotation.
     *
     * @param a annotation to check
     * @return true if this is a value, false otherwise
     */
    protected boolean isValue(Annotation a) {
        if (includeChildren)
            return valueClass.isAssignableFrom(a.getClass());
        return valueClass.isInstance(a);
    }

    /**
     * Create a linking output annotation when a match is found.
     *
     * @param jCas CAS in which the output annotation will be created
     * @param link Annotations which will be linked
     * @throws AnalysisEngineProcessException If there is an error creating the output annotation
     */
    protected void createLink(JCas jCas, Annotation... link) throws AnalysisEngineProcessException {
        if (link == null || link.length < 2)
            return;

        //Find the start and end
        int start = link[0].getBegin(), end = link[0].getEnd();
        for (int i = 1; i < link.length; i++) {
            start = Math.min(start, link[i].getBegin());
            end = Math.max(end, link[i].getEnd());
        }

        //Create the output annotation
        Annotation rel = this.addOutputAnnotation(outputType, jCas, start, end);
        try {
            linkService.doSetLinkedTypes(rel, new ArrayList<Annotation>(Arrays.asList(link)));
        } catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    public String getConceptTypeName() {
        return conceptTypeName;
    }

    public MatchMakerAnnotator setConceptTypeName(String conceptTypeName) {
        this.conceptTypeName = conceptTypeName;
        return this;
    }

    public String getValueTypeName() {
        return valueTypeName;
    }

    public MatchMakerAnnotator setValueTypeName(String valueTypeName) {
        this.valueTypeName = valueTypeName;
        return this;
    }

    public boolean isPeekRightFirst() {
        return peekRightFirst;
    }

    public MatchMakerAnnotator setPeekRightFirst(boolean peekRightFirst) {
        this.peekRightFirst = peekRightFirst;
        return this;
    }

    public static class Param extends ConceptLinkAnnotator.Param {
        public static ConfigurationParameter CONCEPT_TYPE
                = new ConfigurationParameterImpl("conceptTypeName",
                "Name of the annotation type that represents the concept",
                ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
        public static ConfigurationParameter VALUE_TYPE
                = new ConfigurationParameterImpl("valueTypeName",
                "Name of the annotation type that represents a value",
                ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
        public static ConfigurationParameter PEEK_RIGHT_FIRST
                = new ConfigurationParameterImpl("peekRightFirst",
                "If true check for a value to the right first, defaults to true",
                ConfigurationParameter.TYPE_BOOLEAN, false, false, new String[0]);
    }
}
