package com.stocks.model.business;

import java.util.List;
import java.util.Map;

import com.stocks.model.StockBase;
import com.stocks.model.StockScore;

public interface StocksBusiness {
	public StockScore getStockScore(String symbol, int numShare, double costPerShare, double low, double high);
	public List<Map<String,String>> viewDataFromPSE();
	public void sendStocksUpdates(String tradingDate);
	public Map<String,List<StockBase>> adviseBuySell(List<Map<String,String>> stocksList);
}
