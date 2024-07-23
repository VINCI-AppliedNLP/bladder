package gov.va.vinci.leo.bladder.listeners;

import gov.va.vinci.leo.listener.BaseListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.aae.client.UimaAsBaseCallbackListener;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.EntityProcessStatus;

/**
 * Created by thomasginter on 1/14/16.
 */
public class HistoListener extends BaseListener {


    /**
     * @param aCas    the CAS containing the processed entity and the analysis results
     * @param aStatus the status of the processing. This object contains a record of any Exception that occurred, as well as timing information.
     * @see UimaAsBaseCallbackListener#entityProcessComplete(CAS, EntityProcessStatus)
     */
    @Override
    public void entityProcessComplete(CAS aCas, EntityProcessStatus aStatus) {
        super.entityProcessComplete(aCas, aStatus);
        
    }

    protected String cleanUpString(String str) {
        str = str.toLowerCase();
        str = StringUtils.normalizeSpace(str);
        str = str.replaceAll("\"", " ");
        str = str.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
        return str;
    }
}
