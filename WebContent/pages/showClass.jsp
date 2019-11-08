<!DOCTYPE html>
<html lang="en">
<%@page import="it.unimol.codesurvey.bean.Class"%>
<%@page import="it.unimol.codesurvey.storage.ClassManagement"%>

<%
	String title = request.getParameter("title");

	Class toUnderstand = null;
	try {
		int id = Integer.parseInt(request.getParameter("id"));
		toUnderstand = ClassManagement.getClass(id);
	} catch (NumberFormatException e) {
		toUnderstand = new Class();
		toUnderstand.setRelatedResources("");
		toUnderstand.setSystemName("");
		toUnderstand.setTextToShow("This class not available in this preview.");
	}
	
	if (title != null)
		title = title.replaceAll("[^A-Za-z0-9._]", "");
	else
		title = "???";
%>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="CodeSurvey">

    <title>CodeSurvey</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Custom CSS -->
    <link href="../css/clap.css" rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="../js/syntaxhighlighter_3.0.83/scripts/shCore.js"></script>
    <script type="text/javascript" src="../js/syntaxhighlighter_3.0.83/scripts/shBrushJava.js"></script>
    <link href="../js/syntaxhighlighter_3.0.83/styles/shCore.css" rel="stylesheet" type="text/css" />
	<link href="../js/syntaxhighlighter_3.0.83/styles/shThemeDefault.css" rel="stylesheet" type="text/css" />
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body>

    <div id="wrapper">

                <div class="col-md-12 col-md-offset-0" style="margin-top:10px">
                <p><b>Class <%=title%></b></p>
                <div class="col-md-9 col-md-offset-0" style="margin-top:20px">
                	<script type="syntaxhighlighter" class="brush: java; toolbar: false; "><![CDATA[<%=toUnderstand.getTextToShow() %>]]></script>        
                </div>
                <div class="col-md-12 col-md-offset-0" style="margin-top:20px">
                	<%= toUnderstand.getRelatedResources() %>
                </div>
                 
            </div>
        
        <!-- /#page-wrapper -->
     <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
	
<script type="text/javascript">
    SyntaxHighlighter.all()
</script>


</body>
</html>
