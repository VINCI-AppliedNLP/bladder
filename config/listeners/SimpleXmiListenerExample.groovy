import gov.va.vinci.leo.bladder.types.Doi
import gov.va.vinci.leo.flat.types.Flat
import gov.va.vinci.leo.listener.SimpleXmiListener
import gov.va.vinci.leo.regex.types.RegularExpressionType
import gov.va.vinci.leo.tools.LeoUtils

String timeStamp = LeoUtils.getTimestampDateDotTime().replaceAll("[.]", "_")
String xmiDir =  "data/output/xmi/" + timeStamp

if(!(new File(xmiDir)).exists()) (new File(xmiDir)).mkdirs()

listener = new SimpleXmiListener(new File(xmiDir), true)
listener.setAnnotationTypeFilter(Flat.canonicalName)