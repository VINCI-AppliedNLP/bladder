package gov.va.vinci.leo.bladder.comparators.doi;

import gov.va.vinci.leo.bladder.comparators.BaseFlatComparator;
import gov.va.vinci.leo.flat.types.Flat;

/**
 * Created by thomasginter on 2/16/16.
 */
public class DoiFlatComparator extends BaseFlatComparator{

    public DoiFlatComparator() {
        this.featureName = "Invasion Depth";
    }

    @Override
    protected int getValue(String val) {
        if("Not Stated".equals(val))
            return 1;
        if("Superficial".equals(val))
            return 2;
        if("Muscle".equals(val))
            return 3;
        if("Perivesical".equals(val))
            return 4;
        if("Other Organ".equals(val))
            return 5;
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
        return DoiComparator.getDoiString(getValue(a));
    }
}
