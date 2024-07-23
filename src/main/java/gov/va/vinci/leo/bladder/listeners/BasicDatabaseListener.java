package gov.va.vinci.leo.bladder.listeners;

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.listener.BaseDatabaseListener;
import gov.va.vinci.leo.model.DatabaseConnectionInformation;
import gov.va.vinci.leo.tools.LeoUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.*;


public class BasicDatabaseListener extends BaseDatabaseListener {
  private static final Logger log = Logger.getLogger(LeoUtils.getRuntimeClass().toString());

  protected ArrayList<ArrayList<String>> fieldList;
  // table name for this listeners
  protected String outTableName;
  // database name for this listeners
  protected String dbsName;
  ArrayList<String> testlist = new ArrayList<String>( Arrays.asList( new String[] {""}));

  /**
   * Base gov.va.vinci.leo.listeners.
   *
   * @param databaseConnectionInformation information on the database to connect to
   * @param preparedStatementSQL          the prepared statement to use for inserts. For example: insert into myTable ( col1, col2, col2) values (?, ?, ?)
   * @param batchSize                     the size of batches to do in each insert. Typically 1000 is a good batch size.
   * @param validateConnectionEachBatch   if true, the databaseConnectionInformation.validationQuery will be run before each batch in addition to making sure the connection
   * @param fieldList                     String array list of string arrays <ColumnName, <input index, output data type>>
   * @param dbsName
   * @param tableName
   * @param inTypes                       String array of types to output - EchoExtractor specific, is not needed for other projects.
   */
  public BasicDatabaseListener(DatabaseConnectionInformation databaseConnectionInformation,
                               String preparedStatementSQL,
                               int batchSize,
                               boolean validateConnectionEachBatch,
                               ArrayList<ArrayList<String>> fieldList,
                               String dbsName,
                               String tableName,
                               String... inTypes
  ) {
    super(databaseConnectionInformation, preparedStatementSQL, batchSize, validateConnectionEachBatch);
    this.fieldList = fieldList;
    this.inputType = inTypes;
    this.dbsName = dbsName;
    this.outTableName = tableName;
  }

  /**
   * @param driver
   * @param url
   * @param dbUser
   * @param dbPwd
   * @param dbsName
   * @param tableName
   * @param batchSize
   * @param fieldList
   * @param inTypes
   * @return
   */
  public static BasicDatabaseListener createNewListener(
      String driver, String url, String dbUser, String dbPwd,
      String dbsName, String tableName, int batchSize,
      ArrayList<ArrayList<String>> fieldList, String... inTypes) {

    DatabaseConnectionInformation databaseConnectionInformation = new DatabaseConnectionInformation(
        driver, url, dbUser, dbPwd);

    String preparedStatement = createPreparedStatementSQL(dbsName, tableName, fieldList);
    boolean validateConnectionEachBatch = true;
    return new BasicDatabaseListener(databaseConnectionInformation,
        preparedStatement,
        batchSize,
        validateConnectionEachBatch,
        fieldList,
        dbsName,
        tableName,
        inTypes
    );
  }

  /**
   * For each annotation type Relation create an entry
   *
   * @param aCas
   * @return
   */
  @Override
  protected List<Object[]> getRows(CAS aCas) {
    ArrayList rowList = new ArrayList<Object[]>();
    int instanceId = 0;
    if(testlist.contains(docInfo.getID())){
      System.out.println(docInfo.getID());
    }
    try {
      JCas aJCas = aCas.getJCas();

      // EchoExtractor has three potential output types -- Relation1, Relation2, and Relation3
      // In most cases only Relation1 is needed, but others are possible
      for (String incomingType : this.inputType) {

        org.apache.uima.cas.Type inputType = aJCas.getTypeSystem().getType(incomingType);
        Iterator<Annotation> lit = aJCas.getAnnotationIndex(inputType).iterator();
        // Make sure that at least one output annotation exists before creating a row
Boolean hasNext = lit.hasNext();
        if (lit.hasNext()) {

          HashMap<String, String> documentInfo = new HashMap<String, String>();
          // Step 1: set initial values to documentInfo attributes
          documentInfo.put("DocID", docInfo.getID());
          if (docInfo.getRowData() != null) {
            for (ArrayList<String> headerInfo : this.fieldList) {
              int index = -1;
              try {
                index = Integer.parseInt(headerInfo.get(1));
              } catch (Exception e) {
                // Nothing
              }
              if (index >= 0)
                documentInfo.put(headerInfo.get(0), docInfo.getRowData(index));
            }
          }
          while (lit.hasNext()) {
            instanceId++;
            HashMap<String, String> instanceData = new HashMap<String, String>();
            // Step 2. add all other values from the Logic
            // TODO: get fields from ListenerLogic
            instanceData.putAll(ListenerLogic.getInstanceData(aJCas, lit.next()));
            if (instanceData.size() > 0) {
              instanceData.put("InstanceID", "" + instanceId);
              instanceData.putAll(documentInfo);

              // Step 3: Convert from HashMap of fields for each instance to an array
              // add a new instance to the rowList for output
              rowList.add(ListenerLogic.convertFromMapToArray(instanceData, this.fieldList));
            }
          } // end while
        }// end if type exists
      }// end for type
    } catch (org.apache.uima.cas.CASException e) {
      e.printStackTrace();
    }
    return rowList;
  }

  public String checkAnnotation_Present_Feature(JCas aJCas, String inputType, String feature) {
    String output = "";

    // add all values
    try {
      if (AnnotationLibrarian.getAllAnnotationsOfType(aJCas, inputType, false).size() > 0) {
        ArrayList<Annotation> l = new ArrayList();
        l.addAll(AnnotationLibrarian.getAllAnnotationsOfType(aJCas, inputType, false));
        if (StringUtils.isBlank(feature)) {
          output = "true";
        } else {
          HashSet<String> outSet = new HashSet();
          // if there are more values, add all feature values into a set and then output
          if (l.size() > 1) {
            for (Annotation a : l) {
              if (StringUtils.isNotBlank(a.getFeatureValueAsString(a.getType().getFeatureByBaseName(feature)))) {
                outSet.add(a.getFeatureValueAsString(a.getType().getFeatureByBaseName(feature)));
              }
            }



            if (outSet.size() > 1) {
              Iterator<String> i = outSet.iterator();
              output = i.next();
              while (i.hasNext()) {
                output = output + "; " + i.next();
              }
            } else {
              output = outSet.iterator().next();
            }
          } else {
            Annotation a = l.get(0);
            output = a.getFeatureValueAsString(a.getType().getFeatureByBaseName(feature));
          }
        }
      } else {
        output = "false";
      }
    } catch (CASException e) {
      e.printStackTrace();
    }


    return output;

  }

  /**
   * TODO: This method will not be needed once the latest leo is released (after Leo 2014.08.0)
   *
   * @param createStatement
   * @param dropFirst
   * @param tableName
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException

  @Override
  public void createTable(String createStatement, boolean dropFirst, String tableName)
      throws ClassNotFoundException, java.sql.SQLException, InstantiationException, IllegalAccessException {
    super.createTable(createStatement, dropFirst, tableName);
    conn.createStatement().execute(createStatement);
  }

/**/
  /**
   * Creates the table structure.
   *
   * @param dropFirst If drop, the table is dropped before creation.
   * @param dbsName   The database name (needed by some databases for the create statement)
   * @param tableName The resulting table name
   * @param fieldList A map of column names (key) and types (value) for example "id", "varchar(20)"
   */

  public void createTable(boolean dropFirst,
                          String dbsName,
                          String tableName,
                          ArrayList<ArrayList<String>> fieldList
  ) {
    String createStatement = createCreateStatement(dbsName, tableName, fieldList);
    try {
      this.createTable(createStatement, dropFirst, tableName);
    } catch (Exception e) {
      log.error("Creating a table failed \r\n" + e.getMessage());
    }
    log.info("Created a new table: \r\n" + createStatement);
  }//createTable method


  /**
   * @param dropFirst
   */
  public void createTable(boolean dropFirst) {
    if (StringUtils.isNotBlank(this.dbsName)
        && StringUtils.isNotBlank(this.outTableName)
        && this.fieldList.size() > 0) {
      this.createTable(dropFirst, this.dbsName, this.outTableName, this.fieldList);
    } else {
      log.error("Creating table failed because table name or database name or fields are not populated.");
    }
  }

  /**
   * TODO: propose to add this method to BaseDatabaseListener
   * <p/>
   * Protected method to build a create table statement.
   *
   * @param dbsName   the database name if needed.
   * @param tableName the table name.
   * @param fieldList A map of column names (key) and types (value) for example "id", "varchar(20)"
   * @return The sql create statement.
   */
  public String createCreateStatement(String dbsName,
                                      String tableName,
                                      ArrayList<ArrayList<String>> fieldList) {

    String statement = "CREATE TABLE " + dbsName + "." + tableName + " ( ";
    for (ArrayList<String> entry : fieldList) {
      statement = statement + entry.get(0) + " " + entry.get(2) + ", ";
    }
    statement = statement.substring(0, (statement.length() - 2)) + " ) ;";
    return statement;
  }

  /**
   * TODO: Move this static method to BaseDatabaseListener. Serves to create insert statement based on the database and table
   * name
   *
   * @param dbsName
   * @param tableName
   * @param fieldList
   * @return
   */
  public static String createPreparedStatementSQL(String dbsName,
                                                  String tableName,
                                                  ArrayList<ArrayList<String>> fieldList) {
    String statement = "INSERT INTO " + dbsName + "." + tableName + " ( ";
    String values = "";
    for (ArrayList<String> entry : fieldList) {
      statement = statement + entry.get(0) + ", ";
      values = values + " ?,";
    }
    statement = statement.substring(0, statement.length() - 2)
        + " ) VALUES ( " + values.substring(0, values.length() - 1)
        + " ) ;";
    log.info("PreparedStatement : " + statement);
    return statement;
  }
}
