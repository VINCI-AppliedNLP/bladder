package gov.va.vinci.leo.bladder;

import gov.va.vinci.leo.bladder.pipeline.BasePipeline;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.tools.LeoUtils;
import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;

public class Service {

    private static final Logger log = Logger.getLogger(LeoUtils.getRuntimeClass().toString());

    @Option(name = "-serviceConfigFile", usage = "The groovy config file that defines the service properties. (only ONE allowed).", required = true)
    File[] serviceConfigFile;

    @Option(name = "-pipeline", usage = "Select pipeline.", required = false)
    String[] pipeline;


    public Service() {
    }

    /**
     * @param args
     */

    public static void main(String[] args) {
        gov.va.vinci.leo.bladder.Service current_service = new gov.va.vinci.leo.bladder.Service();
        if (args.length == 0) {
            current_service.serviceConfigFile = new File[]{new File("config/ServerConfig.groovy")};
            current_service.pipeline = new String[]{"gov.va.vinci.bladder.pipeline.Pipeline"};

        } else {

            CmdLineParser parser = new CmdLineParser(current_service);
            try {
                parser.parseArgument(args);
            } catch (CmdLineException e) {
                printUsage();
                System.exit(1);
            }
        }
        current_service.run();
    }

    public static void printUsage() {
        CmdLineParser parser = new CmdLineParser(new gov.va.vinci.leo.bladder.Service());
        System.out.print("Usage: java " + gov.va.vinci.leo.bladder.Service.class.getCanonicalName());
        parser.printSingleLineUsage(System.out);
        System.out.println();
        parser.printUsage(System.out);

    }

    public void run() {
        log.info(" \r\n \r\n ===  Starting Service " + LeoUtils.getTimestampDateDotTime() + " ====\r\n  ");

        gov.va.vinci.leo.Service service = null;

        try {
            service = new gov.va.vinci.leo.Service();
            setServerProperties(service);

            LeoAEDescriptor aggregate = new LeoAEDescriptor();
            LeoTypeSystemDescription types = new LeoTypeSystemDescription();

            /** Create an aggregate of the components. */
            for (String line : pipeline) {
                Class pipe = Class.forName(line);
                BasePipeline pipeInstance = (BasePipeline) pipe.newInstance();
                System.out.println("Adding pipeline: " + pipeInstance.getClass().getCanonicalName());
                aggregate.addDelegate(pipeInstance.getPipeline());

                /* create type system */
                types.addTypeSystemDescription(pipeInstance.getLeoTypeSystemDescription());
            }
            aggregate.addTypeSystemDescription(types);
            //types.jCasGen("src/main/java/", "target/classes");

            aggregate.setIsAsync(false);
            aggregate.setNumberOfInstances(1);

            /* Deploy the service. */
            service.deploy(aggregate);

            System.out.println("Deployment: " + service.getDeploymentDescriptorFile());
            System.out.println("Aggregate: " + service.getAggregateDescriptorFile());

            System.out.println("Service running, press enter in this console to stop.");
            System.in.read();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Loading properties from configuration file
     *
     * @param leoServer
     * @return
     * @throws MalformedURLException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected gov.va.vinci.leo.Service setServerProperties(gov.va.vinci.leo.Service leoServer) throws MalformedURLException,
            InvocationTargetException, IllegalAccessException {
        if (serviceConfigFile.length != 1) {
            return leoServer;
        }

        ConfigSlurper configSlurper = new ConfigSlurper();
        ConfigObject o = configSlurper.parse(serviceConfigFile[0].toURI().toURL());

        Set<Map.Entry> entries = o.entrySet();
        for (Map.Entry e : entries) {
            System.out.println("Setting property " + e.getKey() + " on service to " + e.getValue() + ".");
            BeanUtils.setProperty(leoServer, e.getKey().toString(), e.getValue());
        }
        return leoServer;
    }
}
