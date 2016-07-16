<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Real-time Stocks Monitoring</title>
<script src = "jquery-3.0.0.min.js"></script>
<script>
(function worker() {
	  $("#errordiv").hide();
	  $("#tablediv").hide();
	  $.ajax({
		type: 'GET',
	    url: 'RealTimeMonitor.do',
	    success: function(responseJson) {

            if(responseJson!=null){
                $("#stocktable").find("tr:gt(0)").remove();
                var table1 = $("#stocktable");
                $.each(responseJson, function(key,value) { 
                     var rowNew = $("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
                        rowNew.children().eq(0).text(value['rank']); 
                        rowNew.children().eq(1).text(value['stockSymbol']); 
                        rowNew.children().eq(2).text(value['frequency']); 
                        rowNew.children().eq(3).text(value['totalStockValue']); 
                        rowNew.children().eq(4).text(value['latestMostActive']); 
                        rowNew.children().eq(5).text(value['low']); 
                        rowNew.children().eq(6).text(value['high']);
                        rowNew.children().eq(7).text(value['lastPrice']);
                        rowNew.children().eq(8).text(value['targetPrice']);
                        rowNew.children().eq(9).text(value['percentHowClose']);
                        rowNew.appendTo(table1);
                });
                }
              $("#tablediv").show();
	    },
	    complete: function() {
	      // Schedule the next request when the current one's complete
	      setTimeout(worker, 15000);
	    },
	    error: function(jqXHR, textStatus, errorThrown){
	    	console.log("Something really bad happened " + textStatus);
	    	$("#errordiv").show();
	    	$("#ajaxResponse").html(jqXHR.responseText);
	    }
	  });
	  
	})();
</script>
</head>
<body>
<a href="MostActive.do">Most Active</a> | 
<a href="PriceAlert.do">Price Alert</a> | 
<a href="DataImportPSEView.do">Import Data Directly from PSE!</a>
<br/>
<br/>
<h3>Real-time Stocks Monitoring</h3>
<br/>
<div id="tablediv">
<table border="1" cellspacing="0" id="stocktable"> 
    <tr> 
        <th scope="col">Rank</th> 
        <th scope="col">Stock Symbol</th>
        <th scope="col">Frequency</th>
        <th scope="col">Total Value</th>
        <th scope="col">Latest Most Active</th> 
        <th scope="col">Low</th> 
        <th scope="col">High</th>
        <th scope="col">Buy Price</th>
        <th scope="col">Sell Price</th>
        <th scope="col">How Close</th>
    </tr> 
</table>
</div>

<div id="errordiv">
        <fieldset>
            <legend>Response from jQuery Ajax Request</legend>
                 <div id="ajaxResponse"></div>
        </fieldset>
</div>
</body>
</html>