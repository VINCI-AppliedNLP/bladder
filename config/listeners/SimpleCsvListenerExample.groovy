import gov.va.vinci.leo.listener.SimpleCsvListener
import gov.va.vinci.leo.regex.types.RegularExpressionType


listener = new SimpleCsvListener(new File("data/output/output.csv"), false, RegularExpressionType.canonicalName);