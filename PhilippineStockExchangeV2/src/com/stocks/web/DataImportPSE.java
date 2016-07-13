package com.stocks.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.stocks.model.*;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;
import com.stocks.model.dao.StocksDao;
import com.stocks.model.dao.impl.StocksDaoImpl;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class DataImportPSE extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StocksDao ss = new StocksDaoImpl();
		StocksBusiness sb = new StocksBusinessImpl();
		ss.removeData(request.getParameter("tradingDate"));
		List<Map<String,String>> liveDataFromPSE = sb.viewDataFromPSE();
		int importResult = ss.importData(liveDataFromPSE);

		request.setAttribute("importResult",importResult);
		RequestDispatcher view = request.getRequestDispatcher("DataImportPSE.jsp");
		view.forward(request,response);
	}
}