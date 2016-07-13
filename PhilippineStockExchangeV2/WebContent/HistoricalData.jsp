<%@ page import="java.util.*,com.stocks.model.*,java.math.*,java.text.*" %>
<html>
<head>
	<title>Historical Data</title>
</head>
<body>
<%
DecimalFormat df = new DecimalFormat("#,###.00");
Date latestMostActive = (Date)((Map<String,Object>)request.getAttribute("historicalMap")).get("latestMostActive");
String symbol = request.getParameter("symbol");
String frequency = request.getParameter("frequency");
int freq = Integer.parseInt(frequency);
%>
<b>Stock Symbol: <%= symbol %></b>
<br/>
<b>Latest Most Active Date: <%= latestMostActive %></b>
<br/>
<br/>
<table border = "1">
	<tr>
		<td>Rank</td>
		<td>Stock Symbol</td>
		<td>Last Price</td>
		<td>Stock Value</td>
		<td>Closing Date</td>
	</tr>
	<%
	Map<String,Object> historicalMap = ((Map<String,Object>)request.getAttribute("historicalMap"));
	List<Stock> stocks = (List<Stock>)historicalMap.get("stocksList");
	int ctr = 0;
	double low1 = 0.0;
	double high1 = 0.0;
	double cps = 0.0;
	for(Stock s: stocks){
		ctr++;
		if(ctr == 1){
			low1 =  s.getLastPrice();
		}
		
		if(ctr == freq){
			high1 = s.getLastPrice();
		}
		
		if(latestMostActive.getTime() == s.getClosingDate().getTime()){
		%>
	<tr bgcolor="#FFFF00">
		<td><%= ctr %></td>
		<td><%= s.getStockSymbol() %></td>
		<td><%= s.getLastPrice() %></td>
		<td><%= df.format(s.getStockValue()) %></td>
		<td><%= s.getClosingDate() %></td>
	</tr>		
		
		<%
		cps = s.getLastPrice();
		} else {
	%>
	<tr>
		<td><%= ctr %></td>
		<td><%= s.getStockSymbol() %></td>
		<td><%= s.getLastPrice() %></td>
		<td><%= df.format(s.getStockValue()) %></td>
		<td><%= s.getClosingDate() %></td>
	</tr>
	<%
		}
	}
	double targetprice = cps * 1.03;
	BigDecimal howClose = new BigDecimal(-1);
	if(high1 != low1)
		howClose = new BigDecimal((targetprice-low1)/(high1-low1) * 100).setScale(2, RoundingMode.CEILING);
	%>
</table>

<br/>
<br/>
<%
if(high1 == low1) {
%>
<font color="blue"><b>How Close: Need More Data</b></font>
<%
}
else if(howClose.doubleValue() <= 50.0) {
%>
<font color="green"><b>How Close: <%= howClose %> percent!</b></font>
<%
} else {
%>
<font color="red"><b>How Close: <%= howClose %> percent!</b></font>
<%
}
%>
<br/>
<br/>
<b>How Close Value Interpretation:</b>
<br/>
Buying Price today: <%= cps %>
<br/>
Selling Price (3% increase to buying price): <%= new BigDecimal(targetprice).setScale(2, RoundingMode.CEILING) %>
<br/>
<font color="green">How Close value: 0% to 50% - Selling price is closer to 10-day low. Best time to buy.</font>
<br/>
<font color="red">How Close value: 51% and up - Selling price is closer to 10-day high. NOT best time to buy.</font>
<br/>
<font color="blue">How Close value: Need More Data - 10-day high and 10-day low are equal. NOT best time to buy.</font>
<br/>
<br/>
<form method="POST" action="AnalyzeStock.do">
<input type="hidden" name="symbol" value="<%= symbol %>" />
<input type="hidden" name="low" value="<%= low1 %>" />
<input type="hidden" name="high" value="<%= high1 %>" />
<table>
<tr>
	<td>Number of Shares:</td>
	<td><input type="text" size="12" maxlength="12" name="numshare" /></td>
</tr>
<tr>
	<td>Cost Per Share:</td>
	<td><input type="text" size="12" maxlength="12" name="costpershare" /></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td><input type="submit" value="Analyze"/></td>
</tr>
</table>
<br/>
<br/>
<a href="MostActive.do">Back To Most Active Page</a>
</form>

</body>
</html>