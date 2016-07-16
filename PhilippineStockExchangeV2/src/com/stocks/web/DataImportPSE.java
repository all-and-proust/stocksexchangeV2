package com.stocks.web;

import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataImportPSE extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StocksBusiness sb = new StocksBusinessImpl();
		sb.removeData(request.getParameter("tradingDate"));
		List<Map<String,String>> liveDataFromPSE = sb.viewDataFromPSE();
		int importResult = sb.importData(liveDataFromPSE, 1, 50);

		request.setAttribute("importResult",importResult);
		RequestDispatcher view = request.getRequestDispatcher("DataImportPSE.jsp");
		view.forward(request,response);
	}
}