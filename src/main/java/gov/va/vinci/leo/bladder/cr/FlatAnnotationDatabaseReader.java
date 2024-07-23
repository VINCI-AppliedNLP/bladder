package gov.va.vinci.leo.bladder.cr;

import gov.va.vinci.leo.cr.BaseDatabaseCollectionReader;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.descriptors.TypeDescriptionBuilder;
import gov.va.vinci.leo.flat.types.Flat;
import gov.va.vinci.leo.model.DatabaseConnectionInformation;
import gov.va.vinci.leo.tools.ConfigurationParameterImpl;
import gov.va.vinci.leo.tools.LeoUtils;
import gov.va.vinci.leo.tools.db.LeoArrayListHandler;
import gov.va.vinci.leo.types.CSI;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.resource.metadata.impl.ConfigurationParameter_impl;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Picks up documents from one table and annotations in a flat format in another table.
 * <p>
 * Created by thomasginter on 1/7/16.
 */
public class FlatAnnotationDatabaseReader extends BaseDatabaseCollectionReader {
  protected String noteQuery = null;

  protected String noteIdField = null;

  protected String noteTextField = null;

  protected String annotationQuery = null;

  protected String annotationStartField = null;

  protected String annotationEndField = null;

  protected int noteIdIndex = -1;

  protected int noteTextIndex = -1;

  protected Connection connection = null;

  /**
   * Log file handler.
   */
  private final static Logger logger = Logger.getLogger(LeoUtils.getRuntimeClass().toString());

  public FlatAnnotationDatabaseReader() {
    super();
  }

  public FlatAnnotationDatabaseReader(DatabaseConnectionInformation databaseConnectionInformation) {
    super(databaseConnectionInformation);
  }

  public FlatAnnotationDatabaseReader(String driver, String url, String username, String password) {
    super(driver, url, username, password);
  }

  public String getNoteQuery() {
    return noteQuery;
  }

  public FlatAnnotationDatabaseReader setNoteQuery(String noteQuery) {
    this.noteQuery = noteQuery;
    return this;
  }

  public String getNoteIdField() {
    return noteIdField;
  }

  public FlatAnnotationDatabaseReader setNoteIdField(String noteIdField) {
    this.noteIdField = noteIdField;
    return this;
  }

  public String getNoteTextField() {
    return noteTextField;
  }

  public FlatAnnotationDatabaseReader setNoteTextField(String noteTextField) {
    this.noteTextField = noteTextField;
    return this;
  }

  public String getAnnotationQuery() {
    return annotationQuery;
  }

  public FlatAnnotationDatabaseReader setAnnotationQuery(String annotationQuery) {
    this.annotationQuery = annotationQuery;
    return this;
  }

  public String getAnnotationStartField() {
    return annotationStartField;
  }

  public FlatAnnotationDatabaseReader setAnnotationStartField(String annotationStartField) {
    this.annotationStartField = annotationStartField;
    return this;
  }

  public String getAnnotationEndField() {
    return annotationEndField;
  }

  public FlatAnnotationDatabaseReader setAnnotationEndField(String annotationEndField) {
    this.annotationEndField = annotationEndField;
    return this;
  }

  /**
   * This method is called during initialization. Subclasses should override it to perform one-time startup logic.
   *
   * @throws ResourceInitializationException if a failure occurs during initialization.
   */
  @Override
  public void initialize() throws ResourceInitializationException {
    super.initialize();
    this.noteQuery = (String) getConfigParameterValue(Param.NOTE_QUERY.getName());
    this.noteIdField = (String) getConfigParameterValue(Param.NOTE_ID_FIELD.getName());
    this.noteTextField = (String) getConfigParameterValue(Param.NOTE_TEXT_FIELD.getName());
    this.annotationQuery = (String) getConfigParameterValue(Param.ANNOTATION_QUERY.getName());
    this.annotationStartField = (String) getConfigParameterValue(Param.ANNOTATION_START_FIELD.getName());
    this.annotationEndField = (String) getConfigParameterValue(Param.ANNOTATION_END_FIELD.getName());
    try {
      dataManager = getDataManager();
      connection = dataManager.getDataSource().getConnection();
    } catch (Exception e) {
      throw new ResourceInitializationException(e);
    }
  }

  /**
   * @param aCAS the CAS to populate with the next document;
   * @throws CollectionException if there is a problem getting the next and populating the CAS.
   */
  @Override
  public void getNext(CAS aCAS) throws CollectionException, IOException {
    JCas jCas;
    try {
      jCas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    Object[] row = mRecordList.get(mRowIndex++);
    jCas.setDocumentText(LeoUtils.filterText((String) row[noteTextIndex], filters));
    CSI csi = new CSI(jCas);
    csi.setBegin(0);
    csi.setEnd(jCas.getDocumentText().length());
    csi.setID(row[noteIdIndex].toString());
    csi.setLocator(noteQuery);
    //Set the row data
    StringArray rowArray = new StringArray(jCas, row.length);
    String item;
    for (int index = 0; index < row.length; index++) {
      if (index == noteTextIndex) {
        item = null;
      } else {
        if (row[index] != null)
          item = row[index].toString();
        else
          item = "";
      }
      rowArray.set(index, item);
    }
    csi.setRowData(rowArray);
    csi.addToIndexes();

    //Add the annotations to the document
    setAnnotations(jCas, row[noteIdIndex].toString());
  }

  protected void setAnnotations(JCas jCas, String noteId) throws CollectionException {
    try {
      //Fill in the note ID to the query to pull annotations.
      LeoArrayListHandler handler = new LeoArrayListHandler();
      PreparedStatement ps = connection.prepareStatement(annotationQuery);
      ps.setString(1, noteId);
      //Execute the query and pull the annotations.
      List<Object[]> annotationRows = handler.handle(ps.executeQuery());
      int startColumnIndex = handler.getColumnIndex(annotationStartField);
      int endColumnIndex = handler.getColumnIndex(annotationEndField);
      for (Object[] row : annotationRows) {
        Flat flat = new Flat(jCas, new Integer(row[startColumnIndex].toString()), new Integer(row[endColumnIndex].toString()));
        if (row.length > 2) {
          //If there are other columns in the query then add keys and values to the annotation
          ResultSetMetaData rsmd = handler.getResultSetMetaData();
          StringArray keysArray = new StringArray(jCas, rsmd.getColumnCount());
          StringArray valuesArray = new StringArray(jCas, rsmd.getColumnCount());
          for (int i = 0; i < rsmd.getColumnCount(); i++) {
            keysArray.set(i, rsmd.getColumnName(i + 1));
            if (row[i] == null)
              valuesArray.set(i, "");
            else
              valuesArray.set(i, row[i].toString());
          }
          flat.setKeys(keysArray);
          flat.setValues(valuesArray);
        }
        flat.addToIndexes();
      }
    } catch (SQLException e) {
      throw new CollectionException(e);
    }
  }

  /**
   * @return true if and only if there are more elements available from this CollectionReader.
   * @throws IOException
   * @throws CollectionException
   */
  @Override
  public boolean hasNext() throws IOException, CollectionException {
    if (mRecordList == null || mRowIndex < 0) {
      //Execute the query to get the data from the database
      getNoteData(noteQuery);
    }
    return (mRowIndex < mRecordList.size());
  }

  /**
   * Gets information about the number of entities and/or amount of data that has been read from
   * this <code>CollectionReader</code>, and the total amount that remains (if that information
   * is available).
   * <p>
   * This method returns an array of <code>Progress</code> objects so that results can be reported
   * using different units. For example, the CollectionReader could report progress in terms of the
   * number of documents that have been read and also in terms of the number of bytes that have been
   * read. In many cases, it will be sufficient to return just one <code>Progress</code> object.
   *
   * @return an array of <code>Progress</code> objects. Each object may have different units (for
   * example number of entities or bytes).
   */
  @Override
  public Progress[] getProgress() {
    return new Progress[]{
        new ProgressImpl(mRowIndex, mRecordList.size(), "Notes")
    };
  }

  protected void getNoteData(String noteQuery) throws CollectionException {
    try {
      //Get the initial note data, preserve the connection so it is not closed
      StopWatch timer = new StopWatch();
      logger.info("Notes Query: " + noteQuery);
      timer.start();
      LeoArrayListHandler handler = new LeoArrayListHandler();
      mRecordList = dataManager.query(connection, noteQuery, handler);
      mRowIndex = 0;
      timer.stop();
      logger.info("Notes query and results returned " + mRecordList.size() + " rows and took: " + timer);

      if (handler.getColumnIndex(noteIdField) < 0) {
        throw new CollectionException(
            "ID column name, " + noteIdField + " was not found in the query: " + noteQuery,
            null);
      }
      if (handler.getColumnIndex(noteTextField) < 0) {
        throw new CollectionException(
            "Note column name, " + noteTextField + " was not found in the query: " + noteQuery,
            null);
      }
      noteIdIndex = handler.getColumnIndex(noteIdField);
      noteTextIndex = handler.getColumnIndex(noteTextField);
    } catch (Exception e) {
      throw new CollectionException(e);
    }
  }

  /**
   * Generate the UIMA CollectionReader with resources.
   *
   * @return a UIMA CollectionReader.
   * @throws ResourceInitializationException if there is an error initializing the CollectionReader.
   */

  public CollectionReader produceCollectionReader() throws ResourceInitializationException {
    Map<String, Object> parameterValues = produceBaseDatabaseCollectionReaderParams();
    parameterValues.put(Param.NOTE_QUERY.getName(), noteQuery);
    parameterValues.put(Param.NOTE_ID_FIELD.getName(), noteIdField);
    parameterValues.put(Param.NOTE_TEXT_FIELD.getName(), noteTextField);
    parameterValues.put(Param.ANNOTATION_QUERY.getName(), annotationQuery);
    parameterValues.put(Param.ANNOTATION_START_FIELD.getName(), annotationStartField);
    parameterValues.put(Param.ANNOTATION_END_FIELD.getName(), annotationEndField);
    return produceCollectionReader(LeoUtils.getStaticConfigurationParameters(Param.class), parameterValues);
  }

  /**
   * Parameters to auto set from class properties.
   */
  public static class Param extends BaseDatabaseCollectionReader.Param {
    public static ConfigurationParameter NOTE_QUERY
        = new ConfigurationParameterImpl("noteQuery", "Query to pull the note text, at minimum should include ID and note fields.",
        ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
    public static ConfigurationParameter NOTE_ID_FIELD
        = new ConfigurationParameterImpl("noteIdField", "Name of the Column that contains the ID Field",
        ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
    public static ConfigurationParameter NOTE_TEXT_FIELD
        = new ConfigurationParameterImpl("noteTextField", "Name of the Column that contains the Note Field",
        ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
    public static ConfigurationParameter ANNOTATION_QUERY
        = new ConfigurationParameterImpl("annotationQuery", "Query to pull all annotations for a given ID",
        ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
    public static ConfigurationParameter ANNOTATION_START_FIELD
        = new ConfigurationParameterImpl("annotationStartField", "Name of the column which contains the start field",
        ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
    public static ConfigurationParameter ANNOTATION_END_FIELD
        = new ConfigurationParameterImpl("annotationEndField", "Name of the column which contains the end field",
        ConfigurationParameter.TYPE_STRING, true, false, new String[0]);
  }

  /**
   * Get a map of column number / column name from a result set metadata.
   *
   * @param metaData the resultset metadata
   * @return a map of column number / column name from a result set metadata.
   * @throws SQLException
   */
  public static Map<Integer, String> getColumnNameMap(ResultSetMetaData metaData) throws SQLException {
    Map<Integer, String> columnNames = new HashMap<Integer, String>();
    for (int i = 0; i < metaData.getColumnCount(); i++) {
      columnNames.put(i + 1, metaData.getColumnName(i + 1));
    }
    return columnNames;
  }

  public static LeoTypeSystemDescription flatAnnotationTypeSystem() {
    LeoTypeSystemDescription typesSystem = new LeoTypeSystemDescription();
    typesSystem.addType(
        TypeDescriptionBuilder.create("gov.va.vinci.leo.flat.types.Flat", "", "uima.tcas.Annotation")
            .addFeature("keys", "Property Keys Array", "uima.cas.StringArray")
            .addFeature("values", "Property Values Array", "uima.cas.StringArray")
            .getTypeDescription()
    );
    return typesSystem;
  }
}
