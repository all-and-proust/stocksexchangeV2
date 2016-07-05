package com.stocks.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.stocks.scheduled.jobs.MyStockTask;
import java.util.Date;
import java.util.Timer;

public class LiveStocksMonitoring extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		try{
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
			
			String tradingDate = dateFormatter2.format(new Date());
			
			//String startTime = tradingDate + " 09:40:00";//this is original. put this back after testing.
			String startTime = tradingDate + " 13:10:00";
			Date date = dateFormatter.parse(startTime);

			Timer timer = new Timer();
			
			System.out.println("Trading Date: " + tradingDate);
			System.out.println("Start Time: " + startTime);
			//timer.schedule(new MyStockTask(), date, 10000);
			// 1 second = 1,000 milli seconds
			// 1 minute = 60 seconds = 60,000 milli seconds
			// 10 minutes = 600 seconds = 600,000
			timer.scheduleAtFixedRate(new MyStockTask(), date, 600000);
			System.out.println("Live Stocks monitoring has started!");			
		} catch (Exception ex){
			System.out.println("Exception: " + ex.getMessage());
		}
		RequestDispatcher view = request.getRequestDispatcher("LiveStocksMonitoring.jsp");
		view.forward(request,response);
	}
}