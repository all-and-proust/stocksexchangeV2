package com.stocks.model.dao;

import java.util.List;
import java.util.Map;
import com.stocks.model.Stock;

public interface StocksDao {
	public List<Stock> getHighFrequencyStocks();
	public Map<String,Object> getHistoricalData(String symbol);
	public Map<String,Object> importData();
	public int importData(List<Map<String,String>> stocksList);
	public List<Stock> getAllStocks();
	public int removeData(String tradingDate);
	public Map<String,Object> getHistoricalData();
}
