package gov.va.vinci.leo.bladder.cr;

import gov.va.vinci.leo.cr.BaseLeoCollectionReader;
import gov.va.vinci.leo.tools.ConfigurationParameterImpl;
import gov.va.vinci.leo.tools.LeoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This reader populates a specified number of CAS objects with random strings.  Useful primarily as an example of the
 * implementation of the BaseLeoCollectionReader API.
 *
 * User: Thomas Ginter
 * Date: 10/27/14
 * Time: 09:15
 */
public class RandomStringCollectionReader extends BaseLeoCollectionReader {

    /**
     * Number of strings produced so far.
     */
    protected int currentString = 0;
    /**
     * Number of strings that the reader will create.
     */
    protected int numberOfStrings = 0;
    /**
     * Generates a random length for each string.
     */
    protected Random random = new Random(System.currentTimeMillis());
    /**
     * Maximum length of the generated string.
     */
    protected static final int MAX_STRING_LENGTH = 1024;

    public RandomStringCollectionReader(int numberOfStrings) {
        this.numberOfStrings = numberOfStrings;
    }

    /**
     * @param aCAS the CAS to populate with the next document;
     * @throws org.apache.uima.collection.CollectionException if there is a problem getting the next and populating the CAS.
     * @see gov.va.vinci.leo.cr.LeoCollectionReaderInterface  getNext(org.apache.uima.cas.CAS)
     */
    @Override
    public void getNext(CAS aCAS) throws CollectionException, IOException {
        int length = random.nextInt(MAX_STRING_LENGTH);
        aCAS.setDocumentText(RandomStringUtils.randomAlphanumeric(length));
        currentString++;
    }

    /**
     * @return true if and only if there are more elements available from this CollectionReader.
     * @throws java.io.IOException
     * @throws org.apache.uima.collection.CollectionException
     * @see gov.va.vinci.leo.cr.LeoCollectionReaderInterface
     */
    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return currentString < numberOfStrings;
    }

    /**
     * Gets information about the number of entities and/or amount of data that has been read from
     * this <code>CollectionReader</code>, and the total amount that remains (if that information
     * is available).
     * <p/>
     * This method returns an array of <code>Progress</code> objects so that results can be reported
     * using different units. For example, the CollectionReader could report progress in terms of the
     * number of documents that have been read and also in terms of the number of bytes that have been
     * read. In many cases, it will be sufficient to return just one <code>Progress</code> object.
     *
     * @return an array of <code>Progress</code> objects. Each object may have different units (for
     * example number of entities or bytes).
     */

    public Progress[] getProgress() {
        return new Progress[]{ new ProgressImpl(currentString, numberOfStrings, Progress.ENTITIES) };
    }

    /**
     * Generate the UIMA Collection reader with resources.
     *
     * @return a uima collection reader.
     * @throws org.apache.uima.resource.ResourceInitializationException
     */

    public CollectionReader produceCollectionReader() throws ResourceInitializationException {
        Map<String, Object> parameterValues = new HashMap<String, Object>();
        parameterValues.put(Param.NUMBER_OF_STRINGS.getName(), numberOfStrings);
        return produceCollectionReader(LeoUtils.getStaticConfigurationParameters(Param.class), parameterValues);
    }

    /**
     * Static inner class for holding parameter information.
     */
    public static class Param extends BaseLeoCollectionReader.Param {
        /**
         * Input directory to read from.
         */
        public static ConfigurationParameter NUMBER_OF_STRINGS =
                new ConfigurationParameterImpl("numberOfStrings", "The number of random strings to generate",
                        ConfigurationParameter.TYPE_INTEGER, true, false, new String[]{});
    }
}
