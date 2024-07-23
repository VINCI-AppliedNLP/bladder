package gov.va.vinci.leo.bladder.comparators.grade;

import gov.va.vinci.leo.bladder.comparators.BaseValueComparator;
import gov.va.vinci.leo.bladder.types.Grade;

/**
 * Created by thomasginter on 1/26/16.
 */
public class GradeComparator extends BaseValueComparator<Grade> {

    @Override
    public int getValue(Grade a) {
        String concept = a.getConcept();
        if("g1".equals(concept))
            return 1;
        if("g2".equals(concept))
            return 2;
        if("g3".equals(concept))
            return 3;
        if("g4".equals(concept))
            return 4;
        return 0;
    }

    /**
     * Return the value string that corresponds with the hierarchy value provided.
     *
     * @param a annotation whose relative hierarchical value whose corresponding string will be returned
     * @return value string
     */
    @Override
    public String getValueString(Grade a) {
        return getGradeString(getValue(a));
    }

    /**
     * Grade string based on hierarchichal value.  Static method so the flat comparator can use the same method.
     *
     * @param val hierarchical level value
     * @return value string
     */
    public static String getGradeString(int val) {
        String levelString = "not stated";
        switch (val) {
            case 1:
                levelString = "low";
                break;
            case 2:
                levelString = "moderate";
                break;
            case 3:
                levelString = "high";
                break;
            case 4:
                levelString = "undifferentiated";
                break;
        }
        return levelString;
    }
}
