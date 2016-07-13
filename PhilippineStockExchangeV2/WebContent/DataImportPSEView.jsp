<%@ page import="java.util.*,com.stocks.model.*,java.math.*" %>
<html>
<head>
	<title>Data Import PSE View</title>
</head>
<body>
<b>Review the following stocks:</b>
<br/>
<br/>
<table border = "1">

	<%
	String lastTradingDate = "";
	List<Map<String,String>> stocks = (List<Map<String,String>>)request.getAttribute("liveDataFromPSE");
	int ctr = 0;
	for(Map<String,String> sds : stocks){
		if(sds.get("lastTradedPrice").equals("DATE")){
			lastTradingDate = sds.get("securityAlias");
			%>
			<tr>
				<td colspan="4">As Of: <%= lastTradingDate %></td>
			</tr>
	        <tr>
				<td>No.</td>
				<td>Stock Symbol</td>
				<td>Last Traded Price</td>
				<td>Stock Value</td>
			</tr>
			<%
        } else {
        	ctr = ctr + 1;
        	%>
        	<tr>
        		<td><%= ctr %></td>
        		<td><%= sds.get("securitySymbol") %></td>
        		<td><%= Double.parseDouble(sds.get("lastTradedPrice")) %></td>
        		<td><%= new BigDecimal(Double.parseDouble(sds.get("totalVolume")) * Double.parseDouble(sds.get("lastTradedPrice"))).setScale(2, RoundingMode.CEILING) %></td>
        	</tr>
        	<%
        }
	}
	String[] nextTokenArr = lastTradingDate.substring(0, 10).split("/");
	if(nextTokenArr.length > 1){
		lastTradingDate = nextTokenArr[2] + "-" + nextTokenArr[0] +"-" + nextTokenArr[1];
	}
	%>
</table>
<br/>
<br/>
<a href="DataImportPSE.do?tradingDate=<%= lastTradingDate %>">Proceed With Import!</a>
<br/>
<a href="MostActive.do">Back to Most Active Stocks</a>
<br/>
</body>
</html>