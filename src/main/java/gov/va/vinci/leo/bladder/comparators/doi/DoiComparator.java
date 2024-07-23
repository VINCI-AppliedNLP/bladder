package gov.va.vinci.leo.bladder.comparators.doi;

import gov.va.vinci.leo.bladder.comparators.BaseValueComparator;
import gov.va.vinci.leo.bladder.types.Doi;
import gov.va.vinci.leo.bladder.types.DoiBase;
import gov.va.vinci.leo.bladder.types.DoiType;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Created by thomasginter on 2/11/16.
 */
public class DoiComparator extends BaseValueComparator<Doi> {
    /**
     * Return the relative hierarchichal level for this variable based on the metadata in the annotation provided.
     *
     * @param a annotation whose value will be retrieved
     * @return heirarchichal level
     */
    @Override
    public int getValue(Doi a) {
        String value = a.getValue();
        if(StringUtils.isNotBlank(value))
            return getValue(value);
        //Find the highest value from all DoiType annotations in the list
        int max = 0;
        FSArray linked = a.getLinked();
        for(int i = 0; i < linked.size(); i++) {
            Annotation link = (Annotation) linked.get(i);
            if(link instanceof DoiType) {
                int val = getValue((DoiType) link);
                max = Math.max(max, val);
            }
        }
        return max;
    }

    protected int getValue(DoiType doiType) {
        return getValue(doiType.getConcept());
    }

    protected int getValue(String value) {
        if("not_stated".equals(value))
            return 1;
        if("superficial".equals(value))
            return 2;
        if("muscle".equals(value))
            return 3;
        if("perivesical".equals(value))
            return 4;
        if("other_organ".equals(value))
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
    public String getValueString(Doi a) {
        return getDoiString(getValue(a));
    }

    public static String getDoiString(int val) {
        String levelString = "none";
        switch (val) {
            case 1:
                levelString = "not_stated";
                break;
            case 2:
                levelString = "superficial";
                break;
            case 3:
                levelString = "muscle";
                break;
            case 4:
                levelString = "perivesical";
                break;
            case 5:
                levelString = "other_organ";
                break;
        }
        return levelString;
    }
}
