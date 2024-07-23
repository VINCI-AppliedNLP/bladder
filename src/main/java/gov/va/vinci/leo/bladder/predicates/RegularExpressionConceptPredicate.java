package gov.va.vinci.leo.bladder.predicates;

import gov.va.vinci.leo.regex.types.RegularExpressionType;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.function.Predicate;

/**
 * Created by thomasginter on 2/10/16.
 */
public class RegularExpressionConceptPredicate implements Predicate<Annotation> {

    protected String conceptValue = "";

    public RegularExpressionConceptPredicate() {

    }

    public RegularExpressionConceptPredicate(String conceptValue) {
        this.conceptValue = conceptValue;
    }

    public String getConceptValue() {
        return conceptValue;
    }

    public RegularExpressionConceptPredicate setConceptValue(String conceptValue) {
        this.conceptValue = conceptValue;
        return this;
    }

    /**
     * Returns true if this is a regular expression annotation whose concept value matches the concept value set in the
     * object.
     *
     * @param annotation the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    public boolean test(Annotation annotation) {
        if(annotation instanceof RegularExpressionType) {
            return equals(((RegularExpressionType) annotation).getConcept());
        }
        return false;
    }

    /**
     * Return true if the value is "equal" to the conceptValue set in this object.  Extending classes should override
     * this method in order to enforce some other method of comparison.
     *
     * @param value Value to compare
     * @return true if the value is "equal", false otherwise
     */
    protected boolean equals(String value) {
        return conceptValue.equals(value);
    }
}
