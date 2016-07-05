package com.stocks.scheduled.jobs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class MyStockTaskScheduler {

	public static void main(String[] args) throws ParseException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
		
		String tradingDate = dateFormatter2.format(new Date());
		
		//String startTime = tradingDate + " 09:40:00"; put back this original value after testing
		String startTime = tradingDate + " 22:35:00";
		Date date = dateFormatter.parse(startTime);

		Timer timer = new Timer();
		
		System.out.println("Trading Date: " + tradingDate);
		System.out.println("Start Time: " + startTime);
		//timer.schedule(new MyStockTask(), date, 10000);
		// 1 second = 1,000 milli seconds
		// 1 minute = 60 seconds = 60,000 milli seconds
		// 10 minutes = 600 seconds = 600,000
		timer.scheduleAtFixedRate(new MyStockTask(), date, 120000);
		System.out.println("Stocks monitoring has started!");
	}

}
