package com.stocks.test;

import java.util.Map;

import com.stocks.model.business.StocksBusiness;
import com.stocks.model.business.impl.StocksBusinessImpl;

public class StocksDataFeedTest {

	public static void main(String[] args) {
		StocksBusiness ss = new StocksBusinessImpl();
		Map<String,Object> msg = ss.importData();
		System.out.println("Message: " + msg.get("message"));
	}

}
