<%
    if (session.getAttribute("loggedParticipant") == null) {
        response.sendRedirect("../index.html");
    }
%>

<!DOCTYPE html>
<html lang="en">
<%
final String[] SYSTEM_NAMES = new String[] {"antlr4", "car-report", "hibernate-orm", "jenkins", "k-9", "MyExpenses", "opencms-core", "phoenix", "spring-batch", "weka"};
final String[] SYSTEM_NL_NAMES = new String[] {"ANTLR", "Car-Report (Android)", "Hibernate", "Jenkins", "K-9 Mail (Android)", "MyExpenses (Android)", "Apache Phoenix", "OpenCMS", "Spring", "Weka"};

final String[] TOPICS_NAMES = new String[] {"parsing", "android", "orm", "sql", "ci", "mail", "cms", "hbase", "ml"};
final String[] TOPICS_NL_NAMES = new String[] {"Parsing/lexical analysis (e.g., automata and grammars)", "Android development", "Object-relational mapping", "SQL and JDBC", "Continuous integration", "Internet mail protocols (e.g., SMTP)", "Content Management Systems", "HBase", "Machine learning"};
%>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="CodeSurvey">

    <title>CodeSurvey</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Custom CSS -->
    <link href="../css/clap.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3" style="margin-top:10px">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Additional details</h3>
                    </div>
                    <div class="panel-body">
                    	<form class="form-horizontal" data-toggle="validator" data-delay="0" data-disable="true" role="form"
                        action="../addDetailsParticipant" method="post">
                    	
                    	<h4>Knowledge about the systems</h4>
                    	<p>State your knowledge about these Java projects:</p>
                        	<% for (int i = 0; i < SYSTEM_NAMES.length; i++) {
                        		String systemName = SYSTEM_NAMES[i];
                        		String systemNlName = SYSTEM_NL_NAMES[i];
                        	%>
                                <div class="form-group">
                                    <label class="col-md-4 control-label"><%=systemNlName%></label>
                                    <div class="col-md-8">
                                   	<select name="skill<%=systemName%>" id="skill<%=systemName%>" class="form-control">
                                   		<option value="none">Never used</option>
                                   		<option value="low">Beginner user</option>
                                   		<option value="medium">Normal user</option>
                                   		<option value="high">Expert user</option>
                                   		<option value="developer">Contributed to the development</option>
                                  		</select>
                                   	<div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <% } %>
                            
                        <h4>Knowledge about topics</h4>
                   		<p>State your knowledge about these topics:</p>
                   			<% for (int i = 0; i < TOPICS_NAMES.length; i++) {
                        		String topicName = TOPICS_NAMES[i];
                        		String topicNlName = TOPICS_NL_NAMES[i];
                        	%>
                                <div class="form-group">
                                    <label class="col-md-4 control-label"><%=topicNlName%></label>
                                    <div class="col-md-8">
                                   	<select name="skill<%=topicName%>" id="skill<%=topicName%>" class="form-control">
                                   		<option value="none">No knowledge</option>
                                   		<option value="low">Low knowledge</option>
                                   		<option value="medium">Medium knowledge</option>
                                   		<option value="high">Good knowledge</option>
                                  		</select>
                                   	<div class="help-block with-errors"></div>
                                    </div>
                                </div>
                            <% } %>
                            
                            <!-- Change this to a button or input when using this as a form -->
                            <div class="row">
                            <button class="btn btn-sm btn-primary col-md-6 col-md-offset-4" type="submit">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

    <script src="../js/validator.js"></script>

</body>

</html>
