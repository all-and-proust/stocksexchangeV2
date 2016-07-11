package com.stocks.web;

import javax.servlet.*;
import javax.servlet.http.*;

import com.stocks.model.BuySellStock;
import com.stocks.model.dao.StocksDao;
import com.stocks.model.dao.impl.StocksDaoImpl;

import java.io.*;
import java.util.List;

public class PriceAlert extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		StocksDao sd = new StocksDaoImpl();
		String action = request.getParameter("action");
		try{
			if(action!= null){
				long id = Long.parseLong(request.getParameter("id"));
				if(action.equals("enable")){
					sd.updatePriceAlert(id, "Y", "N");
				} else if (action.equals("disable")){
					sd.updatePriceAlert(id, "N", "N");
				} else if (action.equals("delete")){
					sd.updatePriceAlert(id, "N", "Y");
				}
			}
		} catch(NumberFormatException ex){
			System.out.println("Invalid ID: " + ex.getMessage());
		}
		
		List<BuySellStock> enabledPriceAlert = sd.getPriceAlert("Y", "N");
		List<BuySellStock> disabledPriceAlert = sd.getPriceAlert("N", "N");
		
		request.setAttribute("enabledPriceAlert",enabledPriceAlert);
		request.setAttribute("disabledPriceAlert",disabledPriceAlert);

		RequestDispatcher view = request.getRequestDispatcher("PriceAlert.jsp");
		view.forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StocksDao sd = new StocksDaoImpl();
		
		String symbol = request.getParameter("fStockSymbol");
		String buyPrice = request.getParameter("fBuyprice");
		String sellPrice = request.getParameter("fSellprice");
		
		//validate forms
		if(symbol!=null && !symbol.trim().equals("")){
			try {
				if(buyPrice!=null && sellPrice!=null){
					double dBuyPrice = Double.parseDouble(buyPrice.trim());
					double dSellPrice = Double.parseDouble(sellPrice.trim());
					sd.addPriceAlert(symbol, dBuyPrice, dSellPrice);
				}

			} catch(NumberFormatException ex){
				System.out.println("Exception: " + ex.getMessage());
			}
		}
		
		
		List<BuySellStock> enabledPriceAlert = sd.getPriceAlert("Y", "N");
		List<BuySellStock> disabledPriceAlert = sd.getPriceAlert("N", "N");
		
		request.setAttribute("enabledPriceAlert",enabledPriceAlert);
		request.setAttribute("disabledPriceAlert",disabledPriceAlert);

		RequestDispatcher view = request.getRequestDispatcher("PriceAlert.jsp");
		view.forward(request,response);
	}
}