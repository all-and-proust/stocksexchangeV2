package com.stocks.test;

import java.util.List;
import java.util.Map;

import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;
import com.stocks.model.dao.StocksDao;
import com.stocks.model.dao.impl.StocksDaoImpl;

public class StocksUpdatesTest {

	public static void main(String[] args) {
		StocksDao ss = new StocksDaoImpl();
		StocksBusiness sb = new StocksBusinessImpl();
		ss.removeData("2016-07-01");
		List<Map<String,String>> liveDataFromPSE = sb.viewDataFromPSE();
		int importResult = ss.importData(liveDataFromPSE);
		if(importResult == 0){
			System.out.println("Successfully imported stocks data.");
		} else {
			System.out.println("Import of stocks data has failed.");
		}
		sb.sendStocksUpdates("2016-07-01");

	}

}
