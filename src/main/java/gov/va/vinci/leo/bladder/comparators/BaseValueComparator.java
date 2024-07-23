package gov.va.vinci.leo.bladder.comparators;

import java.util.Comparator;

/**
 * Created by thomasginter on 1/25/16.
 */
public abstract class BaseValueComparator<T> implements Comparator<T> {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     * <p/>
     * In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.<p>
     * <p/>
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     * <p/>
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     * <p/>
     * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     * <p/>
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException if an argument is null and this
     *                              comparator does not permit null arguments
     * @throws ClassCastException   if the arguments' types prevent them from
     *                              being compared by this comparator.
     */

    public int compare(T o1, T o2) {
        if(o1 == null || o2 == null)
            throw new NullPointerException("Paramters cannot be null!");
        //Get the values and compare them
        int v1 = getValue(o1), v2 = getValue(o2);
        return new Integer(v1).compareTo(new Integer(v2));
    }

    /**
     * Return the relative hierarchichal level for this variable based on the metadata in the annotation provided.
     *
     * @param a annotation whose value will be retrieved
     * @return heirarchichal level
     */
    public abstract int getValue(T a);

    /**
     * Return the value string that corresponds with the hierarchy value provided.
     *
     * @param a annotation whose relative hierarchical value whose corresponding string will be returned
     * @return value string
     */
    public abstract String getValueString(T a);

}
