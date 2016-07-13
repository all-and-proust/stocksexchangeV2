<%@ page import="java.util.*,com.stocks.model.*,java.text.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Price Alert</title>
</head>
<body>
<% 
List<BuySellStock> enabledPriceAlert = (List<BuySellStock>)request.getAttribute("enabledPriceAlert");
List<BuySellStock> disabledPriceAlert = (List<BuySellStock>)request.getAttribute("disabledPriceAlert");
%>
<a href="MostActive.do">Most Active</a> | 
<a href="DataImportPSEView.do">Import Data Directly from PSE!</a>
<br/>
<br/>
<h3>Enabled Price Alert</h3>
<table border = "1">
	<tr>
		<th>Stock Symbol</th>
		<th>Buy Price</th>
		<th>Sell Price</th>
		<th>Date Created</th>
		<th>Action</th>
	</tr>
	<% 
	for(BuySellStock s: enabledPriceAlert){
		%>
		<tr>
			<td><%= s.getStockSymbol() %></td>
			<td><%= s.getBuyPrice() %></td>
			<td><%= s.getSellPrice() %></td>
			<td><%= s.getCreateDate() %></td>
			<td><a href="PriceAlert.do?action=disable&id=<%= s.getId() %>">Disable</a> | <a href="PriceAlert.do?action=delete&id=<%= s.getId() %>">Delete</a></td>
		</tr>
		<%
	}
	%>
</table>

<br/>
<br/>
<form method="POST" action="PriceAlert.do">
<table>
<tr>
	<td>Stock Symbol:</td>
	<td><input type="text" size="12" maxlength="12" name="fStockSymbol" /></td>
</tr>
<tr>
	<td>BUY price:</td>
	<td><input type="text" size="12" maxlength="12" name="fBuyprice" /></td>
</tr>
<tr>
	<td>SELL price:</td>
	<td><input type="text" size="12" maxlength="12" name="fSellprice" /></td>
</tr>
<tr>
	<td><input type="submit" value="Add"/></td>
	<td>&nbsp;</td>
</tr>
</table>
</form>
<br/>
<br/>
<h3>Disabled Price Alert</h3>
<table border = "1">
	<tr>
		<th>Stock Symbol</th>
		<th>Buy Price</th>
		<th>Sell Price</th>
		<th>Date Created</th>
		<th>Action</th>
	</tr>
	<% 
	for(BuySellStock s: disabledPriceAlert){
		%>
		<tr>
			<td><%= s.getStockSymbol() %></td>
			<td><%= s.getBuyPrice() %></td>
			<td><%= s.getSellPrice() %></td>
			<td><%= s.getCreateDate() %></td>
			<td><a href="PriceAlert.do?action=enable&id=<%= s.getId() %>">Enable</a> | <a href="PriceAlert.do?action=delete&id=<%= s.getId() %>">Delete</a></td>
		</tr>
		<%
	}
	%>
</table>

</body>
</html>