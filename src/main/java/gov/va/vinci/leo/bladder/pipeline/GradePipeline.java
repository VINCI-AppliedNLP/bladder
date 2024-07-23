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

import gov.va.vinci.leo.bladder.ae.*;
import gov.va.vinci.leo.bladder.types.*;
import gov.va.vinci.leo.bladder.comparators.grade.GradeFlatComparator;
import gov.va.vinci.leo.bladder.comparators.grade.GradeComparator;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.flat.types.Flat;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.regex.types.RegularExpressionType;
import gov.va.vinci.leo.types.TypeLibrarian;

import java.util.ArrayList;


public class GradePipeline extends BasePipeline {


    /**
     * Constructor
     *
     * @throws Exception
     */
    public GradePipeline() {
        /* List for holding our annotators. This list, and the order of the list created the annotator pipeline. */
        ArrayList<LeoAEDescriptor> annotators = new ArrayList<LeoAEDescriptor>();
        try {
            /** First, run regex on the document. */
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/grade-regex.groovy")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            /** Validate Grade annotations and normalize 'numeric' values to specific ranges. **/
            annotators.add(new GradeVal().getLeoAEDescriptor()
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            /** Get the document level value for the Grade annotation **/
            annotators.add(new DocumentValue(GradeValue.class.getCanonicalName(), Grade.class.getCanonicalName())
                    .setComparatorClass(GradeComparator.class.getCanonicalName())
                    .getLeoAEDescriptor()
                    .setName("GradeVariableValue")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            /** Get the document level value for the Flat annotations of type Grade **/
            annotators.add(new DocumentValue(GradeFlatValue.class.getCanonicalName(), Flat.class.getCanonicalName())
                    .setComparatorClass(GradeFlatComparator.class.getCanonicalName())
                    .getLeoAEDescriptor()
                    .setName("GradeFlatVariableValue")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{  "gov.va.vinci.leo.bladder.types.Grade"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), false)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new LogicAnnotator().getLeoAEDescriptor()
                    .setName("GradeLogic")
                    .setParameterSetting(LogicAnnotator.Param.TYPE_NAME.getName(), new String[]{Grade.class.getCanonicalName()})
                    .setParameterSetting(LogicAnnotator.Param.CONCEPT_FEATURE_NAME.getName(), "concept")
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
            description.addType("gov.va.vinci.leo.bladder.types.Grade", "Grade regex annotation", RegularExpressionType.class.getCanonicalName())
                    .addType("gov.va.vinci.leo.bladder.types.GradeValue", "Grade document value", "gov.va.vinci.leo.bladder.types.DocVariable")
                    .addType("gov.va.vinci.leo.bladder.types.GradeFlatValue", "Grade Flat Value", "gov.va.vinci.leo.bladder.types.DocVariable");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }


}
