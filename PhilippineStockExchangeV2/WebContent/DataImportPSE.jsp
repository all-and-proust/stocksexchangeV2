<%@ page import="java.util.*,com.stocks.model.*" %>
<html>
<head>
	<title>Stock Score</title>
</head>
<body>
<table border="0">
<% 
   Integer importResult = (Integer)request.getAttribute("importResult");
   if(importResult == 0){
   %>
	<tr>
		<td>Import Data Result:</td>
		<td>Successful</td>
	</tr>
   <% 
   } else {
	   %>
	<tr>
		<td>Import Data Result:</td>
		<td>Failed. Please contact Ben De Leon!</td>
	</tr>
	   <%
   }
%>
</table>
<br/>
<br/>
<a href="MostActive.do">Back To Most Active Page</a>
</body>
</html>