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
|---------------|----------------|-----|-----|---------|---------|-----------|--------|
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

