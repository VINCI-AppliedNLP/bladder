package gov.va.vinci.leo.bladder.comparators.au;

import gov.va.vinci.leo.aucompare.comparators.BaseAuComparator;
import gov.va.vinci.leo.aucompare.tools.AuStats;
import gov.va.vinci.leo.bladder.tools.AuBladderStats;
import gov.va.vinci.leo.tools.LeoUtils;
import groovy.lang.MissingPropertyException;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by thomasginter on 1/28/16.
 */
public class FeatureAuComparator extends BaseAuComparator {

    protected String featureName = "";

    /**
     * Log messages
     */
    protected static Logger log = Logger.getLogger(LeoUtils.getRuntimeClass());

    /**
     * Initialize to a map of Gold annotation types to system annotation types.
     *
     * @param types    list of Au Annotation -> System Annotation types.
     * @param keepRows if true then the comparison results will be stored.
     */
    public FeatureAuComparator(Map<String, String> types, boolean keepRows) {
        super(types, keepRows);
    }

    public String getFeatureName() {
        return featureName;
    }

    public FeatureAuComparator setFeatureName(String featureName) {
        this.featureName = featureName;
        return this;
    }

    /**
     * Set the map of gold to system annotation types.
     *
     * @param types    types map to set.
     * @param keepRows
     * @return Reference to this comparator.
     */
    @Override
    public <T extends BaseAuComparator> T setTypes(Map<String, String> types, boolean keepRows) {
        statsList = new ArrayList<AuStats>(types.size());
        for (String au : types.keySet()) {
            String tool = types.get(au);
            statsList.add(new AuBladderStats(au, tool, keepRows));
        }
        return (T) this;
    }

    /**
     * Add an annotation mapping from a gold annotation to a tool annotation to the annotation types map.
     *
     * @param auAnnotation   gold annotation type to compare.
     * @param toolAnnotation system annotation type to compare.
     * @return Reference to this comparator.
     */
    @Override
    public <T extends BaseAuComparator> T addTypeMapping(String auAnnotation, String toolAnnotation) {
        statsList.add(new AuBladderStats(auAnnotation, toolAnnotation));
        return (T) this;
    }

    /**
     * Indicate whether there is no match, an overlap match, or an exact match as follows:
     * {@code
     * -1 = no match
     * 0 = exact match
     * 1 = overlap match
     * }
     *
     * @param a1 first annotation
     * @param a2 second annotation
     * @return none, overlap, exact match
     */
    @Override
    public int compare(Annotation a1, Annotation a2) {
        if(a1 == null || a2 == null)
            throw new NullArgumentException("Both a1 and a2 must have a value!");
        if(StringUtils.isBlank(featureName))
            throw new MissingPropertyException("featurName must be set!", featureName, FeatureAuComparator.class);

        //Get the feature object and values from each annotation, then compare them
        Feature feature = a1.getType().getFeatureByBaseName(featureName);
        if(feature == null) {
            log.error("Missing feature " + featureName + " in " + a1.getClass().getCanonicalName());
            return -1;
        }

        try {
            String s1 = a1.getFeatureValueAsString(feature);
            String s2 = a2.getFeatureValueAsString(feature);
            return (s1.equals(s2))? 0 : (s1.equalsIgnoreCase(s2))? 1 : -1;
        } catch (CASRuntimeException cre) {
            log.error("Exception thrown extracting feature string of " + featureName + " from the annotation.", cre);
            return -1;
        }
    }
}
