package com.stocks.model.business.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.stocks.model.GMailAuthenticator;
import com.stocks.model.Stock;
import com.stocks.model.StockScore;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.dao.StocksDao;
import com.stocks.model.dao.impl.StocksDaoImpl;

public class StocksBusinessImpl implements StocksBusiness {
	
	public StockScore getStockScore(String symbol, int numShare, double costPerShare, double low, double high){
		StockScore score = new StockScore();
		score.setSymbol(symbol);
		score.setNumShare(numShare);
		score.setCostPerShare(costPerShare);
		score.setLow(low);
		score.setHigh(high);
		return score.computeMe();
	}

	public List<Map<String,String>> viewDataFromPSE(){
		List<Map<String,String>> stocksList = new ArrayList<Map<String,String>>();
		try {
			// live feed from PSE
			URL url = new URL("http://pse.com.ph/stockMarket/home.html?method=getSecuritiesAndIndicesForPublic&ajax=true");
			URLConnection con = url.openConnection();
			InputStream is =con.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			
			int ctr = 0;
	        while ((line = br.readLine()) != null) {
	        	String[] lineAr = line.split("\\{");
	        	for(String ln : lineAr){
	        		ctr = ctr +1;
	        		if (ctr == 1) 
	        			continue;
	        		if(ln.contains("PSEi"))
	        			break;
	        		
	        		String ln1 = ln.substring(0, ln.length()-2);
	        		String[] xAr =ln1.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	        		
	        		Map<String,String> stockdetails = new HashMap<String,String>();
	        		for (String xl : xAr){
	        			String xlvalue = xl.replace(",", "");
	        			String[] xlvalue2 = xlvalue.split(":");
	        			stockdetails.put(xlvalue2[0].substring(1, xlvalue2[0].length()-1), xlvalue2[1].substring(1, xlvalue2[1].length()-1));
	        			//if the value has time like "07/01/2016 03:20 PM", we need this few lines of codes
	        			if(ctr == 2 && xlvalue2[0].substring(1, xlvalue2[0].length()-1).equals("securityAlias")){
	        				stockdetails.put("securityAlias",xlvalue2[1].substring(1, xlvalue2[1].length()) + ":" + xlvalue2[2].substring(0, xlvalue2[2].length()-1));
	        			}
	        		}
	        		stocksList.add(stockdetails);
	        	}
	        }
		} catch (Exception e){
			System.out.println("Something went wrong: " + e);
		}
		return stocksList;
	}
	
	public void sendStocksUpdates(){
		StocksDao sd = new StocksDaoImpl();
		String tableData = buildBestBuyData(sd.getHighFrequencyStocks());
		final String username = "test.email.july.2016@gmail.com"; // original
		//final String username = "test.email.august.2016@gmail.com";
		final String password = "TestAccount";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,new GMailAuthenticator(username, password));

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("test.email.july.2016@gmail.com"));//original
			//message.setFrom(new InternetAddress("test.email.august.2016@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("benedicto.gualda.deleon@gmail.com"));
			message.setSubject("Stocks Updates - 3% ROI");
			message.setContent(tableData, "text/html" );

			Transport.send(message);

			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println("Message Sent on: " + dateFormatter.format(new Date()));

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (Exception ex){
			System.out.println("Exception thrown: " + ex.getMessage());
		}
	}

	private String buildBestBuyData(List<Stock> stocks){
		String htmltable = 
		"<table border = '1'>" +
		"<tr>" +
		"<td>Rank</td>" +
		"<td>Stock Symbol</td>" +
		"<td>Frequency</td>" +
		"<td>Total Value</td>" +
		"<td>Latest Most Active</td>" +
		"<td>Low</td>" +
		"<td>High</td>" +
		"<td>Buy Price</td>" +
		"<td>Sell Price</td>" +
		"<td>How Close</td>" +
	    "</tr>";
		
		DecimalFormat df = new DecimalFormat("#,###.00");
		int ctr = 0;
		for(Stock s: stocks){
			ctr++;
			if (s.getPercentHowClose().doubleValue() > 0.0 && s.getPercentHowClose().doubleValue() <= 50.0 ){
				htmltable = htmltable +
						"<tr>" +
						"<td>" + ctr + "</td>" +
						"<td>" + s.getStockSymbol() +"</td>" +
						"<td>" + s.getFrequency() +"</td>" +
						"<td>" + df.format(s.getTotalStockValue()) + "</td>" +
						"<td>" + s.getLatestMostActive() + "</td>" +
						"<td>" + s.getLow() + "</td>" +
						"<td>" + s.getHigh() + "</td>" +
						"<td>" + s.getLastPrice() + "</td>" +
						"<td>" + s.getTargetPrice() + "</td>" +
						"<td>" + s.getPercentHowClose() + "</td>" +
					    "</tr>";		
			}
		}
		htmltable = htmltable + "</table>";
		return htmltable;
	}

	public void stocksMonitoring(String tradingDate){
		StocksDao sd = new StocksDaoImpl();
		sd.removeData(tradingDate);
		int importResult = sd.importData(viewDataFromPSE());
		if(importResult == 0){
			System.out.println("Successfully imported stocks data.");
		} else {
			System.out.println("Import of stocks data has failed.");
		}
		sendStocksUpdates();
	}
}
