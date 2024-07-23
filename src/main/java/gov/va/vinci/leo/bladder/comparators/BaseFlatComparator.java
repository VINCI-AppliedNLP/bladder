package gov.va.vinci.leo.bladder.comparators;

import gov.va.vinci.leo.flat.types.Flat;
import groovy.lang.MissingPropertyException;
import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by thomasginter on 1/26/16.
 */
public abstract class BaseFlatComparator extends BaseValueComparator<Flat> {

    /**
     * Extending classes need to set this value to the variable name they are getting.
     */
    protected String featureName = "";

    protected int featureIndex = -1;

    protected int optionIndex = -1;

    protected int incorrectIndex = -1;

    protected int coveredTextIndex = -1;

    /**
     * Column Names
     */
    public static final String FEATURE = "FeatureName";
    public static final String OPTION = "OptionName";
    public static final String INCORRECT = "Incorrect";
    public static final String COVERED_TEXT = "CoveredText";

    public BaseFlatComparator() {
        /** No init needed **/
    }

    /**
     * Return the relative hierarchichal level for this variable based on the metadata in the annotation provided.
     *
     * @param a annotation to check
     * @return
     */
    @Override
    public int getValue(Flat a) {
        getIndexes(a);
        if(getFeatureValue(a).equalsIgnoreCase(featureName) &&
                StringUtils.isBlank(getIncorrectValue(a))) {
            return getValue(getOptionValue(a));
        }
        return 0;
    }

    protected abstract int getValue(String val);

    protected void getIndexes(Flat flat) {
        if(featureIndex != -1
                && optionIndex != -1
                && incorrectIndex != -1
                && coveredTextIndex != -1)
            return;
        ListOrderedSet keySet = ListOrderedSet.decorate(Arrays.asList(flat.getKeys().toStringArray()));
        featureIndex = keySet.indexOf(FEATURE);
        optionIndex = keySet.indexOf(OPTION);
        incorrectIndex = keySet.indexOf(INCORRECT);
        coveredTextIndex = keySet.indexOf(COVERED_TEXT);
    }

    protected String getFeatureValue(Flat flat) {
        if(featureIndex == -1)
            throw new MissingPropertyException("featureIndex is not set!");
        return flat.getValues(featureIndex);
    }

    protected String getOptionValue(Flat flat) {
        if(optionIndex == -1)
            throw new MissingPropertyException("optionIndex is not set!");
        return flat.getValues(optionIndex);
    }

    protected String getIncorrectValue(Flat flat) {
        if(incorrectIndex == -1)
            throw new MissingPropertyException("incorrectIndex is not set!");
        return flat.getValues(incorrectIndex);
    }

    public String getCoveredText(Flat flat) {
        if(coveredTextIndex == -1)
            getIndexes(flat);
        return flat.getValues(coveredTextIndex);
    }

}
