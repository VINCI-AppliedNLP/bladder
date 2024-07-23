package gov.va.vinci.leo.bladder.listeners;

import com.google.gson.JsonSyntaxException;
import gov.va.vinci.leo.aucompare.comparators.AuComparator;
import gov.va.vinci.leo.aucompare.listener.AuCompareCSVListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by thomasginter on 2/17/16.
 */
public class AuBladderCSVListener extends AuCompareCSVListener {
    /**
     * Constructor with a Writer and one or more comparators.
     *
     * @param file        The printwriter to write to.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     */
    public AuBladderCSVListener(File file, AuComparator... comparators) throws FileNotFoundException {
        super(file, comparators);
    }

    /**
     * Constructor with a Writer and a list of comparators.
     *
     * @param file
     * @param comparators list of comparators to use for the comparison
     * @throws FileNotFoundException
     */
    public AuBladderCSVListener(File file, List<AuComparator> comparators) throws FileNotFoundException {
        super(file, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator.
     *
     * @param file        The printwriter to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException               if the file is not found or can't be written to.
     * @throws JsonSyntaxException if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(File file, char separator, AuComparator... comparators) throws FileNotFoundException {
        super(file, separator, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator and quote character.
     *
     * @param file        The printwriter to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(File file, char separator, char quotechar, AuComparator... comparators) throws FileNotFoundException {
        super(file, separator, quotechar, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator, quote character, and escape character.
     *
     * @param file        The printwriter to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param escapechar  the character to escape quotechar in values.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(File file, char separator, char quotechar, char escapechar, AuComparator... comparators) throws FileNotFoundException {
        super(file, separator, quotechar, escapechar, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator, quote character, escape character, and line ending.
     *
     * @param file        The printwriter to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param escapechar  the character to escape quotechar in values.
     * @param lineend     the string to use as line endings.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(File file, char separator, char quotechar, char escapechar, String lineend, AuComparator... comparators) throws FileNotFoundException {
        super(file, separator, quotechar, escapechar, lineend, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator, quote character,  and line ending.
     *
     * @param file        The printwriter to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param lineend     the string to use as line endings.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(File file, char separator, char quotechar, String lineend, AuComparator... comparators) throws FileNotFoundException {
        super(file, separator, quotechar, lineend, comparators);
    }

    /**
     * Constructor with a Writer.
     *
     * @param stream      The stream to write to.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(OutputStream stream, AuComparator... comparators) throws FileNotFoundException {
        super(stream, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator.
     *
     * @param stream      The stream to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(OutputStream stream, char separator, AuComparator... comparators) throws FileNotFoundException {
        super(stream, separator, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator and quote character.
     *
     * @param stream      The stream to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(OutputStream stream, char separator, char quotechar, AuComparator... comparators) throws FileNotFoundException {
        super(stream, separator, quotechar, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator, quote character, and escape character.
     *
     * @param stream      The stream to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param escapechar  the character to escape quotechar in values.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(OutputStream stream, char separator, char quotechar, char escapechar, AuComparator... comparators) throws FileNotFoundException {
        super(stream, separator, quotechar, escapechar, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator, quote character, escape character, and line ending.
     *
     * @param stream      The stream to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param escapechar  the character to escape quotechar in values.
     * @param lineend     the string to use as line endings.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(OutputStream stream, char separator, char quotechar, char escapechar, String lineend, AuComparator... comparators) throws FileNotFoundException {
        super(stream, separator, quotechar, escapechar, lineend, comparators);
    }

    /**
     * Constructor with a Writer, specifying a separator, quote character,  and line ending.
     *
     * @param stream      The stream to write to.
     * @param separator   the character to use as a separator in the csv.
     * @param quotechar   the character to quote values with.
     * @param lineend     the string to use as line endings.
     * @param comparators one or more comparators to use for the comparison
     * @throws FileNotFoundException if the file is not found or can't be written to.
     * @throws JsonSyntaxException   if the comparators is not a valid JSON string.
     */
    public AuBladderCSVListener(OutputStream stream, char separator, char quotechar, String lineend, AuComparator... comparators) throws FileNotFoundException {
        super(stream, separator, quotechar, lineend, comparators);
    }

    /**
     * Returns the string list of the headers for a row.
     * ie ("col1", "id", "myValue");
     *
     * @return the string list of the headers for a row.
     */
    @Override
    protected String[] getHeaders() {
        return new String[]{"documentID", "mapping", "begin", "end", "type", "coveredText", "statClass", "Value", "ValueText"};
    }
}
