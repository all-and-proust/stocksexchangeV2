package com.stocks.test;

import java.util.Map;

import com.stocks.model.SimpleSelect;

public class StocksDataFeedTest {

	public static void main(String[] args) {
		SimpleSelect ss = new SimpleSelect();
		Map<String,Object> msg = ss.importData();
		System.out.println("Message: " + msg.get("message"));
	}

}
