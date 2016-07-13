<%@ page import="java.util.*,com.stocks.model.*,java.text.*" %>
<html>
<head>
	<title>Most Active Stocks</title>
</head>
<body>
<a href="PriceAlert.do">Price Alert</a> | 
<a href="LiveStocksMonitoring.do">Start Live Stocks Monitoring!</a> | 
<a href="DataImport.do">Import Data From CSV!</a> | 
<a href="DataImportPSEView.do">Import Data Directly from PSE!</a> | 
<a href="AllStocks.do">View All Stocks With Frequency</a>
<br/>
<br/>
<b>Most Active Stocks In The Last 10 Trading Days</b>
<br/>
<br/>
<table border = "1">
	<tr>
		<td>Rank</td>
		<td>Stock Symbol</td>
		<td>Frequency</td>
		<td>Total Value</td>
		<td>Latest Most Active</td>
		<td>Low</td>
		<td>High</td>
		<td>Buy Price</td>
		<td>Sell Price</td>
		<td>How Close</td>
	</tr>
	<%
	DecimalFormat df = new DecimalFormat("#,###.00");
	List<Stock> stocks = (List<Stock>)request.getAttribute("mostActive");
	int ctr = 0;
	for(Stock s: stocks){
		ctr++;
	%>
	<tr>
		<td><%= ctr %></td>
		<td><a href="HistoricalData.do?symbol=<%= s.getStockSymbol() %>&frequency=<%= s.getFrequency() %>"><%= s.getStockSymbol() %></a></td>
		<td><%= s.getFrequency() %></td>
		<td><%= df.format(s.getTotalStockValue()) %></td>
		<td><%= s.getLatestMostActive() %></td>
		<td><%= s.getLow() %></td>
		<td><%= s.getHigh() %></td>
		<td><%= s.getLastPrice() %></td>
		<td><%= s.getTargetPrice() %></td>
		<td <%= (s.getPercentHowClose().doubleValue() > 0.0 && s.getPercentHowClose().doubleValue() <= 50.0 ) ? "bgcolor='green'" : "" %>><%= (s.getPercentHowClose().intValue() == -1) ? "Need More Data" : s.getPercentHowClose() %></td>
	</tr>
	<%
	
	}
	%>
</table>
</body>
</html>