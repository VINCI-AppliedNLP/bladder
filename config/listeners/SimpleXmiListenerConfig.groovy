
import gov.va.vinci.leo.listener.SimpleXmiListener
import gov.va.vinci.leo.tools.LeoUtils

String timeStamp = LeoUtils.getTimestampDateDotTime().replaceAll("[.]", "_")
String xmiDir =  "//vhacdwfpcfs02/Projects/ORD_Schroeck_201411045D/NLP/test/output/" + timeStamp.substring(0, timeStamp.length() - 3) + "/xmi/";
//String xmiDir ="src/test/resources/output/xmi/"
if(!(new File(xmiDir)).exists()) (new File(xmiDir)).mkdirs()

listener = new SimpleXmiListener(new File(xmiDir), true)
//listener.setAnnotationTypeFilter("gov.va.vinci.leo.bladder.types.Stage")

