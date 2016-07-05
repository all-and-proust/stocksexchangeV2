package com.stocks.scheduled.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.stocks.model.SimpleSelect;

public class MyStockTask extends TimerTask {
	int ctr = 0;
	SimpleSelect ss = new SimpleSelect();
	public void run(){
		ctr++;
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		String tradingDate = null;
		try{
			tradingDate = dateFormatter2.format(new Date());
			String endTime = tradingDate + " 15:40:00";
			date1 = dateFormatter.parse(endTime);
			
			System.out.println("Trading Date: " + tradingDate);
			System.out.println("End Time: " + endTime);
		} catch (Exception ex){
			System.out.println("Exception: " + ex.getMessage());
		}
		if(new Date().getTime() > date1.getTime()){
			cancel();
			System.out.println("Live Stocks Monitoring is done!");
		} else {
			System.out.println("Interval " + ctr);
			ss.stocksMonitoring(tradingDate);
		}
		
	}
}
