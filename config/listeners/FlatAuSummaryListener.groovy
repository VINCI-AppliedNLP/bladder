package listeners
import gov.va.vinci.leo.aucompare.comparators.SpanAuComparator
import gov.va.vinci.leo.aucompare.listener.AuSummaryListener
import gov.va.vinci.leo.bladder.types.*
import gov.va.vinci.leo.refst.types.*

def typesMap = [:]
typesMap[GradeFlatValue.canonicalName] = GradeValue.canonicalName
typesMap[InvasionType_Invasive.canonicalName] = InvasionTypeInvasive.canonicalName
typesMap[InvasionType_none.canonicalName] = InvasionTypeNone.canonicalName
typesMap[InvasionType_suspected.canonicalName] = InvasionTypeSuspected.canonicalName
typesMap[InvasionDepth_muscle.canonicalName] = InvasionDepthMuscle.canonicalName
typesMap[InvasionDepth_other.canonicalName] = InvasionDepthOther.canonicalName
typesMap[InvasionDepth_perivesical.canonicalName] = InvasionDepthPerivesical.canonicalName
typesMap[InvasionDepth_superficial.canonicalName] = InvasionDepthSuperficial.canonicalName
/*
typesMap[Grade_1.canonicalName] = Grade_G1.canonicalName
typesMap[Grade_2.canonicalName] = Grade_G2.canonicalName
typesMap[Grade_3.canonicalName] = Grade_G3.canonicalName
typesMap[Grade_4.canonicalName] = Grade_G4.canonicalName
*/
typesMap[Histology_adenocarcinoma.canonicalName] =  Histology_adenocarcinoma.canonicalName
typesMap[Histology_no_cancer.canonicalName] = HistologyNocancer.canonicalName
typesMap[Histology_other.canonicalName] =  HistologyOther.canonicalName
typesMap[Histology_punlmp.canonicalName] =  HistologyPunlmp.canonicalName
typesMap[Histology_squamous.canonicalName] =  HistologySquamous.canonicalName
typesMap[Histology_urothelial.canonicalName] =  HistologyUrothelial.canonicalName
/*
//typesMap[DoiFlatValue.canonicalName] = DoiValue.canonicalName
*/

listener = new AuSummaryListener(new SpanAuComparator(typesMap, false));