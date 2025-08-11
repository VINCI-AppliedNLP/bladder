# NLP Bladder Pathology Post-Processing (Aggregate Output View)

This README documents the design decisions and encoding logic used in `bladder_post_process_normalization_query.sql`, which creates the SQL view `[nlp].[Aggregate_Output_Original]`. The view normalizes raw (row-level) NLP extraction output into a **single summarized row per pathology report (per TIU Document)** with one column per target clinical variable.

## 1. Source Data & Join Context
Inputs combined to build the view:
- `[nlp].[output_tiu_combined_pathology_20250811]` – raw NLP output (one row per extracted annotation/value).
- `[nlp].[note_tiu_combined_pathology]` – note / document level metadata (patient ICN, document identifiers).
- `[Src].[STIUNotes_TIUDocument_8925]` – TIU note table for `ReferenceDateTime` and canonical text.

The join keys:
- `TIUDocumentSID` links NLP output to metadata and to TIU note header.

Each final row in the view is uniquely identified by `(PatientICN, TIUDocumentSID)` and includes the full `ReportText` plus normalized feature columns.

## 2. Normalization Workflow Overview
1. Map raw `VariableType` strings (which embed fully qualified type names and sometimes polarity) to a canonical `FeatureName`.
2. Convert each `(FeatureName, OptionName)` pair into an **ordinal numeric code** labeled as `Value`.
3. For each feature within a document, select the **maximum numeric value** (pivot + MAX) to represent the *most advanced / affirmative* state.
4. Pivot features to columns and derive paired human-readable label columns (e.g., `Histology_value` + `Histology`).

## 3. Feature Canonicalization (VariableType → FeatureName)
The SQL collapses variant raw type names into these stable feature buckets:
- `Histology`
- `Grade`
- `CIS` (Carcinoma In Situ)
- `InvasionType`
- `InvasionDepth`
- `Muscle` (presence of muscularis propria in specimen)
- `Stage`
- `Specimen` (relevance of specimen type to bladder pathology)
- `Document Type` (document filtering / classification)

Logic patterns used:
- Presence of substrings like `%histology%`, `%InvasionDepth%`, `%MuscleNo%|%MuscleYes%`, `%CisYes%|%CisNo%`, `%Specimen%`.
- Fallback: strip the Java/Groovy package prefix `gov.va.vinci.leo.bladder.types.`.

## 4. Ordinal Encoding Strategy
Each categorical domain is mapped to integers where higher usually means greater clinical specificity, severity, or affirmative presence. This enables the MAX aggregation to choose the desired row when multiple values are extracted.

### 4.1 Histology
| Code | OptionName            | Aggregated Label        | Rationale |
|------|-----------------------|-------------------------|-----------|
| 0    | not_Stated            | Not Stated              | Baseline unknown |
| 1    | no_cancer             | No Cancer               | Explicit absence |
| 2    | other                 | Other (Specify)         | Non-core class |
| 3    | adenocarcinoma        | Adenocarcinoma          | Malignant subtype |
| 4    | squamous              | Squamous                | Malignant subtype |
| 5    | PUNLMP                | PUNLMP                  | Distinct urothelial lesion |
| 6    | urothelial            | Urothelial              | Dominant target category |

Ordering prioritizes capturing urothelial carcinoma if present (assigned highest). Warning: Mixed histologies collapse to the highest code; secondary components are lost.

### 4.2 Grade
`g0–g4` → `0–4` (increasing grade). Unrecognized values default to 0 (Not Stated).

### 4.3 Carcinoma In Situ (CIS)
| Code | Meaning |
|------|---------|
| 0    | No / Not Stated |
| 1    | Yes |
Encoding treats absence and not stated equivalently for roll-up; downstream consumers may need to differentiate (limitation noted below).

### 4.4 InvasionType
0 Not Stated < 1 None < 2 Suspected < 3 Invasive. Facilitates selecting Invasive if any assertion exists.

### 4.5 InvasionDepth
0 Not Stated < 1 Superficial < 2 Muscle < 3 Perivesical < 4 Other Organ. Reflects increasing extent.

### 4.6 Muscle (Muscle in Specimen)
0 Not Stated < 1 No < 2 Yes. Presence prioritized because absence may be due to omission / parsing variance.

### 4.7 Stage
0 Not Stated < 1 Other (Specify) < 2 Tis < 3 Ta < 4 T1 < 5 T2 < 6 T3 < 7 T4. Numerical order reflects pathological advancement. `Other (Specify)` sits low to prevent overshadowing defined TNM categories.

### 4.8 Specimen Relevance
0 Not Stated < 1 Irrelevant/Other Specimen < 2 Relevant. In final label mapping the code 1 is rendered as `Other Specimen` (the raw label `Irrelevant` is generalized).

### 4.9 Document Type
0 Not Stated < 1 Outside Pathology < 2 Not Bladder Pathology. (Final textual mapping is commented out; numeric retained for filtering logic.)

## 5. Aggregation Rule
For each `(PatientICN, TIUDocumentSID, FeatureName)` group, the SQL takes `MAX(Value)`. This implies:
- Higher code = preferred representative value.
- Multiple conflicting values collapse deterministically.
- Absence (0) is inert and cannot override a positive / specific finding.

## 6. Pivot & Output Columns
After aggregation:
- Numeric columns: `<Feature>_value` (e.g., `Histology_value`).
- Human-readable columns: `<Feature>` (e.g., `Histology`). For some features (`DocumentType`) only the numeric code is exposed (text mapping commented out to preserve potential downstream logic flexibility).
- Text (`ReportText`) retained for traceability / error analysis.

## 7. Null & Fallback Handling
- Unrecognized `OptionName` values default to 0 (Not Stated) via CASE `else 0` branches, ensuring they do not falsely escalate a category.
- After pivot, labels not resolvable (unexpected integer) yield `NULL` to surface data issues distinctly from 'Not Stated'.

## 8. Limitations & Caveats
1. Information Loss in Mixed Histology: Only the highest ordered histology survives; mixtures (e.g., urothelial + squamous differentiation) are flattened.
2. CIS Absence vs Not Mentioned: Both map to 0; differentiating explicit negation from silence is not preserved.
3. Potential Over-Elevation: Any single mention of a higher stage / depth can dominate despite conflicting earlier statements; no temporal / section weighting applied.
4. Encoding Dependencies: Changing numeric order alters clinical meaning of MAX; adjustments must be carefully versioned.
5. Document Type Text Suppressed: Downstream users must apply their own decode if needed.

## 9. Rationale for Using MAX
Compared alternatives:
- FIRST/LAST: Order instability without robust positional sorting.
- MODE: Requires tie-breaking logic and more expensive computation; still leaves mixed cases ambiguous.
- RULE HIERARCHY: Implemented implicitly via numeric ordering, simplifying SQL.

## 10. Extensibility Guidelines
When adding a new feature:
1. Define clinical ordering explicitly (table in this README).
2. Assign codes with room for future categories (e.g., leave gaps if expansion likely).
3. Update CASE blocks in the `flat` CTE.
4. Add feature to the PIVOT `IN (...)` list and final select mapping.
5. Append documentation here (new subsection in Section 4).

When modifying ordering:
1. Create a new view name (e.g., `[nlp].[Aggregate_Output_v2]`) to preserve backward compatibility.
2. Record a change log (see Section 14 if added later).

## 11. Testing & Validation Recommendations
- Spot check a sample of documents: compare raw rows vs aggregated value decisions.
- Validate monotonic logic: for each feature, confirm higher codes truly represent more advanced / affirmative states.
- Regression test counts of each label distribution before & after changes.

## 12. Downstream Usage Patterns
Common tasks enabled by this view:
- Cohort filtering (e.g., select first invasive report per patient).
- Trend analysis (longitudinal highest stage per patient over time).
- Performance auditing of NLP extraction (discrepancy review using `ReportText`).

## 13. Operational Notes
- View is non-materialized; performance depends on size of underlying NLP table. Consider materializing if latency is high.
- The raw source table includes a date suffix (`..._20250811`); rotating to newer snapshots requires updating the FROM clause in the view definition.

## 14. Suggested Change Log Format (Future)
| Date | Version | Change | Rationale | Impact |
|------|---------|--------|-----------|--------|
| (fill) | v1 | Initial creation | Baseline aggregation | — |

## 15. Quick Reference: Code → Label
Summarized highest codes:
- Histology 6 = Urothelial
- Grade 4 = Undifferentiated (4)
- CIS 1 = Yes
- InvasionType 3 = Invasive
- InvasionDepth 4 = Other Organ
- Muscle 2 = Yes
- Stage 7 = T4
- Specimen 2 = Relevant

## 16. Potential Future Enhancements
- Track multiple histologies via separate boolean columns (e.g., `Histology_Urothelial_Flag`).
- Distinguish explicit negation from unspecified (store separate boolean per feature: `*_Mentioned`, `*_Present`).
- Add provenance columns (e.g., span offsets for the selected max value) for auditability.
- Introduce tie-break rule based on section priority or proximity to diagnosis phrases.

## 17. Disclaimer
This aggregation prioritizes simplicity and deterministic behavior over full preservation of nuanced pathology narratives. Clinical validation is advised before using these outputs for patient-level decision support.

---
For questions or proposed revisions, open an issue or submit a pull request referencing this README and the SQL view definition.
