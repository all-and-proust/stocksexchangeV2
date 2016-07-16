package com.stocks.web;

import com.stocks.model.Stock;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MostActive extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StocksBusiness sb = new StocksBusinessImpl();
		List<Stock> stocks = sb.getHighFrequencyStocks(true, 1, 10);
		request.setAttribute("mostActive",stocks);
		RequestDispatcher view = request.getRequestDispatcher("MostActiveStocks.jsp");
		view.forward(request,response);
	}
}