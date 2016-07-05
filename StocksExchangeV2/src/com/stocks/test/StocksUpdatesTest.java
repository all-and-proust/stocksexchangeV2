package com.stocks.test;

import java.util.List;
import java.util.Map;

import com.stocks.model.SimpleSelect;

public class StocksUpdatesTest {

	public static void main(String[] args) {
		SimpleSelect ss = new SimpleSelect();
		ss.removeData("2016-07-01");
		List<Map<String,String>> liveDataFromPSE = ss.viewDataFromPSE();
		int importResult = ss.importData(liveDataFromPSE);
		if(importResult == 0){
			System.out.println("Successfully imported stocks data.");
		} else {
			System.out.println("Import of stocks data has failed.");
		}
		ss.sendStocksUpdates();

	}

}
