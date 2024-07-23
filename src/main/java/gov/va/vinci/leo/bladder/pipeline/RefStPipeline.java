package gov.va.vinci.leo.bladder.pipeline;

/*
 * #%L
 * Leo Examples
 * %%
 * Copyright (C) 2010 - 2014 Department of Veterans Affairs
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import gov.va.vinci.leo.bladder.ae.RefStAnnotator;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.types.TypeLibrarian;

import java.util.ArrayList;

public class RefStPipeline extends BasePipeline {


    /**
     * Constructor
     *
     * @throws Exception
     */
    public RefStPipeline() {
        /* List for holding our annotators. This list, and the order of the list created the annotator pipeline. */
        ArrayList<LeoAEDescriptor> annotators = new ArrayList<LeoAEDescriptor>();
        try {
            /** First, run regex on the document. */
            annotators.add(new RefStAnnotator().getLeoAEDescriptor()
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    /* Create aggregate descriptor and add type system */
        pipeline = new LeoAEDescriptor(annotators);
        pipeline.setTypeSystemDescription(getLeoTypeSystemDescription());

    }


    @Override
    protected LeoTypeSystemDescription defineTypeSystem() {
        description = new LeoTypeSystemDescription();
        try {
            description.addTypeSystemDescription(CentralTypes.getLeoTypeSystemDescription());
            description.addType(TypeLibrarian.getCSITypeSystemDescription());
            description.addTypeSystemDescription(new RefStAnnotator().getLeoTypeSystemDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }


}
