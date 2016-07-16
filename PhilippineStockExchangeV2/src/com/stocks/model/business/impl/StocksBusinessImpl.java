package com.stocks.model.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.stocks.model.BuySellStock;
import com.stocks.model.GMailAuthenticator;
import com.stocks.model.Stock;
import com.stocks.model.StockBase;
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
	
	public void sendStocksUpdates(String tradingDate){
		StocksDao sd = new StocksDaoImpl();
		sd.removeStocksData(tradingDate);
		List<Map<String,String>> stocksList = viewDataFromPSE();
		int importResult = sd.importLiveData(stocksList, 1, 50);
		if(importResult == 0){
			System.out.println("Successfully imported stocks data.");
		} else {
			System.out.println("Import of stocks data has failed.");
		}
		
		String tableData = buildBestBuyData(getHighFrequencyStocks(true, 1, 10));
		
		Map<String,List<StockBase>> buySell= adviseBuySell(stocksList);
		
		String buyTableData = buildBuyStocksData(buySell.get("BUY"));
		String sellTableData = buildSellStocksData(buySell.get("SELL"));
		
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
			message.setContent(tableData + sellTableData + buyTableData, "text/html" );

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

	private String buildSellStocksData(List<StockBase> sellStocks){
		String htmltable = 
		"<br/><table border = '1'>" +
		"<tr>" +
		"<td>Stock Symbol</td>" +
		"<td>Best Bid (SELL)</td>" +
	    "</tr>";
		for(StockBase s: sellStocks){
				htmltable = htmltable +
						"<tr>" +
						"<td>" + s.getStockSymbol() +"</td>" +
						"<td>" + s.getLastPrice() + "</td>" +
					    "</tr>";
		}
		htmltable = htmltable + "</table>";
		return htmltable;
	}

	private String buildBuyStocksData(List<StockBase> sellStocks){
		String htmltable = 
		"<br/><table border = '1'>" +
		"<tr>" +
		"<td>Stock Symbol</td>" +
		"<td>Best Offer (BUY)</td>" +
	    "</tr>";
		for(StockBase s: sellStocks){
				htmltable = htmltable +
						"<tr>" +
						"<td>" + s.getStockSymbol() +"</td>" +
						"<td>" + s.getLastPrice() + "</td>" +
					    "</tr>";		
		}
		htmltable = htmltable + "</table>";
		return htmltable;
	}

	public Map<String,List<StockBase>> adviseBuySell(List<Map<String,String>> stocksList){
		StocksDao sd = new StocksDaoImpl();
		Map<String,BigDecimal> buySellMap = sd.getBuySellStocks();
		Map<String,List<StockBase>> recommendedBuySell = new HashMap<String,List<StockBase>>();
		List<StockBase> recommendedSell = new ArrayList<StockBase>();
		List<StockBase> recommendedBuy = new ArrayList<StockBase>();
		
		for(Map<String,String> sds : stocksList){
			if(sds.get("lastTradedPrice").equals("DATE")){
				; // do nothing. just skip this first element of List
	        } else {	        	
	        	String stockSymbol = sds.get("securitySymbol");
	        	double lastTradedPrice = Double.parseDouble(sds.get("lastTradedPrice"));
	        	if(buySellMap.containsKey(stockSymbol+"_SELL") && lastTradedPrice >= buySellMap.get(stockSymbol+"_SELL").doubleValue()){
	        		//sell
	        		StockBase sb = new StockBase();
	        		sb.setStockSymbol(stockSymbol);
	        		sb.setLastPrice(lastTradedPrice);
	        		recommendedSell.add(sb);
	        	}
	        	
	        	if(buySellMap.containsKey(stockSymbol+"_BUY") && lastTradedPrice <= buySellMap.get(stockSymbol+"_BUY").doubleValue() * 0.97){
	        		//buy again
	        		StockBase sb = new StockBase();
	        		sb.setStockSymbol(stockSymbol);
	        		sb.setLastPrice(lastTradedPrice);
	        		recommendedBuy.add(sb);
	        		
	        	}
	        }
		}
		recommendedBuySell.put("BUY", recommendedBuy);
		recommendedBuySell.put("SELL", recommendedSell);
		return recommendedBuySell;
	}

	public List<Stock> getAllStocks(){
		StocksDao sd = new StocksDaoImpl();
		return sd.getAllPSEStocks();
	}
	
	public Map<String,Object> importData(){
		StocksDao sd = new StocksDaoImpl();
		Map<String,Object> resultsMap = new HashMap<String,Object>();
        try
        {
			  File directory = new File("C:/stocksdatafeed/csv/in");
			  File[] myarray;  
			  myarray=directory.listFiles();
			  for (int j = 0; j < myarray.length; j++)
			  {
			         File path=myarray[j];
			         FileReader fr = new FileReader(path);
			         BufferedReader br = new BufferedReader(fr);
			         String strLine = "";
			         StringTokenizer st = null;
			         int lineNumber = 0, tokenNumber = 0;
               
			         //read comma separated file line by line
			         while( (strLine = br.readLine()) != null)
			         {
                        lineNumber++;
                       
                        if(lineNumber == 1)
                        	continue;
                        //break comma separated line using ","
                        st = new StringTokenizer(strLine, ",");
                       
                        String symbol = null;
                        double lastPrice = 0.0;
                        long stockValue = 0;
                        String tradingDate = null;
                        while(st.hasMoreTokens())
                        {
                                //display csv values
                                tokenNumber++;
                                String nextToken = st.nextToken();
                                if(tokenNumber == 1)
                                	continue;
                                
                                if(tokenNumber == 5){
                                	String[] nextTokenArr = nextToken.split("/");
                                	if(nextTokenArr.length > 1){
                                		nextToken = nextTokenArr[2] + "-" + nextTokenArr[0] +"-" + nextTokenArr[1];
                                		tradingDate = nextToken;
                                	}
                                }
                                
                                if(tokenNumber == 2){
                                	symbol = nextToken;
                                } else if(tokenNumber == 3){
                                	lastPrice = Double.parseDouble(nextToken);
                                } else if(tokenNumber == 4){
                                	stockValue = Long.parseLong(nextToken);
                                }
                        }
                       
                        //reset token number
                        tokenNumber = 0;
                        
                        sd.importStocksData(symbol, lastPrice, tradingDate);
			         }
			         br.close(); //release the resources
					 break;//we want only to process the first file.
			  }

			  //move the read files to archive directory
			  if(myarray.length == 0){
				resultsMap.put("message","No Data to import. Data File not found.");
			  }

			  for (int j = 0; j < myarray.length; j++)
			  {
			    File file=myarray[j];

			  	// Destination directory
			  	File dir = new File("C:/stocksdatafeed/csv/archive");

			  	// Move file to new directory
			  	boolean success = file.renameTo(new File(dir, file.getName()));
			  	if (!success) {
			  		resultsMap.put("message",file.getName() + " was not imported successfully!");
			  	} else {
					resultsMap.put("message",file.getName() + " was imported successfully!");
				}
				
				break;//process only single file
			  }
        }
        catch(Exception e)
        {
                System.out.println("Exception while reading csv file: " + e);                  
        }
        return resultsMap;
	}

	public int importData(List<Map<String,String>> stocksList, int lowRange, int highRange){
		StocksDao sd = new StocksDaoImpl();
		return sd.importLiveData(stocksList, lowRange, highRange);
	}

	public int removeData(String tradingDate){
		StocksDao sd = new StocksDaoImpl();
		return sd.removeStocksData(tradingDate);
	}

	public Map<String,Object> getHistoricalData(String symbol, int lowRange, int highRange){
		StocksDao sd = new StocksDaoImpl();
		return sd.getPSEHistoricalData(symbol, lowRange, highRange);
	}
	
	public void updatePriceAlert(long id, String isEnabled, String isDeleted){
		StocksDao sd = new StocksDaoImpl();
		sd.updateBuySellStocks(id, isEnabled, isDeleted);
	}
	
	public List<BuySellStock> getPriceAlert(String isEnabled, String isDeleted){
		StocksDao sd = new StocksDaoImpl();
		return sd.getBuySellStocks(isEnabled, isDeleted);
	}
	
	public void addPriceAlert(String stockSymbol, double buyPrice, double sellPrice){
		StocksDao sd = new StocksDaoImpl();
		sd.addBuySellStock(stockSymbol, buyPrice, sellPrice);
	}
	
	public List<Stock> getHighFrequencyStocks(boolean isFullData, int lowRange, int highRange) {
		StocksDao sd = new StocksDaoImpl();
		List<Stock> stocks = sd.getHighFrequencyStocks(lowRange, highRange);
		if(isFullData){
			Map<String, Object> historicalmap = sd.getHistoricalData(lowRange, highRange);
			int rank = 0;
			for(Stock stock: stocks){
				rank = rank + 1;
				stock.setRank(rank);
				Date latestMostActive = (Date) historicalmap.get(stock.getStockSymbol()+"latestMostActive");
				stock.setLatestMostActive(latestMostActive);
				List<Stock> stocksList = (List<Stock>)historicalmap.get(stock.getStockSymbol());
				int ctr = 0;
				double low1 = 0.0;
				double high1 = 0.0;
				double cps = 0.0;
				for(Stock s: stocksList){
					ctr++;
					if(ctr == 1){
						low1 = s.getLastPrice();
					}
					if(ctr == stock.getFrequency()){
						high1 = s.getLastPrice();
					}
					if(latestMostActive.getTime() == s.getClosingDate().getTime()){
						cps = s.getLastPrice();
					}
				}
				double targetprice = cps * 1.03;
				BigDecimal howClose = new BigDecimal(-1);
				if(high1 != low1)
					howClose = new BigDecimal((targetprice-low1)/(high1-low1) * 100).setScale(2, RoundingMode.CEILING);
				stock.setPercentHowClose(howClose);
				stock.setHigh(high1);
				stock.setLow(low1);
				stock.setLastPrice(cps);
				stock.setTargetPrice(new BigDecimal(targetprice).setScale(2, RoundingMode.CEILING));
			}
			return stocks;
		} else {
			return stocks;
		}
	}

	public void refreshMostActiveStocks(List<Stock> mostActiveStocks, List<Map<String,String>> freshStocks){		
		Map<String,Double> freshStockMap = new HashMap<String,Double>();
		String tradingDateStr = "";
		Date tradingDate = null;
		//loop through the fresh stocks
		for(Map<String,String> sds : freshStocks){
			if(sds.get("lastTradedPrice").equals("DATE")){
				String[] nextTokenArr = sds.get("securityAlias").substring(0, 10).split("/");
				if(nextTokenArr.length > 1){
					tradingDateStr = nextTokenArr[2] + "-" + nextTokenArr[0] +"-" + nextTokenArr[1];
				}
	        } else {	        	
	        	String stockSymbol = sds.get("securitySymbol");
	        	double lastTradedPrice = Double.parseDouble(sds.get("lastTradedPrice"));
	        	freshStockMap.put(stockSymbol, lastTradedPrice);
	        }
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try{
			tradingDate = formatter.parse(tradingDateStr);
			for(Stock mostActiveStock: mostActiveStocks){
				if(freshStockMap.get(mostActiveStock.getStockSymbol())!=null){
					double lastPrice = freshStockMap.get(mostActiveStock.getStockSymbol()).doubleValue();
					
					mostActiveStock.setLastPrice(lastPrice);
					double low1 = (lastPrice < mostActiveStock.getLow()) ? lastPrice : mostActiveStock.getLow();
					mostActiveStock.setLow(low1);
					double high1 = (lastPrice > mostActiveStock.getHigh()) ? lastPrice : mostActiveStock.getHigh();
					mostActiveStock.setHigh(high1);
					double targetprice = lastPrice * 1.03;
					mostActiveStock.setTargetPrice(new BigDecimal(targetprice).setScale(2, RoundingMode.CEILING));
					BigDecimal howClose = new BigDecimal(-1);
					if(high1 != low1){
						howClose = new BigDecimal((targetprice-low1)/(high1-low1) * 100).setScale(2, RoundingMode.CEILING);
					}
					mostActiveStock.setPercentHowClose(howClose);
					
					if(mostActiveStock.getLatestMostActive().getTime() != tradingDate.getTime()){
						int frequency = mostActiveStock.getFrequency();
						mostActiveStock.setFrequency(frequency + 1);
						mostActiveStock.setLatestMostActive(tradingDate);
					}					
				}
			}
		} catch (ParseException ex){
			System.out.println("Error parsing trading date.");
		}
	}
}
