# Bladder pathology extraction system

The system uses Java 8 and UIMA AS 2.6.0



All text processed and validated on

    Variable type 	Document Count 	TP 	TN 	Ref Pos 	Sys Pos 	Precision 	Recall
    Grade 	    300 	247 	 35 	254 	263 	0.939 	0.972
    Invasion 	300 	198 	 43 	243 	248 	0.798 	0.815
    Depth 	    300 	 96 	160 	127 	120 	0.800 	0.756
    Histology 	300 	268 	  5 	290 	294 	0.912 	0.924
    Stage 	    300 	 47 	 33 	 55 	 50 	0.940 	0.855
    Grade 	    150 	119 	 19 	126 	130 	0.915 	0.944
    Invasion 	150 	 91 	 21 	119 	122 	0.746 	0.765
    Depth 	    150 	 30 	 86 	 50 	 51 	0.588 	0.600
    Histology 	150 	132 	  2 	147 	147 	0.898 	0.898
    Stage 	    150 	 20 	 15 	 28 	 22 	0.909 	0.714




-pipeline=gov.va.vinci.leo.bladder.pipeline.GradePipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.InvasionTypePipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.DepthOfInvasionPipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.MusclePipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.HistologyPipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.CISPipeline
-pipeline=gov.va.vinci.leo.bladder.pipeline.StagePipeline