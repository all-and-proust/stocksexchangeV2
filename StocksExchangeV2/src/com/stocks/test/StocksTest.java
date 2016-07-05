package com.stocks.test;

import java.util.List;

import com.stocks.model.SimpleSelect;
import com.stocks.model.Stock;

public class StocksTest {

	public static void main(String[] args) {
		SimpleSelect ss = new SimpleSelect();
		List<Stock> stocksList = ss.getAllStocks();
		
		System.out.println("Symbol|Frequency");
		for(Stock stock: stocksList){
			System.out.println(stock.getStockSymbol()
					+ "|"
					+ stock.getFrequency());
		}
	}
}
