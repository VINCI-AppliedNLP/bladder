package gov.va.vinci.leo.bladder.comparators.invasionType;

import gov.va.vinci.leo.bladder.comparators.BaseFlatComparator;
import gov.va.vinci.leo.bladder.comparators.grade.GradeComparator;
import gov.va.vinci.leo.flat.types.Flat;

/**
 * Created by thomasginter on 1/26/16.
 */
public class InvasionTypeFlatComparator extends BaseFlatComparator {

    public InvasionTypeFlatComparator() {
        this.featureName = "Invasion Type";
    }

    @Override
    protected int getValue(String val) {
        if(val.contains("None"))
            return 1;
        if(val.contains("Suspected"))
            return 2;
        if(val.contains("Invasive"))
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
    public String getValueString(Flat a) {
        //return GradeComparator.getGradeString(getValue(a));
        return InvasionTypeComparator.getInvasionTypeString(getValue(a));
    }
}
