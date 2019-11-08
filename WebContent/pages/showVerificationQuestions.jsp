<!DOCTYPE html>
<html lang="en">
<%@page import="it.unimol.codesurvey.bean.Participant"%>
<%@page import="it.unimol.codesurvey.bean.Snippet"%>
<%@page import="it.unimol.codesurvey.bean.Assessment"%>
<%@page import="it.unimol.codesurvey.bean.Question"%>
<%@page import="it.unimol.codesurvey.bean.Answer"%>
<%@page import="java.util.*"%>

<%
	Snippet toUnderstand = (Snippet) session.getAttribute("snippetToComprehend");
	Assessment assessment = (Assessment) session.getAttribute("assessment");
	ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("questions");
	Participant loggedParticipant = (Participant) session.getAttribute("loggedParticipant");
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
                <p><b>Please answer the following questions</b></p>
                <div class="col-md-12 col-md-offset-0" style="margin-top:20px">
                	<script type="syntaxhighlighter" class="brush: java; toolbar: false; "><![CDATA[<%= toUnderstand.getTextToShow() %>]]></script>        
                </div>
                <div class="col-md-12 col-md-offset-0" style="margin-top:20px">
                
                <div class="panel panel-default">
                	<form  role="form" action="../storeAnswers" method="get">
                    <% for(int i=0; i<questions.size(); i++){ 
                    Question question = questions.get(i);%>
                    <div class="panel-heading">
                        <h3 class="panel-title">Question <%= (i+1) %>: <%= question.getQuestion() %></h3>
                    </div>
                    <div class="panel-body">
                    	<% for(Answer answer: question.getAnswers()) {%>
                    	<div class="col-md-10 col-md-offset-1">
                                 <label class="radio-inline">
                                 	<input type="radio" name="question<%=question.getId() %>" id="<%=question.getId() %>" value="<%= answer.getId() %>" 
                                 	<% if(question.getAnswers().indexOf(answer) == 0){ %>checked<% } %>><%= answer.getText() %>
                                 </label><br><br>
                       </div>	
                       <% } %>
                	</div>	
                    <% } %> 
                      
                       <div class="row">
                       		 <button class="btn btn-sm btn-primary col-md-4 col-md-offset-4" type="submit" style="margin-top:20px">Next</button>
                       </div>
                	<br>
                	</form>
                    </div>
                </div>
                	
                </div>
                <div class="col-md-12 col-md-offset-0" style="margin-top:20px">
                	<!-- @Simone: Add here links to the resources shown in the snippet (a new page must be opened, don't close this one) -->
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
