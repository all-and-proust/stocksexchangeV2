package com.stocks.web;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.stocks.model.Stock;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class RealTimeMonitoring extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		StocksBusiness sb = new StocksBusinessImpl();
		List<Stock> stocks = sb.getHighFrequencyStocks(true, 1, 10);
		sb.refreshMostActiveStocks(stocks, sb.viewDataFromPSE());
		//remove all elements with -1 percent close
		for (Iterator<Stock> iter = stocks.listIterator(); iter.hasNext(); ) {
			Stock a = iter.next();
		    if (a.getPercentHowClose().intValue() == -1) {
		        iter.remove();
		    }
		}
		//Sort by percent close ascending
		Collections.sort(stocks, new Comparator<Stock>() {
		    @Override
		    public int compare(Stock c1, Stock c2) {
		        return Double.compare(c1.getPercentHowClose().doubleValue(), c2.getPercentHowClose().doubleValue());
		    }
		});
		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(stocks, new TypeToken<List<Stock>>() {}.getType());
		
		JsonArray jsonArray = element.getAsJsonArray();
		response.setContentType("application/json");
		response.getWriter().print(jsonArray);
	}
}
