package gov.va.vinci.leo.bladder.tools;

import gov.va.vinci.leo.aucompare.tools.AuStats;
import gov.va.vinci.leo.bladder.ae.DocumentValue;
import gov.va.vinci.leo.bladder.comparators.BaseFlatComparator;
import gov.va.vinci.leo.bladder.comparators.doi.DoiFlatComparator;
import gov.va.vinci.leo.bladder.types.DocVariable;
import gov.va.vinci.leo.flat.types.Flat;
import gov.va.vinci.leo.tools.LeoUtils;
import org.apache.log4j.Logger;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Created by thomasginter on 2/17/16.
 */
public class AuBladderStats extends AuStats {

    /**
     * Logging object of output
     */
    private static final Logger log = Logger.getLogger(LeoUtils.getRuntimeClass().toString());

    /**
     * Default constructor, initialize annotation names to empty strings.
     */
    public AuBladderStats() {
    }

    /**
     * Initialize the gold and tool annotation names.
     *
     * @param auAnnotation   the canonical name of the gold annotation
     * @param toolAnnotation the canonical name of the tool annotation
     */
    public AuBladderStats(String auAnnotation, String toolAnnotation) {
        super(auAnnotation, toolAnnotation);
    }

    /**
     * Initialize the gold and tool annotation names.  Also indicate if the stats object should store the row output for
     * later retrieval.  Rows are output as the annotation information and the class type, i.e. True Positive. Exact
     * matches are only output as an exact match and not also as an overlap match though they are counted as overlap in
     * the statistics.
     *
     * @param auAnnotation   the canonical name of the gold annotation
     * @param toolAnnotation the canonical name of the tool annotation
     * @param keepRows       if true then row output is stored for later retrieval
     */
    public AuBladderStats(String auAnnotation, String toolAnnotation, boolean keepRows) {
        super(auAnnotation, toolAnnotation, keepRows);
    }

    /**
     * Add the annotation information rows for each annotation and stats class listed.  The rows are in the format of:
     * documentID, begin, end, annotation type, coveredText, statClass.
     *
     * @param classList      list of statsClasses returned by the AuStats object for this Annotation(s)
     * @param documentID     document ID from which the annotations have come
     * @param rowAnnotations one or more Annotations whose action resulted in the stats class list
     */
    @Override
    protected void addRows(String[] classList, String documentID, Annotation... rowAnnotations) {
        if (rows == null) {
            return;
        }
        if (rowAnnotations == null) {
            throw new IllegalArgumentException("Missing required parameter rowAnnotations, at least one Annotation must be provided!");
        }
        if (classList == null || classList.length == 0) {
            throw new IllegalArgumentException("Missing required parameter classList!");
        }
        String begin, end, type, value, valueText, coveredText = null, mapping = auAnnotation + "->" + toolAnnotation;
        for (Annotation a : rowAnnotations) {
            begin = "" + a.getBegin();
            end = "" + a.getEnd();
            type = a.getType().getName();
            value = "";
            valueText = "";
            if(DocVariable.class.isAssignableFrom(a.getClass())) {
                value = ((DocVariable) a).getValue();
                Annotation src = ((DocVariable)a).getSrc();
                if(src.getClass().isAssignableFrom(Flat.class))
                    valueText = new DoiFlatComparator().getCoveredText((Flat) src);
                else
                    valueText = src.getCoveredText();
            }
            try {
                coveredText = a.getCoveredText();
            } catch (StringIndexOutOfBoundsException e) {
                coveredText = "";
                log.error("Exception thrown getting the covered text, documentID: " + documentID
                        + ", Annotation.begin: " + a.getBegin()
                        + ", Annotation.end: " + a.getEnd(), e);
            }
            for (String statClass : classList) {
                rows.add(new String[]{documentID, mapping, begin, end, type, coveredText, statClass, value, valueText});
            }
        }
    }
}
