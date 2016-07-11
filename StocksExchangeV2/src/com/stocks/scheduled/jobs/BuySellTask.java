package com.stocks.scheduled.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.stocks.model.StockBase;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class BuySellTask extends TimerTask {
	StocksBusiness sb = new StocksBusinessImpl();
	public void run(){
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date recessDate = null;
		Date resumeDate = null;
		String tradingDate = null;
		try{
			tradingDate = dateFormatter2.format(new Date());
			String endTime = tradingDate + " 15:35:00";
			date1 = dateFormatter.parse(endTime);
			
			String recessTime = tradingDate + " 12:00:00";
			recessDate = dateFormatter.parse(recessTime);
			String resumeTime = tradingDate + " 13:30:00";
			resumeDate = dateFormatter.parse(resumeTime);
		} catch (Exception ex){
			System.out.println("3% Exception: " + ex.getMessage());
		}
		if(new Date().getTime() > recessDate.getTime() && new Date().getTime() < resumeDate.getTime()){
			System.out.println("3% Buy and Sell Monitoring is currently on recess!");
		} else if(new Date().getTime() > date1.getTime()){
			cancel();
			System.out.println("3% Buy and Sell Monitoring is done!");
		} else {
			Map<String,List<StockBase>> buySell= sb.adviseBuySell(sb.viewDataFromPSE());
			if(buySell.get("BUY").isEmpty()){
				System.out.println("3% No best buy!");
			} else {
				List<StockBase> buyStocks = buySell.get("BUY");
				System.out.println("3% Recommended stocks to BUY: ");
				for(StockBase s: buyStocks){
					System.out.println("(3%) " + s.getStockSymbol() + ": " + s.getLastPrice());
				}
			}
			if(buySell.get("SELL").isEmpty()){
				System.out.println("3% No best sell!");
			} else {
				List<StockBase> buyStocks = buySell.get("SELL");
				System.out.println("3% Recommended stocks to SELL: ");
				for(StockBase s: buyStocks){
					System.out.println("(3%) " + s.getStockSymbol() + ": " + s.getLastPrice());
				}
			}		
		}
		
	}
}
