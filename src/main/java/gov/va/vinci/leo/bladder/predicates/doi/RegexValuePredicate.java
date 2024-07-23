package gov.va.vinci.leo.bladder.predicates.doi;

import gov.va.vinci.leo.bladder.predicates.RegularExpressionConceptPredicate;
import gov.va.vinci.leo.bladder.types.DoiType;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Created by thomasginter on 2/10/16.
 */
public class RegexValuePredicate extends RegularExpressionConceptPredicate {

    public RegexValuePredicate() {
        this.conceptValue = "other_organ";
    }

    /**
     * Checks DoiType annotations for other_organ types, other annotations are preserved.
     *
     * @param annotation the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Annotation annotation) {
        if(annotation instanceof DoiType) {
            return equals(((DoiType)annotation).getConcept());
        }
        return true;
    }
}
