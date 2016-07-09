package com.stocks.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.stocks.model.dao.StocksDao;
import com.stocks.model.dao.impl.StocksDaoImpl;
import java.util.Map;

public class HistoricalData extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		StocksDao ss = new StocksDaoImpl();
		Map<String,Object> historicalDataMap = ss.getHistoricalData(request.getParameter("symbol"));
		//out.println("Stock Symbol: " + );		
		//for(Stock s: stocks){
		//	out.println("<br><br>Stock Symbol: " + s.getStockSymbol());
		//	out.println("<br>Stock Frequency: " + s.getFrequency());
		//}
		request.setAttribute("historicalMap",historicalDataMap);
		RequestDispatcher view = request.getRequestDispatcher("HistoricalData.jsp");
		view.forward(request,response);
	}
}