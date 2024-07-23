package gov.va.vinci.leo.conceptlink.ae;

import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.conceptlink.ConceptLinkService;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.descriptors.TypeDescriptionBuilder;
import gov.va.vinci.leo.tools.ConfigurationParameterImpl;
import gov.va.vinci.leo.tools.LeoUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Create an output annotation from the boundaries of adjacent annotations as enumerated in the inputTypes list.
 * Annotations that are "linked" are added to the linked feature in the output annotation if the feature is available.
 * A linking occurs for annotations that are overlapping, adjacent, or if not immediately adjacent then they must meet
 * the following conditions:
 * 1. They are closer than the max distance in characters and
 * 2. One of the join patterns matches the entire span of the text between the annotations minus trimmed whitespace.
 *
 * Created by thomasginter on 12/22/15.
 */
public class ConceptLinkAnnotator extends LeoBaseAnnotator {

    /**
     * If true then child types of the input types are included in the merge operations. Default is false.
     */
    protected boolean includeChildren = false;

    /**
     * Maximum distance in characters between annotations to compare. Defaults to 1000.
     */
    protected int maxDistance = 1000;

    /**
     * Maximum number of chars not overlapped by the join pattern in the middle text for the pattern to count as a match.
     */
    protected int maxDifference = 1;

    /**
     * Maximum number of items in the linked collection.
     */
    protected int maxCollectionSize = Integer.MAX_VALUE;

    /**
     * File containing regular expression patterns that indicate joining expressions
     */
    protected String patternFile = null;

    /**
     * If true then remove the annotations that are "linked" by the covering annotation.
     */
    protected boolean removeCovered = false;

    /**
     * If true then allow matches only separated by whitespace
     */
    protected boolean includeWhiteSpace = true;

    /**
     * Merge proximal annotations from the input types list.
     */
    protected ConceptLinkService linkService = null;

    /**
     * One or more Predicates to filter the list of annotations.
     */
    protected String[] typePredicates = null;

    /**
     * Predicate objects used in filtering the list of annotations.
     */
    protected ArrayList<Predicate<Annotation>> predicates = new ArrayList<Predicate<Annotation>>();

    /**
     * Log messages
     */
    private static final Logger log = Logger.getLogger(LeoUtils.getRuntimeClass());

    /**
     * Default constructor for initialization by the UIMA Framework.
     */
    public ConceptLinkAnnotator() {
    }

    /**
     * Constructor setting the initial values for the inputTypes and outputType parameters.
     *
     * @param outputType Annotation type to be used as output for this annotator
     * @param inputTypes One or more annotation types on which processing will occur
     */
    public ConceptLinkAnnotator(String outputType, String... inputTypes) {
        super(outputType, inputTypes);
    }

    /**
     * Constructor setting the initial values for the number of instances, inputTypes, and outputType parameters.
     *
     * @param numInstances Number of replication instances for this annotator in the pipeline
     * @param outputType   Annotation type to be used as output for this annotator
     * @param inputTypes   One or more annotation types on which processing will occur
     */
    public ConceptLinkAnnotator(int numInstances, String outputType, String... inputTypes) {
        super(numInstances, outputType, inputTypes);
    }

    @Override
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);

        if(this.getClass().isInstance(ConceptLinkAnnotator.class) && (inputTypes == null || inputTypes.length < 1)) {
            throw new ResourceInitializationException("inputTypes is a required parameter for this annotator", null);
        }

        if(StringUtils.isNotBlank(patternFile)) {
            try {
                //Validate file exists
                File pFile = new File(patternFile);
                if(!pFile.exists() || !pFile.isFile()) {
                    throw new ResourceInitializationException("File " + patternFile + " either does not exist or is not a file!", null);
                }
                linkService = new ConceptLinkService(maxDistance, maxDifference, maxCollectionSize, pFile);
            } catch (IOException e) {
                throw new ResourceInitializationException(e);
            }
        } else {
            linkService = new ConceptLinkService(maxDistance, maxDifference, maxCollectionSize);
        }

        linkService.setIncludeWhitespace(includeWhiteSpace);
        linkService.setRemoveCovered(removeCovered);

        //Initialize the predicates
        if(typePredicates != null) {
            for(String predicateName : typePredicates) {
                try {
                    Predicate<Annotation> predicate = (Predicate<Annotation>) Class.forName(predicateName).getConstructor().newInstance();
                    predicates.add(predicate);
                } catch (Exception e) {
                    throw new ResourceInitializationException(e);
                }
            }
        }
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        super.process(aJCas);
        linkTypes(aJCas);
    }

    protected ArrayList<Annotation> getTypesList(JCas jCas) throws AnalysisEngineProcessException {
        try {
            Predicate<Annotation>[] predicateArray = (predicates.size() < 1)? null : predicates.toArray(new Predicate[predicates.size()]);
            return ConceptLinkService.getSortedList(jCas, inputTypes, includeChildren, predicateArray);
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
    protected void linkTypes(JCas aJCas) throws AnalysisEngineProcessException {
        String docText = aJCas.getDocumentText();
        try {
            //Get the first set and initialize the sorted list
            ArrayList<Annotation> list = getTypesList(aJCas);

            //If there are not enough annotations to merge then skip it.
            if(list.size() < 2)
                return;

            /**
             * Now merge adjacent annotations in the list
             */
            List<ConceptLinkService.LinkSpan> spans = linkService.linkSpans(list, docText);
            for(ConceptLinkService.LinkSpan span : spans) {
                Annotation a = this.addOutputAnnotation(outputType, aJCas, span.getStart(), span.getEnd());
                linkService.doSetLinkedTypes(a, span.getLinked());
            }
        } catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    @Override
    public LeoTypeSystemDescription getLeoTypeSystemDescription() {
        return new LeoTypeSystemDescription(TypeDescriptionBuilder
                .create("gov.va.vinci.leo.conceptlink.types.Link", "Default Linked Output Type", "uima.tcas.Annotation")
                .addFeature(ConceptLinkService.LINKED_FEATURE_NAME, "Annotations that were 'linked'", "uima.cas.FSArray", "uima.tcas.Annotation", false)
                .getTypeDescription());
    }

    public boolean isIncludeChildren() {
        return includeChildren;
    }

    public ConceptLinkAnnotator setIncludeChildren(boolean includeChildren) {
        this.includeChildren = includeChildren;
        return this;
    }

    public boolean isIncludeWhiteSpace() {
        return includeWhiteSpace;
    }

    public ConceptLinkAnnotator setIncludeWhiteSpace(boolean includeWhiteSpace) {
        this.includeWhiteSpace = includeWhiteSpace;
        return this;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public ConceptLinkAnnotator setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public int getMaxDifference() {
        return maxDifference;
    }

    public ConceptLinkAnnotator setMaxDifference(int maxDifference) {
        this.maxDifference = maxDifference;
        return this;
    }

    public int getMaxCollectionSize() {
        return maxCollectionSize;
    }

    public ConceptLinkAnnotator setMaxCollectionSize(int maxCollectionSize) {
        this.maxCollectionSize = maxCollectionSize;
        return this;
    }

    public boolean isRemoveCovered() {
        return removeCovered;
    }

    public ConceptLinkAnnotator setRemoveCovered(boolean removeCovered) {
        this.removeCovered = removeCovered;
        return this;
    }

    public String getPatternFile() {
        return patternFile;
    }

    public ConceptLinkAnnotator setPatternFile(String patternFile) {
        this.patternFile = patternFile;
        return this;
    }

    public String[] getTypePredicates() {
        return typePredicates;
    }

    public ConceptLinkAnnotator setTypePredicates(String[] typePredicates) {
        this.typePredicates = typePredicates;
        return this;
    }

    public static class Param extends LeoBaseAnnotator.Param {
        public static ConfigurationParameter INCLUDE_CHILDREN
                = new ConfigurationParameterImpl("includeChildren",
                "Should child classes of each type be included in the list",
                ConfigurationParameter.TYPE_BOOLEAN, false, false, new String[0]);
        public static ConfigurationParameter INCLUDE_WHITESPACE
                = new ConfigurationParameterImpl("includeWhiteSpace",
                "Include matches separated by only whitespace, defaults to true",
                ConfigurationParameter.TYPE_BOOLEAN, false, false, new String[0]);
        public static ConfigurationParameter MAX_DISTANCE
                = new ConfigurationParameterImpl("maxDistance",
                "Maximum distance in characters between annotations to be linked",
                ConfigurationParameter.TYPE_INTEGER, false, false, new String[0]);
        public static ConfigurationParameter MAX_DIFFERENCE
                = new ConfigurationParameterImpl("maxDifference",
                "Maximum number of uncovered characters in join pattern match",
                ConfigurationParameter.TYPE_INTEGER, false, false, new String[0]);
        public static ConfigurationParameter MAX_COLLECTION_SIZE
                = new ConfigurationParameterImpl("maxCollectionSize",
                "Maximum number of linked items in the collection",
                ConfigurationParameter.TYPE_INTEGER, false, false, new String[0]);
        public static ConfigurationParameter REMOVE_COVERED
                = new ConfigurationParameterImpl("removeCovered",
                "If true then remove the annotations that are 'linked'",
                ConfigurationParameter.TYPE_BOOLEAN, false, false, new String[0]);
        public static ConfigurationParameter RESOURCE_FILE
                = new ConfigurationParameterImpl("patternFile",
                "File of join patterns that indicate two annotations are associated",
                ConfigurationParameter.TYPE_STRING, false, false, new String[0]);
        public static ConfigurationParameter TYPE_PREDICATES
                = new ConfigurationParameterImpl("typePredicates",
                "One or more predicates to use in filtering the list of annotations",
                ConfigurationParameter.TYPE_STRING, false, true, new String[0]);
    }
}
