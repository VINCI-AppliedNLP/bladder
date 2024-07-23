package gov.va.vinci.leo.bladder.comparators.grade;

import gov.va.vinci.leo.bladder.comparators.BaseFlatComparator;
import gov.va.vinci.leo.flat.types.Flat;

/**
 * Created by thomasginter on 1/26/16.
 */
public class GradeFlatComparator extends BaseFlatComparator {

    public GradeFlatComparator() {
        this.featureName = "Grade";
    }

    @Override
    protected int getValue(String val) {
        if(val.contains("1"))
            return 1;
        if(val.contains("2"))
            return 2;
        if(val.contains("3"))
            return 3;
        if(val.contains("4"))
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
    public String getValueString(Flat a) {
        return GradeComparator.getGradeString(getValue(a));
    }
}
