USE [Study_DataBase]
GO

/****** Object:  View [nlp].[Aggregate_Output]    

Roll up raw NLP output to the max value of each variable type per pathology report



******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO





















create  view  [nlp].[Aggregate_Output_Original]
as 
with flat as (
select [PatientICN],[TIUDocumentSID], [ReferenceDateTime], [ReportText], [FeatureName],
	case when [FeatureName] like '%Histology%' 
		then case	when [OptionName] = 'not_Stated' then 0
					when [OptionName] = 'no_cancer' then 1	
					when [OptionName] = 'other' then 2
					when [OptionName] = 'adenocarcinoma' then 3
					when [OptionName] = 'squamous' then 4
					when [OptionName] = 'PUNLMP' then 5
					when [OptionName] = 'urothelial' then 6
					else  0					
			 end
		when [FeatureName] like 'Grade' 
		then case	when [OptionName] = 'g0' then 0
					when [OptionName] = 'g1' then 1
					when [OptionName] = 'g2' then 2
					when [OptionName] = 'g3' then 3
					when [OptionName] = 'g4' then 4
					else 0
			 end
		when [FeatureName] like 'Cis%' 
		then case	when [OptionName] = 'CisNo' then 0
					when [OptionName] = 'CisYes' then 1
					else 0
		end
		when [FeatureName] like 'InvasionType%' 
		then case	when [OptionName] = 'Not Stated' then 0
					when [OptionName] = 'None' then 1
					when [OptionName] = 'Suspected' then 2
					when [OptionName] = 'Invasive' then 3
					else  0
		end
		when [FeatureName] like 'InvasionDepth%' 
		then case	when [OptionName] = 'not_stated' then 0
					when [OptionName] = 'superficial' then 1
					when [OptionName] = 'muscle' then 2
					when [OptionName] = 'perivesical' then 3
					when [OptionName] = 'other_organ' then 4
					else 0
		end
		when [FeatureName] like 'Muscle%' 
		then case	when [OptionName] = 'Not Stated' then 0
					when [OptionName] = 'MuscleNo' then 1
					when [OptionName] = 'MuscleYes' then 2 
					else 0
		end
		when [FeatureName] like 'Stage' 
		then case	when [OptionName] = 'Not Stated' then 0
					when [OptionName] = 'Other (Specify)' then 1
					when [OptionName] = 'Tis' then 2
					when [OptionName] = 'Ta' then 3
					when [OptionName] = 'T1' then 4
					when [OptionName] = 'T2' then 5
					when [OptionName] = 'T3' then 6
					when [OptionName] = 'T4' then 7
					else 0
		end
		when [FeatureName] like 'Specimen' 
			then case 
				when [OptionName] =  'Relevant' then 2
				when [OptionName] =  'Irrelevant' then 1
				else 0	
		end
		when [FeatureName] like 'Document Type' 
		then case	when [OptionName] = 'Not Stated' then 0
					when [OptionName] = 'Outside Pathology' then 1
					when [OptionName] = 'Not Bladder Pathology' then 2 
					else 0	
		end
	end as [Value]
	, [OptionName]
from
( SELECT 
notes.patienticn 
, nlp.[TIUDocumentSID]
, tiu.ReferenceDateTime
, [ReportText]
          , case when [VariableType] like '%histology%' then 'Histology'
	  when [VariableType] like '%InvasionDepth%' then 'InvasionDepth'
	  	  when [VariableType] like '%InvasionType%' then 'InvasionType'
		  when [VariableType] like '%MuscleNo%' then 'Muscle'
		  when [VariableType] like '%MuscleYes%' then 'Muscle'
		  when [VariableType] like '%CisYes%' then 'CIS'
		  when [VariableType] like '%CisNo%' then 'CIS'
		   when [VariableType] like '%Specimen%' then 'Specimen'
	  
	  else replace([VariableType], 'gov.va.vinci.leo.bladder.types.', '') end as FeatureName
      ,[VariableValue] as [OptionName]
      ,[SpanStart]
      ,[SpanEnd]
      ,[Snippets]
  FROM 
		[Study_DataBase].[nlp].[raw_nlp_output] as nlp
	  left join 
		[note].[notes_table] tiu --referenc to table where reporttext exists
		on nlp.TiuDocumentSid=tiu.TIUDocumentSID
  
  ) d

)



  select 
  [PatientICN]
  ,b.[TIUDocumentsid]
  , [ReferenceDateTime]
  , [ReportText]
			,[Histology_value]
			, case [Histology_value] 
					when 0 then 'Not Stated'  
					when 1 then 'No Cancer'
					when 2 then 'Other (Specify)' 
					when 3 then 'Adenocarcinoma'  
					when 4 then 'Squamous' 
					when 5 then 'PUNLMP'
					when 6 then 'Urothelial'
					else  null
			   end as [Histology]
			 , [Grade_value]
			 , case [Grade_value] 
					when 0 then 'Not Stated' 
					when 1 then 'Low (1)' 
					when 2 then 'Intermediate (2)' 
					when 3 then 'High (3)' 
					when 4 then 'Undifferentiated (4)' 
					else null
				end as [Grade]
			 , [CarcinomaInSitu_value] 
			 , case [CarcinomaInSitu_value]
					when 0 then 'No'
					when 1 then 'Yes'  
					else null
			  end as [CarcinomaInSitu]
	, [InvasionType_value]
	, case [InvasionType_value]
		when 0 then 'Not Stated' 
		when 1 then 'None' 
		when 2 then 'Suspected' 
		when 3 then 'Invasive' 
		else null
		end as [InvasionType]
		, [InvasionDepth_value]
		, case [InvasionDepth_value]
			when 0 then 'Not Stated' 
			when 1 then 'Superficial' 
			when 2 then 'Muscle'
			when 3 then 'Perivesical' 
			when 4 then 'Other Organ' 
			else null
			end as [InvasionDepth]
		, [MuscleInSpecimen_value]
		, case [MuscleInSpecimen_value]
			when 0 then 'Not Stated'
			when 1 then 'No' 
			when 2 then 'Yes' 
			else null
			end as [MuscleInSpecimen]
		, [Stage_value]
		, case [Stage_value] 
			when 0 then 'Not Stated' 
			when 1 then 'Other (Specify)' 
			when 2 then 'Tis'
			when 3 then 'Ta' 
			when 4 then 'T1' 
			when 5 then 'T2' 
			when 6 then 'T3'
			when 7 then 'T4' 
			else null
		  end  as [Stage]
		, [DocumentType_value]
		--, case [DocumentType_value]
		--	when 0 then 'Not Stated'
		--	when 1 then 'Outside Pathology'
		--	when 2 then 'Not Bladder Pathology'
		--	else null
		--	end as [DocumentType]
      	, case [SpecimenType_value]
			when 0 then 'Not Stated'
			when 1 then 'Other Specimen'
			when 2 then 'Relevant'
			else null
			end as [SpecimenType]
   
  from 
  (
		select distinct 
		[PatientICN],
		TIUDocumentsid, 
		[ReferenceDateTime],
		[ReportText],
			[CIS] as [CarcinomaInSitu_value], 
			[Document Type] as [DocumentType_value], 
			[Grade] as [Grade_value], 
			[Histology] as [Histology_value], 
			[InvasionDepth] as InvasionDepth_value ,
			[InvasionType] as InvasionType_value,
			[Muscle] as MuscleInSpecimen_value,
			[Stage] as Stage_value,
			[Specimen] as SpecimenType_value
		from
	( select [PatientICN],TIUDocumentsid, [ReferenceDateTime], [ReportText], [FeatureName], [Value]
		from flat 
	) ff
  pivot
	( max(value)
	  for [FeatureName] in ( [CIS] ,
							 [Document Type] ,
							 [Grade] ,
							 [Histology] ,
							 [InvasionDepth] ,
							 [InvasionType] ,
							 [Muscle] ,
							 [Stage], 
							 [Specimen])
	) piv
	) b
	

/*
Carcinoma In Situ (CIS)
Comments
Document Type
Grade
Histology
Invasion Depth
Invasion Type
Muscle in Specimen
Other (Specify)
Stage
*/



















GO


