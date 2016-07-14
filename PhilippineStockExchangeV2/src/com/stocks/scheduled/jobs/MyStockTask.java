package com.stocks.scheduled.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class MyStockTask extends TimerTask {
	int ctr = 0;
	StocksBusiness sb = new StocksBusinessImpl();
	public void run(){
		ctr++;
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date recessDate = null;
		Date resumeDate = null;
		String tradingDate = null;
		try{
			tradingDate = dateFormatter2.format(new Date());
			String endTime = tradingDate + " 15:40:00";
			date1 = dateFormatter.parse(endTime);
			
			String recessTime = tradingDate + " 12:04:00";
			recessDate = dateFormatter.parse(recessTime);
			String resumeTime = tradingDate + " 13:26:00";
			resumeDate = dateFormatter.parse(resumeTime);
		} catch (Exception ex){
			System.out.println("3% Exception: " + ex.getMessage());
		}
		if(new Date().getTime() > recessDate.getTime() && new Date().getTime() < resumeDate.getTime()){
			System.out.println("3% Recess Time!");
		} else if(new Date().getTime() > date1.getTime()){
			cancel();
			System.out.println("3% Live Stocks Monitoring is done!");
		} else {
			System.out.println("3% Interval " + ctr);
			sb.sendStocksUpdates(tradingDate);
		}
		
	}
}
