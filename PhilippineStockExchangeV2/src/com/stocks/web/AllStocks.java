package com.stocks.web;


import com.stocks.model.Stock;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AllStocks extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StocksBusiness sb = new StocksBusinessImpl();
		List<Stock> stocks = sb.getAllStocks();

		request.setAttribute("allStocks",stocks);
		RequestDispatcher view = request.getRequestDispatcher("AllStocks.jsp");
		view.forward(request,response);
	}
}