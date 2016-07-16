package com.stocks.web;

import com.stocks.model.BuySellStock;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PriceAlert extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		StocksBusiness sb = new StocksBusinessImpl();
		String action = request.getParameter("action");
		try{
			if(action!= null){
				long id = Long.parseLong(request.getParameter("id"));
				if(action.equals("enable")){
					sb.updatePriceAlert(id, "Y", "N");
				} else if (action.equals("disable")){
					sb.updatePriceAlert(id, "N", "N");
				} else if (action.equals("delete")){
					sb.updatePriceAlert(id, "N", "Y");
				}
			}
		} catch(NumberFormatException ex){
			System.out.println("Invalid ID: " + ex.getMessage());
		}
		
		List<BuySellStock> enabledPriceAlert = sb.getPriceAlert("Y", "N");
		List<BuySellStock> disabledPriceAlert = sb.getPriceAlert("N", "N");
		
		request.setAttribute("enabledPriceAlert",enabledPriceAlert);
		request.setAttribute("disabledPriceAlert",disabledPriceAlert);

		RequestDispatcher view = request.getRequestDispatcher("PriceAlert.jsp");
		view.forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StocksBusiness sb = new StocksBusinessImpl();
		
		String symbol = request.getParameter("fStockSymbol");
		String buyPrice = request.getParameter("fBuyprice");
		String sellPrice = request.getParameter("fSellprice");
		
		//validate forms
		if(symbol!=null && !symbol.trim().equals("")){
			try {
				if(buyPrice!=null && sellPrice!=null){
					double dBuyPrice = Double.parseDouble(buyPrice.trim());
					double dSellPrice = Double.parseDouble(sellPrice.trim());
					sb.addPriceAlert(symbol, dBuyPrice, dSellPrice);
				}

			} catch(NumberFormatException ex){
				System.out.println("Exception: " + ex.getMessage());
			}
		}
		
		
		List<BuySellStock> enabledPriceAlert = sb.getPriceAlert("Y", "N");
		List<BuySellStock> disabledPriceAlert = sb.getPriceAlert("N", "N");
		
		request.setAttribute("enabledPriceAlert",enabledPriceAlert);
		request.setAttribute("disabledPriceAlert",disabledPriceAlert);

		RequestDispatcher view = request.getRequestDispatcher("PriceAlert.jsp");
		view.forward(request,response);
	}
}