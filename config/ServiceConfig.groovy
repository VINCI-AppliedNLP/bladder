/**
 * The UIMA AS Broker url that is coordinating requests.
 */
brokerURL = "tcp://localhost:61616"


endpoint = "BladderService"

/** Tell the service to persist the descriptors that are generated, deletes them by default **/
deleteOnExit = true
//descriptorDirectory = "config/desc"

/** Additional service configurations  **/
casPoolSize = 4