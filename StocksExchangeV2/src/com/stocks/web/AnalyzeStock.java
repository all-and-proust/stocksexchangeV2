package com.stocks.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.stocks.model.*;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class AnalyzeStock extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		//SimpleSelect ss = new SimpleSelect();
		//Map<String,Object> historicalDataMap = ss.getHistoricalData(request.getParameter("symbol"));
		String symbol = request.getParameter("symbol");
		double low = Double.parseDouble(request.getParameter("low"));
		double high = Double.parseDouble(request.getParameter("high"));
		int numShare = Integer.parseInt(request.getParameter("numshare"));
		double costPerShare = Double.parseDouble(request.getParameter("costpershare"));
		
		//out.println("Stock Symbol (hidden): " + request.getParameter("symbol"));
		//out.println("<br/>Low (hidden): " + request.getParameter("low"));
		//out.println("<br/>High (hidden): " + request.getParameter("high"));
		//out.println("<br/>Number of Shares: " + request.getParameter("numshare"));
		//out.println("<br/>Cost Per Share: " + request.getParameter("costpershare"));
		StocksBusiness sb = new StocksBusinessImpl();
		StockScore stockScore = sb.getStockScore(symbol,numShare,costPerShare,low,high);
		//for(Stock s: stocks){
		//	out.println("<br><br>Stock Symbol: " + s.getStockSymbol());
		//	out.println("<br>Stock Frequency: " + s.getFrequency());
		//}
		request.setAttribute("stockScore",stockScore);
		RequestDispatcher view = request.getRequestDispatcher("StockScore.jsp");
		view.forward(request,response);
	}
}