package com.stocks.web;

import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataImport extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StocksBusiness sb = new StocksBusinessImpl();
		Map<String,Object> importResult = sb.importData();

		request.setAttribute("importResult",importResult);
		RequestDispatcher view = request.getRequestDispatcher("DataImport.jsp");
		view.forward(request,response);
	}
}