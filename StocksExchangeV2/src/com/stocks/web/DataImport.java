package com.stocks.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.stocks.model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class DataImport extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();

		Map<String,Object> importResult = new SimpleSelect().importData();

		request.setAttribute("importResult",importResult);
		RequestDispatcher view = request.getRequestDispatcher("DataImport.jsp");
		view.forward(request,response);
	}
}