package com.stocks.web;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stocks.model.StockScore;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class AnalyzeStock extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String symbol = request.getParameter("symbol");
		double low = Double.parseDouble(request.getParameter("low"));
		double high = Double.parseDouble(request.getParameter("high"));
		int numShare = Integer.parseInt(request.getParameter("numshare"));
		double costPerShare = Double.parseDouble(request.getParameter("costpershare"));

		StocksBusiness sb = new StocksBusinessImpl();
		StockScore stockScore = sb.getStockScore(symbol,numShare,costPerShare,low,high);
		request.setAttribute("stockScore",stockScore);
		RequestDispatcher view = request.getRequestDispatcher("StockScore.jsp");
		view.forward(request,response);
	}
}