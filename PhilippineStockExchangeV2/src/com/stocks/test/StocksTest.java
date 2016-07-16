package com.stocks.test;

import java.util.List;

import com.stocks.model.Stock;
import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class StocksTest {

	public static void main(String[] args) {
		StocksBusiness sb = new StocksBusinessImpl();
		List<Stock> stocksList = sb.getAllStocks();
		
		System.out.println("Symbol|Frequency");
		for(Stock stock: stocksList){
			System.out.println(stock.getStockSymbol()
					+ "|"
					+ stock.getFrequency());
		}
	}
}
