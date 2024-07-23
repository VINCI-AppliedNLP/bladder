package gov.va.vinci.leo.conceptlink;

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.conceptlink.tools.AnnotationComparator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Create output annotations from the boundaries of adjacent annotations.  Annotations that are "linked" are added to
 * the linked feature in the output annotation if the feature is available.  A linking occurs for annotations that are
 * overlapping, adjacent, or if not immediately adjacent then they must meet the following conditions:
 * 1.  They are closer than the max distance in characters
 * 2. one of the join patterns matches the entire span of the text between the annotations minus trimmed whitespace.
 *
 * Created by thomasginter on 12/22/15.
 */
public class ConceptLinkService {
    /**
     * Maximum distance in characters between annotations to compare. Defaults to 1000.
     */
    protected int maxDistance = 1000;

    /**
     * Maximum number of chars not overlapped by the join pattern in the middle text for the pattern to count as a match.
     * Defaults to 1.
     */
    protected int maxDifference = 1;

    /**
     * Maximum number of items in the linked collection.
     */
    protected int maxCollectionSize = Integer.MAX_VALUE;

    /**
     * Include matches that are separated by only whitespace, defaults to true.
     */
    protected boolean includeWhitespace = true;

    /**
     * Remove covered types that are linked, defaults to false.
     */
    protected boolean removeCovered = false;

    /**
     * Array of patterns that indicate a join expression.
     */
    protected List<Pattern> joinPatterns = null;

    /**
     * Name of the feature in which the list of annotations that are linked will be stored.
     */
    protected String linkedFeatureName = LINKED_FEATURE_NAME;

    /**
     * Name of the feature where the list of "linked" annotations will be stored.
     */
    public static String LINKED_FEATURE_NAME = "linked";
    /**
    public ConceptLinkService(String patternFilePath) throws IOException {
        this(new File(patternFilePath));
    }
     **/

    public ConceptLinkService(File patternFile) throws IOException {
        if(patternFile != null) {
            List<String> expressions = FileUtils.readLines(patternFile);
            joinPatterns = new ArrayList<Pattern>(expressions.size());
            int flags = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
            for(String expression : expressions) {
                if(!expression.startsWith("#"))
                    joinPatterns.add(Pattern.compile(expression, flags));
            }
        }
    }

    public ConceptLinkService(int maxDistance, int maxDifference, int maxCollectionSize, File patternFile) throws IOException {
        this(patternFile);
        this.maxDistance       = maxDistance;
        this.maxDifference     = maxDifference;
        this.maxCollectionSize = maxCollectionSize;
    }

    public ConceptLinkService(int maxDistance, int maxDifference, int maxCollectionSize) {
        this.maxDistance = maxDistance;
        this.maxDifference = maxDifference;
        this.maxCollectionSize = maxCollectionSize;
        joinPatterns = new ArrayList<Pattern>();
    }

    public String getLinkedFeatureName() {
        return linkedFeatureName;
    }

    public ConceptLinkService setLinkedFeatureName(String linkedFeatureName) {
        this.linkedFeatureName = linkedFeatureName;
        return this;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public ConceptLinkService setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public int getMaxDifference() {
        return maxDifference;
    }

    public ConceptLinkService setMaxDifference(int maxDifference) {
        this.maxDifference = maxDifference;
        return this;
    }

    public int getMaxCollectionSize() {
        return maxCollectionSize;
    }

    public ConceptLinkService setMaxCollectionSize(int maxCollectionSize) {
        this.maxCollectionSize = maxCollectionSize;
        return this;
    }

    public boolean isIncludeWhitespace() {
        return includeWhitespace;
    }

    public ConceptLinkService setIncludeWhitespace(boolean includeWhitespace) {
        this.includeWhitespace = includeWhitespace;
        return this;
    }

    public boolean isRemoveCovered() {
        return removeCovered;
    }

    public ConceptLinkService setRemoveCovered(boolean removeCovered) {
        this.removeCovered = removeCovered;
        return this;
    }

    public List<Pattern> getJoinPatterns() {
        return joinPatterns;
    }

    public ConceptLinkService addJoinPattern(Pattern joinPattern) {
        if(joinPattern == null)
            return this;
        if(joinPatterns == null)
            joinPatterns = new ArrayList<Pattern>();
        this.joinPatterns.add(joinPattern);
        return this;
    }

    /**
     * Merge the list of annotations which are proximal.
     *
     * @param linkList
     * @param docText
     * @return
     */
    public List<LinkSpan> linkSpans(List<Annotation> linkList, String docText) {
        ArrayList<LinkSpan> spans = new ArrayList<LinkSpan>();
        ArrayList<Annotation> linkedAnnotations = new ArrayList<Annotation>();
        int curr = 0, next = 1;
        int begin = linkList.get(curr).getBegin(), end = linkList.get(curr).getEnd();
        linkedAnnotations.add(linkList.get(curr)); //Add the first annotation by default
        while(next < linkList.size()) {
            Annotation currAnn = linkList.get(curr);
            Annotation nextAnn = linkList.get(next);

            if(isANextToB(docText, currAnn, nextAnn)
                    && linkedAnnotations.size() < maxCollectionSize) {
                linkedAnnotations.add(nextAnn);
                end = Math.max(currAnn.getEnd(), nextAnn.getEnd());
            } else {
                if(linkedAnnotations.size() > 1) {
                    //Create the output annotation only when there is more than one annotation in the merge list.
                    spans.add(new LinkSpan(begin, end, linkedAnnotations));
                }
                //Reset the linked list to start with the next annotation
                //TODO Research efficiency of clearing the list instead of resetting pointer
                linkedAnnotations = new ArrayList<Annotation>();
                linkedAnnotations.add(nextAnn);
                //reset the indexes
                begin = nextAnn.getBegin();
                end = nextAnn.getEnd();
            }
            //reset the pointers
            curr = next;
            next++;
        }
        //Merged annotations still in the queue, create the span
        if(linkedAnnotations.size() > 1) {
            spans.add(new LinkSpan(begin, end, linkedAnnotations));
        }
        return spans;
    }

    /**
     * Returns true if annotation A is next to annotation B where A precedes B.  Algorithm does not attempt to order
     * the annotations and will return false if B precedes A.  A is next to B if:
     *   1.  A overlaps B
     *   2.  A is separated from B by only whitespace
     *   3.  A is separated from B by whitespace and text that is either completely matched by one of the join patterns
     *       or else one of the join patterns matches a minimum number of characters.
     *
     * @param docText
     * @param a
     * @param b
     * @return
     */
    public boolean isANextToB(String docText, Annotation a, Annotation b) {
        //Get the middle text between annotations and trim whitespace and control chars
        String middle = null;
        int distance = b.getBegin() - a.getEnd();
        if(distance == 0)
            middle = "";
        else if(distance > 0 && distance < maxDistance) {
            middle = docText.substring(a.getEnd(), b.getBegin());
            middle = StringUtils.stripToEmpty(StringUtils.trimToEmpty(middle));
        }

        if(AnnotationLibrarian.overlaps(a, b)
                || (middle != null && ((includeWhitespace && StringUtils.isBlank(middle))
                    || isPatternCompleteMatch(middle))))
            return true;
        else
            return false;
    }

    /**
     * Tests the join patterns on the text found between the annotations.  Returns true if there is a match within the
     * distance requirements.
     *
     * @param text Text found between the annotations
     * @return true if there is a match
     */
    protected boolean isPatternCompleteMatch(String text) {
        for(Pattern pattern : joinPatterns) {
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
                int diff = text.length() - (matcher.end() - matcher.start());
                if(diff <= maxDifference)
                    return true;
            }
        }
        return false;
    }

    /**
     * Add the list of linked types to an FSArray linked feature in the output annotation.
     *
     * @param a output annotation to which the linked type list will be added.
     * @param linkedAnnotationList list of "linked" annotations.
     * @throws CASException if the JCas reference cannot be obtained or there is an error setting the feature.
     */
    public void doSetLinkedTypes(Annotation a, ArrayList<Annotation> linkedAnnotationList) throws CASException {
        ConceptLinkService.doSetLinkedTypes(a, linkedAnnotationList, linkedFeatureName, removeCovered);
    }

    /**
     * Build a sorted list of annotations in the CAS of the types listed.
     *
     * @param jCas CAS from which to retrieve the types
     * @param types names of the annotation types to retrieve
     * @param includeChildren true if child types should also be included
     * @return sorted list of annotation instances
     * @throws AnalysisEngineProcessException
     * @throws CASException
     *
     * TODO Move to Annotation Librarian
     */
    public static ArrayList<Annotation> getSortedList(JCas jCas, String[] types, boolean includeChildren, Predicate<Annotation>...predicates)
            throws AnalysisEngineProcessException, CASException {
        ArrayList<Annotation> list = new ArrayList<Annotation>();
        Collection<Annotation> temp = null;
        /**
         * For each input type remove covered annotations, add them to the overall list, then resort the list.
         */
        for(int i = 0; i < types.length; i++) {
            AnnotationLibrarian.removeCoveredAnnotations(jCas, types[i], includeChildren, null);
            temp = AnnotationLibrarian.getAllAnnotationsOfType(jCas, types[i], includeChildren);
            //Filter the list using provided predicates
            if(predicates != null) {
                for (Predicate<Annotation> predicate : predicates)
                    temp.stream().filter(predicate).collect(Collectors.toList());
            }
            list.addAll(temp);
        }
        Collections.sort(list, new AnnotationComparator());

        return list;
    }

    /**
     * Add the list of linked types to an FSArray linked feature in the output annotation.
     *
     * @param a output annotation to which the linked type list will be added
     * @param linkedAnnotationList list of "linked" annotations
     * @param linkedFeatureName Name of the linked feature which will store a list of references to linked annotations
     * @param removeCovered Remove the covered annotations
     * @throws CASException if the JCas reference cannot be obtained or there is an error setting the feature
     */
    public static void doSetLinkedTypes(Annotation a, ArrayList<Annotation> linkedAnnotationList, String linkedFeatureName, boolean removeCovered) throws CASException {
        Feature linkedFeatureObj = a.getType().getFeatureByBaseName(linkedFeatureName);
        //No linked feature so quit
        if(linkedFeatureObj == null)
            return;

        //Create a new FSArray
        FSArray linkedFSArray = new FSArray(a.getCAS().getJCas(), linkedAnnotationList.size());
        linkedFSArray.copyFromArray(linkedAnnotationList.toArray(new Annotation[linkedAnnotationList.size()]), 0, 0, linkedAnnotationList.size());

        //Add the new FSArray to the output annotation linked feature
        a.setFeatureValue(linkedFeatureObj, linkedFSArray);

        if(removeCovered) {
            for(Annotation annotation: linkedAnnotationList) {
                annotation.removeFromIndexes();
            }
        }
    }

    /**
     * Span used for tracking merges.
     */
    public class LinkSpan {
        protected int start;
        protected int end;
        protected ArrayList<Annotation> linked = new ArrayList<Annotation>();

        public LinkSpan(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public LinkSpan(int start, int end, List<Annotation> linked) {
            this.start = start;
            this.end = end;
            this.linked = (ArrayList<Annotation>) linked;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public ArrayList<Annotation> getLinked() {
            return linked;
        }

        public void addLinkAnnotation(Annotation annotation) {
            linked.add(annotation);
        }

        public void setLinked(ArrayList<Annotation> linked) {
            this.linked = linked;
        }
    }
}
