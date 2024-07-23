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

import gov.va.vinci.leo.bladder.ae.DocumentValue;
import gov.va.vinci.leo.bladder.ae.LogicAnnotator;
import gov.va.vinci.leo.bladder.comparators.invasionType.InvasionTypeComparator;
import gov.va.vinci.leo.bladder.comparators.invasionType.InvasionTypeFlatComparator;
import gov.va.vinci.leo.bladder.types.DocVariable;
import gov.va.vinci.leo.bladder.types.InvasionType;
import gov.va.vinci.leo.bladder.types.InvasionTypeFlatValue;
import gov.va.vinci.leo.bladder.types.InvasionTypeValue;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.flat.types.Flat;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.regex.types.RegularExpressionType;

import java.util.ArrayList;


public class InvasionTypePipeline extends BasePipeline {


    public InvasionTypePipeline() {
        /* List for holding our annotators. This list, and the order of the list created the annotator pipeline. */
        ArrayList<LeoAEDescriptor> annotators = new ArrayList<LeoAEDescriptor>();
        try {


            /** First, run regex on the document. */
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/invasion-type-regex.groovy")
                    .setTypeSystemDescription(getLeoTypeSystemDescription())

            );
            annotators.add(new RegexAnnotator().getLeoAEDescriptor()
                    .setName("ExcludeRegex")
                    .setParameterSetting(RegexAnnotator.Param.GROOVY_CONFIG_FILE.getName(), "src/main/resources/exclude-regex.groovy")
                    .addParameterSetting(RegexAnnotator.Param.OUTPUT_TYPE.getName(), true, false, "String", "gov.va.vinci.leo.bladder.types.Exclude")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName()
                            , new String[]{"gov.va.vinci.leo.bladder.types.InvasionTypeNone", "gov.va.vinci.leo.bladder.types.InvasionTypeSuspected"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.InvasionTypeInvasive"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), true)

                            //.setParameterSetting(FilterAnnotator.Param.KEEP_CHILDREN.getName(), true)

                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    // .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(), new String[]{InvasionType.class.getCanonicalName()})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.Exclude"})
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_DELETE.getName(),
                            new String[]{"gov.va.vinci.leo.bladder.types.InvasionTypeInvasive"
                                    ,"gov.va.vinci.leo.bladder.types.InvasionTypeNone",
                                    "gov.va.vinci.leo.bladder.types.InvasionTypeSuspected"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), true)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new FilterAnnotator().getLeoAEDescriptor()
                    .setParameterSetting(FilterAnnotator.Param.TYPES_TO_KEEP.getName(),
                            new String[]{ "gov.va.vinci.leo.bladder.types.InvasionTypeInvasive"
                                    ,"gov.va.vinci.leo.bladder.types.InvasionTypeNone",
                                    "gov.va.vinci.leo.bladder.types.InvasionTypeSuspected"})
                    .setParameterSetting(FilterAnnotator.Param.REMOVE_OVERLAPPING.getName(), false)
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            annotators.add(new LogicAnnotator().getLeoAEDescriptor()
                    .setName("InvasionLogic")
                    .setParameterSetting(LogicAnnotator.Param.TYPE_NAME.getName(), new String[] {
                            "gov.va.vinci.leo.bladder.types.InvasionTypeInvasive"
                            ,"gov.va.vinci.leo.bladder.types.InvasionTypeNone",
                            "gov.va.vinci.leo.bladder.types.InvasionTypeSuspected"})
                    .setParameterSetting(LogicAnnotator.Param.CONCEPT_FEATURE_NAME.getName(), "concept")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            /** Get the document level value for the InvasionType annotation **/
            annotators.add(new DocumentValue(InvasionTypeValue.class.getCanonicalName(), InvasionType.class.getCanonicalName())
                    .setComparatorClass(InvasionTypeComparator.class.getCanonicalName())
                    .getLeoAEDescriptor()
                    .setName("InvasionTypeVariableValue")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));

            /** Get the document level value for the Flat annotations of type InvasionType **/
            annotators.add(new DocumentValue(InvasionTypeFlatValue.class.getCanonicalName(), Flat.class.getCanonicalName())
                    .setComparatorClass(InvasionTypeFlatComparator.class.getCanonicalName())
                    .getLeoAEDescriptor()
                    .setName("InvasionTypeFlatVariableValue")
                    .setTypeSystemDescription(getLeoTypeSystemDescription()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    /* Create aggregate descriptor and add type system */
        pipeline = new LeoAEDescriptor(annotators);
        pipeline.setTypeSystemDescription(getLeoTypeSystemDescription());

    }

    @Override
    public LeoTypeSystemDescription defineTypeSystem() {
        description = new LeoTypeSystemDescription();
        try {
            description.addType("gov.va.vinci.leo.bladder.types.InvasionType", "InvasionType regex annotation", RegularExpressionType.class.getCanonicalName());
            description.addType("gov.va.vinci.leo.bladder.types.InvasionTypeInvasive", "InvasionType regex annotation", "gov.va.vinci.leo.bladder.types.InvasionType");
            description.addType("gov.va.vinci.leo.bladder.types.InvasionTypeNone", "InvasionType regex annotation", "gov.va.vinci.leo.bladder.types.InvasionType");
            description.addType("gov.va.vinci.leo.bladder.types.InvasionTypeSuspected", "InvasionType regex annotation", "gov.va.vinci.leo.bladder.types.InvasionType")
                    .addType("gov.va.vinci.leo.bladder.types.InvasionContext", "InvasionType regex annotation", "gov.va.vinci.leo.annotationpattern.types.AnnotationPatternType")
                    .addType("gov.va.vinci.leo.bladder.types.Exclude", "Exclude Type regex annotation",  RegularExpressionType.class.getCanonicalName())


                    .addType("gov.va.vinci.leo.bladder.types.InvasionTypeValue", "InvasionType document value", DocVariable.class.getCanonicalName())
                    .addType("gov.va.vinci.leo.bladder.types.InvasionTypeFlatValue", "InvasionType Flat Value", DocVariable.class.getCanonicalName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }
}
