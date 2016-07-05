package com.stocks.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.stocks.model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class DataImportPSEView extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		List<Map<String,String>> liveDataFromPSE = new SimpleSelect().viewDataFromPSE();

		request.setAttribute("liveDataFromPSE",liveDataFromPSE);
		RequestDispatcher view = request.getRequestDispatcher("DataImportPSEView.jsp");
		view.forward(request,response);
	}
}