package com.stocks.test;

import java.util.List;
import java.util.Map;

import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class StocksUpdatesTest {

	public static void main(String[] args) {
		StocksBusiness sb = new StocksBusinessImpl();
		sb.removeData("2016-07-01");
		List<Map<String,String>> liveDataFromPSE = sb.viewDataFromPSE();
		int importResult = sb.importData(liveDataFromPSE, 1, 50);
		if(importResult == 0){
			System.out.println("Successfully imported stocks data.");
		} else {
			System.out.println("Import of stocks data has failed.");
		}
		sb.sendStocksUpdates("2016-07-01");

	}

}
