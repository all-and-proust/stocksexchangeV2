<%@ page import="java.util.*,com.stocks.model.*" %>
<html>
<head>
	<title>All Stocks</title>
</head>
<body>
<a href="MostActive.do">Back to Most Active Stocks</a>
<br/>
<br/>
<b>All Stocks with Frequency</b>
<br/>
<br/>
<table border = "1">
	<tr>
		<td>Rank</td>
		<td>Stock Symbol</td>
		<td>Frequency</td>
	</tr>
	<%
	List<Stock> stocks = (List<Stock>)request.getAttribute("allStocks");
	int ctr = 0;
	for(Stock s: stocks){
		ctr++;
	%>
	<tr>
		<td><%= ctr %></td>
		<td><%= s.getStockSymbol() %></td>
		<td><%= s.getFrequency() %></td>
	</tr>
	<%
	}
	%>
</table>
</body>
</html>