# Bladder Pathology Extraction System

This repository contains an NLP system for the Bladder Cancer project with Dr. Florian Schroeck. The system is designed to extract key clinical concepts from pathology reports related to bladder cancer. The system uses Java 8 and UIMA AS 2.6.0.

## Citation

If you use this code, please cite the following paper:

Florian R. Schroeck, Olga V. Patterson, Patrick R. Alba, Erik A. Pattison, John D. Seigne, Scott L. DuVall, Douglas J. Robertson, Brenda Sirovich, Philip P. Goodney,
Development of a Natural Language Processing Engine to Generate Bladder Cancer Pathology Data for Health Services Research,
Urology,
Volume 110,
2017,
Pages 84-91,
ISSN 0090-4295,
https://doi.org/10.1016/j.urology.2017.07.056

## System Logic

The system processes pathology reports to extract and validate various clinical concepts related to bladder cancer. It utilizes several pipelines tailored to specific extraction tasks, including:

- **GradePipeline**: Extracts the grade of the cancer.
- **InvasionTypePipeline**: Identifies the type of invasion.
- **DepthOfInvasionPipeline**: Determines the depth of invasion.
- **MusclePipeline**: Analyzes muscle involvement.
- **HistologyPipeline**: Extracts histological type.
- **CISPipeline**: Identifies carcinoma in situ.
- **StagePipeline**: Determines the stage of the cancer.

## Extracted Concepts and Performance Metrics

The system extracts the following clinical concepts and has been validated with the following performance metrics:

| Variable type | Document Count | TP  | TN  | Ref Pos | Sys Pos | Precision | Recall |
| ------------- | -------------- | --- | --- | ------- | ------- | --------- | ------ |
| Grade         | 300            | 247 | 35  | 254     | 263     | 0.939     | 0.972  |
| Invasion      | 300            | 198 | 43  | 243     | 248     | 0.798     | 0.815  |
| Depth         | 300            | 96  | 160 | 127     | 120     | 0.800     | 0.756  |
| Histology     | 300            | 268 | 5   | 290     | 294     | 0.912     | 0.924  |
| Stage         | 300            | 47  | 33  | 55      | 50      | 0.940     | 0.855  |
| Grade         | 150            | 119 | 19  | 126     | 130     | 0.915     | 0.944  |
| Invasion      | 150            | 91  | 21  | 119     | 122     | 0.746     | 0.765  |
| Depth         | 150            | 30  | 86  | 50      | 51      | 0.588     | 0.600  |
| Histology     | 150            | 132 | 2   | 147     | 147     | 0.898     | 0.898  |
| Stage         | 150            | 20  | 15  | 28      | 22      | 0.909     | 0.714  |

## Usage

To run the system, use the following pipelines as needed:

```sh
-pipeline=gov.va.vinci.leo.bladder.pipeline.GradePipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.InvasionTypePipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.DepthOfInvasionPipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.MusclePipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.HistologyPipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.CISPipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.StagePipeline
```


## Aggregate Output Data Dictionary

For the original work, the raw NLP output was rolled up to a report-level view selecting the max (ordinal) value for each variable. The roll‑up SQL (`bladder_post_process_normalization_query.sql`) is in the repository root and produces one row per pathology report.

| Column | Type | Description | Allowed / Encoded Values |
|--------|------|-------------|---------------------------|
| PatientICN | string | Patient identifier (ICN). | Free text |
| TIUDocumentsid | int | TIU document surrogate key. | Integer |
| ReferenceDateTime | datetime | Document reference (report) timestamp. | SQL datetime |
| ReportText | text | Full pathology report text used for NLP. | Free text |
| Histology_value | int | Ordinal histology code (max per doc). | 0 Not Stated; 1 No Cancer; 2 Other (Specify); 3 Adenocarcinoma; 4 Squamous; 5 PUNLMP; 6 Urothelial |
| Histology | string | Histology label mapped from code. | See Histology_value (NULL if unmapped) |
| Grade_value | int | Ordinal grade code. | 0 Not Stated; 1 Low (1); 2 Intermediate (2); 3 High (3); 4 Undifferentiated (4) |
| Grade | string | Grade label. | See Grade_value |
| CarcinomaInSitu_value | int | CIS presence code. | 0 No / Not Stated; 1 Yes |
| CarcinomaInSitu | string | CIS label. | No; Yes |
| InvasionType_value | int | Invasion type qualitative code. | 0 Not Stated; 1 None; 2 Suspected; 3 Invasive |
| InvasionType | string | Invasion type label. | See InvasionType_value |
| InvasionDepth_value | int | Depth of invasion code. | 0 Not Stated; 1 Superficial; 2 Muscle; 3 Perivesical; 4 Other Organ |
| InvasionDepth | string | Depth label. | See InvasionDepth_value |
| MuscleInSpecimen_value | int | Presence of muscularis propria in specimen. | 0 Not Stated; 1 No; 2 Yes |
| MuscleInSpecimen | string | Muscle presence label. | See MuscleInSpecimen_value |
| Stage_value | int | Simplified pathologic stage code. | 0 Not Stated; 1 Other (Specify); 2 Tis; 3 Ta; 4 T1; 5 T2; 6 T3; 7 T4 |
| Stage | string | Stage label. | See Stage_value |
| DocumentType_value | int | Document classification / filter. | 0 Not Stated; 1 Outside Pathology; 2 Not Bladder Pathology |
| SpecimenType | string | Specimen relevance category (text only). | Not Stated; Other Specimen; Relevant |

Notes:
- Each *_value column is an ordinal where higher indicates a more specific / affirmative state; aggregation uses MAX.
- Code 0 represents Not Stated (or unmapped); unexpected integers produce NULL labels for visibility.
