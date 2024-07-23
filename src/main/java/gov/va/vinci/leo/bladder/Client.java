package gov.va.vinci.leo.bladder;

import gov.va.vinci.leo.aucompare.listener.AuSummaryListener;
import gov.va.vinci.leo.cr.BaseLeoCollectionReader;
import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.uima.aae.client.UimaAsBaseCallbackListener;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An example UIMA AS PrepClient that takes command line arguments for its configuration.
 */
public class Client {
  public static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(gov.va.vinci.leo.tools.LeoUtils.getRuntimeClass().toString());
  // INFO: change the trainingModel path before running a process
  public static String trainingModel_FilePath = "";

  //new File("config/ClientConfig.groovy")
  @Option(name = "-clientConfigFile", usage = "The groovy config file that defines the client properties. (only ONE allowed).", required = true)
  File[] clientConfigFile;

  //new File("config/readers/FileCollectionReaderConfig.groovyample.groovy")
  @Option(name = "-readerConfigFile", usage = "The groovy config file that defines the reader (only ONE readerConfigFile allowed).", required = true)
  File[] readerConfigFile;

  // new File("config/listeners/SimpleXmiListenerener.groovy"),
  // new File("config/listeners/SimpleCsvListenerConfig.groovy.groovy")
  @Option(name = "-listenerConfigFile", usage = "The groovy config file that defines the listeners (one required, but can specify more than one).", required = true)
  File[] listenerConfigFileList;

  /**
   * @param args
   */
  public static void main(String[] args) throws CmdLineException {
    if (args.length < 3) {

      runClient_withBasicConfigs();

    } else {
      Client bean = new Client();
      CmdLineParser parser = new CmdLineParser(bean);
      try {
        parser.parseArgument(args);
      } catch (CmdLineException e) {
        printUsage();
        System.exit(1);
      }
      bean.runClient();
    }
  }

  public Client() {

  }

  public static void runClient_withBasicConfigs() {
    Client myAc = new Client(new File("config\\ClientConfig.groovy"),
        new File("config\\readers\\BatchDatabaseCollectionReaderConfig.groovy"
        ),
        new File[]{
            new File("config\\listeners\\DatabaseListenerConfig.groovy")

        });
    myAc.runClient();

  }


  public Client(File clientConfigFile, File readerConfig, File[] listenerConfigFiles) {
    readerConfigFile = new File[]{readerConfig};
    listenerConfigFileList = listenerConfigFiles;
    this.clientConfigFile = new File[]{clientConfigFile};
  }

  /**
   * Parse the groovy config files, and return the listeners objects that are defined in them.
   *
   * @param configs the groovy config files to slurp
   * @return the list of listeners that are defined in the groovy configs.
   * @throws MalformedURLException
   */
  public List<UimaAsBaseCallbackListener> getListeners(File... configs) throws MalformedURLException {
    ConfigSlurper configSlurper = new ConfigSlurper();
    List<UimaAsBaseCallbackListener> listeners = new ArrayList<UimaAsBaseCallbackListener>();

    for (File config : configs) {
      ConfigObject configObject = configSlurper.parse(config.toURI().toURL());
      if (configObject.get("listener") != null) {
        listeners.add((UimaAsBaseCallbackListener) configObject.get("listener"));
      }
    }

    return listeners;
  }

  /**
   * Parse the groovy config file, and return the reader object. This must be a BaseLeoCollectionReader.
   *
   * @param config the groovy config file to slurp
   * @return the reader defined in the groovy config.
   * @throws MalformedURLException
   */
  public static BaseLeoCollectionReader getReader(File config) throws MalformedURLException {
    ConfigSlurper configSlurper = new ConfigSlurper();

    ConfigObject configObject = configSlurper.parse(config.toURI().toURL());
    if (configObject.get("reader") != null) {
      return (BaseLeoCollectionReader) configObject.get("reader");
    }
    return null;
  }

  public static void printUsage() {
    CmdLineParser parser = new CmdLineParser(new Client());
    System.out.print("Usage: java " + Client.class.getCanonicalName());
    parser.printSingleLineUsage(System.out);
    System.out.println();
    parser.printUsage(System.out);

  }

  protected gov.va.vinci.leo.Client setClientProperties(gov.va.vinci.leo.Client leoClient) throws MalformedURLException, InvocationTargetException, IllegalAccessException {
    if (clientConfigFile.length != 1) {
      return leoClient;
    }

    ConfigSlurper configSlurper = new ConfigSlurper();
    ConfigObject o = configSlurper.parse(clientConfigFile[0].toURI().toURL());

    Set<Map.Entry> entries = o.entrySet();
    for (Map.Entry e : entries) {
      System.out.println("Setting property " + e.getKey() + " on client to " + e.getValue() + ".");
      BeanUtils.setProperty(leoClient, e.getKey().toString(), e.getValue());

    }

    return leoClient;
  }

  /**
   * Actual run method that configures and runs the client.
   */
  public void runClient() {
    try {

      if (readerConfigFile.length > 1 || clientConfigFile.length > 1) {
        printUsage();
        return;
      }
      /**
       * These point to whichever readers/listeners configurations are needed in this particular client.
       * There can be many listeners, but only one reader.
       */
      List<UimaAsBaseCallbackListener> listeners = getListeners(listenerConfigFileList);
      BaseLeoCollectionReader reader = getReader(readerConfigFile[0]);

      /**
       * Create the client.
       */
      gov.va.vinci.leo.Client myClient = new gov.va.vinci.leo.Client();
      setClientProperties(myClient);

      System.out.println("Broker URL: " + myClient.getBrokerURL() + "    Endpoint name: " + myClient.getEndpoint());
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      /**
       * Add the listeners from the groovy config.
       */
      //gov.va.vinci.bc.listeners.BinaryLearningListener dll = null;
      AuSummaryListener u = null;

      // INFO: If one of the listeners is a LearningListener - add code to the client
      for (UimaAsBaseCallbackListener listener : listeners) {
      //  if (listener instanceof gov.va.vinci.bc.listeners.BinaryLearningListener) {
      //    dll = (gov.va.vinci.bc.listeners.BinaryLearningListener) listener;
      //  }
        /**
         * Add the listeners from the groovy config.
         */
          if (listener instanceof AuSummaryListener) {
            u = ((AuSummaryListener) listener);
          }


        myClient.addUABListener(listener);
      }

      /**
       * Run the client with the collection reader.
       */
      myClient.run(reader);
      if (u != null) {
        try {
          u.outputStatsToConsole();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
/*
      if (dll != null) {
    SvmVectorTranslator svt = new  SvmVectorTranslator();
        try {
          log.info("Performing k-fold validation on " + dll.numVectors + " vectors..." );
          StopWatch clock = new StopWatch();
          clock.start();
          double svmAccuracy;

          // Set 3-fold cross validation
          svmAccuracy = dll.validate(svt,3);

          clock.stop();
          log.info("K-fold validation complete, " + clock + " - SVM Accuracy: " + svmAccuracy);
          log.info("Writing to " + trainingModel_FilePath);
          if (StringUtils.isNotBlank(trainingModel_FilePath)) {
            log.info("Training the final model for serialization...");
            clock = new StopWatch();
            svt.setModelPath(trainingModel_FilePath);
            clock.start();
            dll.train(svt);
            clock.stop();
            log.info("Training and serialization complete, " + clock);
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      */
      //System.out.println("Client finished in: " + stopWatch.getTime() + "ms.");
      stopWatch.stop();
      log.info("Client finished in: " + stopWatch.toString() + ".");


    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
