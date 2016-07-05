<%@ page import="java.util.*,com.stocks.model.*" %>
<html>
<head>
	<title>Stock Score</title>
</head>
<body>
<table>
	<tr>
		<td>Stock Symbol:</td>
		<td>${stockScore.symbol}</td>
	</tr>
	<tr>
		<td>Number of Shares:</td>
		<td>${stockScore.numShare}</td>
	</tr>
	<tr>
		<td>Cost Per Share:</td>
		<td>${stockScore.costPerShare}</td>
	</tr>
	<tr>
		<td>Total Cost:</td>
		<td>${stockScore.totalCost}</td>
	</tr>
	<tr>
		<td>Target Cost:</td>
		<td>${stockScore.totalTargetCost}</td>
	</tr>
	<tr>
		<td><b>Sell At:</b></td>
		<td><b>${stockScore.sellAt}</b></td>
	</tr>
	<tr>
		<td>Minimum Profit:</td>
		<td>${stockScore.totalProfit}</td>
	</tr>
	<tr>
		<td>10-day low:</td>
		<td>${stockScore.low}</td>
	</tr>
	<tr>
		<td>10-day high:</td>
		<td>${stockScore.high}</td>
	</tr>
</table>
<br/>
<br/>
How Close: ${stockScore.howClose} percent!
<br/>
<br/>
The lower the HOW CLOSE, the better the investment!
<br/>
Recommended HOW CLOSE is 50% and below!
<br/>
<br/>
<a href="MostActive.do">Back To Most Active Page</a>
</body>
</html>