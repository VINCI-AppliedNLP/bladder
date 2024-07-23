package gov.va.vinci.bladder;

import gov.va.vinci.leo.bladder.pipeline.*;
import gov.va.vinci.leo.cr.FileCollectionReader;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.listener.SimpleXmiListener;

import java.io.File;
import java.util.HashMap;

public class PathologyEdgeTesting {


    protected static LeoAEDescriptor aggregate = null;
    protected static LeoTypeSystemDescription types = null;
    protected static boolean generateTypes = false;
    protected static String brokerUrl = "tcp://localhost:61616";
    protected static String endpoint = "PathTest_bladder_20190321";

    public static void main(String[] args) {
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        types = new LeoTypeSystemDescription();

        types.addTypeSystemDescription(new GradePipeline().getLeoTypeSystemDescription());
        types.addTypeSystemDescription(new CISPipeline().getLeoTypeSystemDescription());
        types.addTypeSystemDescription(new MusclePipeline().getLeoTypeSystemDescription());
        types.addTypeSystemDescription(new InvasionTypePipeline().getLeoTypeSystemDescription());
        types.addTypeSystemDescription(new DepthOfInvasionPipeline().getLeoTypeSystemDescription());
        types.addTypeSystemDescription(new HistologyPipeline().getLeoTypeSystemDescription());
        types.addTypeSystemDescription(new StagePipeline().getLeoTypeSystemDescription());

        aggregate = new LeoAEDescriptor();
        aggregate.setTypeSystemDescription(types);
        aggregate.addDelegate(new GradePipeline().getPipeline());  //1
        aggregate.addDelegate(new CISPipeline().getPipeline());     //2
        aggregate.addDelegate(new MusclePipeline().getPipeline());   //3
        aggregate.addDelegate(new InvasionTypePipeline().getPipeline());  //4
        aggregate.addDelegate(new DepthOfInvasionPipeline().getPipeline()); //5
        aggregate.addDelegate(new HistologyPipeline().getPipeline());  //6
        aggregate.addDelegate(new StagePipeline().getPipeline()); //7

        aggregate.setName("FullPipeline");
        aggregate.setNumberOfInstances(1);
        gov.va.vinci.leo.Service testService = null;
        try {
            testService = new gov.va.vinci.leo.Service();
        } catch (Exception e) {
            e.printStackTrace();
        }
        testService.setBrokerURL(brokerUrl);
        testService.setEndpoint(endpoint);

        testService.setDeleteOnExit(true);
        try {
            testService.deploy(aggregate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileCollectionReader reader = null;
        SimpleXmiListener xmiListener = null;
        String files_in = "\\\\vhacdwfpcfs02\\Projects\\ORD_LYNCH_201807010D\\NLP\\edge\\";
        String files_out = "\\\\vhacdwfpcfs02\\Projects\\ORD_LYNCH_201807010D\\NLP\\output\\out_xmi_edge";
        reader = new FileCollectionReader(new File(files_in), false);
        if (!new File(files_out).exists()) {
            new File(files_out).mkdirs();
        }

        xmiListener = new SimpleXmiListener(new File(files_out));

        // setup client
        gov.va.vinci.leo.Client testClient = new gov.va.vinci.leo.Client();
        testClient.setBrokerURL(testService.getBrokerURL());
        testClient.setEndpoint(testService.getEndpoint());
        try {
            testClient.run(reader, xmiListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(100);
            //testService.undeploy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
