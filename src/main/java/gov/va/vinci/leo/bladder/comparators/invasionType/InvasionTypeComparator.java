package gov.va.vinci.leo.bladder.comparators.invasionType;

import gov.va.vinci.leo.bladder.comparators.BaseValueComparator;
import gov.va.vinci.leo.bladder.types.InvasionType;

/**
 * Created by thomasginter on 1/26/16.
 */
public class InvasionTypeComparator extends BaseValueComparator<InvasionType> {

    @Override
    public int getValue(InvasionType a) {
        String concept = a.getConcept();
        if("none".equals(concept))
            return 1;
        if("suspected".equals(concept))
            return 2;
        if("invasive".equals(concept))
            return 3;

        return 0;
    }

    /**
     * Return the value string that corresponds with the hierarchy value provided.
     *
     * @param a annotation whose relative hierarchical value whose corresponding string will be returned
     * @return value string
     */
    @Override
    public String getValueString(InvasionType a) {
        return getInvasionTypeString(getValue(a));
    }

    /**
     * InvasionType string based on hierarchichal value.  Static method so the flat comparator can use the same method.
     *
     * @param val hierarchical level value
     * @return value string
     */
    public static String getInvasionTypeString(int val) {
        String levelString = "not stated";
        switch (val) {
            case 1:
                levelString = "None";
                break;
            case 2:
                levelString = "suspected";
                break;
            case 3:
                levelString = "Invasive";
                break;
        }
        return levelString;
    }
}
