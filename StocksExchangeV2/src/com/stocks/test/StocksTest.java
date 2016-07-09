package com.stocks.test;

import java.util.List;

import com.stocks.model.Stock;
import com.stocks.model.dao.StocksDao;
import com.stocks.model.dao.impl.StocksDaoImpl;

public class StocksTest {

	public static void main(String[] args) {
		StocksDao ss = new StocksDaoImpl();
		List<Stock> stocksList = ss.getAllStocks();
		
		System.out.println("Symbol|Frequency");
		for(Stock stock: stocksList){
			System.out.println(stock.getStockSymbol()
					+ "|"
					+ stock.getFrequency());
		}
	}
}
