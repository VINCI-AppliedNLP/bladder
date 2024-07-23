import gov.va.vinci.leo.bladder.cr.FlatAnnotationDatabaseReader;

String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
String url = "jdbc:sqlserver://vhacdwrb02:1433;databasename=ORD_Schroeck_201411045D;integratedSecurity=true"
String noteQuery = " SELECT  docs.[TIUDocumentSID]  , docs.[ReportText]   FROM [ORD_Schroeck_201411045D].[validation].[ChartReview_ClinicalElement_20150814] as docs  WHERE  (NewTrainSetAssignment = 3)   "
//"SELECT [TIUDocumentSID], [ReportText]  FROM [ORD_Schroeck_201411045D].[nlp].[AnnotationCorpus_600_20150814]   WHERE NewTrainSetAssignment = 2"
String noteIdField = "tiudocumentsid"
String noteTextField = "reporttext"
String annotationQuery =
//        "SELECT ac.[TIUDocumentSID], ac.[TrainSet], ff.[start], ff.[end], ff.[CoveredText], ff.[FeatureName], ff.[OptionName], ff.[Incorrect] FROM [ORD_Schroeck_201411045D].[nlp].[AnnotationCorpus_600_20150814] ac LEFT JOIN [ORD_Schroeck_201411045D].[validation].[ChartReview_FlatFile] ff ON ac.TIUDocumentSID = ff.TIUDocumentSID  WHERE ac.TIUDocumentSID = ? AND ff.[FeatureName] like 'Invasion Type' ORDER BY ff.[start]"
        "SELECT ac.[TIUDocumentSID], ac.[NewTrainSetAssignment] , " +
                "ff.[start], ff.[end], ff.[CoveredText], ff.[FeatureName], ff.[OptionName],'' [Incorrect]" +
                " FROM [ORD_Schroeck_201411045D].[nlp].[AnnotationCorpus_600_20150814] ac " +
                " LEFT JOIN [ORD_Schroeck_201411045D].[nlp].[ChartReview_FlatFile] ff  " +
                "ON ac.TIUDocumentSID = ff.TIUDocumentSID  WHERE ac.TIUDocumentSID = ?  ORDER BY ff.[start]"
String annotationStart = "start"
String annotationEnd = "end"

reader = new FlatAnnotationDatabaseReader(driver, url, "", "")
        .setNoteQuery(noteQuery)
        .setNoteIdField(noteIdField)
        .setNoteTextField(noteTextField)
        .setAnnotationQuery(annotationQuery)
        .setAnnotationStartField(annotationStart)
        .setAnnotationEndField(annotationEnd);l
