package com.stocks.model.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.stocks.model.BuySellStock;
import com.stocks.model.Stock;

public interface StocksDao {
	public List<Stock> getHighFrequencyStocks();
	public Map<String,Object> getHistoricalData(String symbol);
	public Map<String,Object> importData();
	public int importData(List<Map<String,String>> stocksList);
	public List<Stock> getAllStocks();
	public int removeData(String tradingDate);
	public Map<String,Object> getHistoricalData();
	public Map<String,BigDecimal> getBuySellStocks();
	public List<BuySellStock> getPriceAlert(String isEnabled, String isDeleted);
	public void updatePriceAlert(long id, String isEnabled, String isDeleted);
	public void addPriceAlert(String stockSymbol, double buyPrice, double sellPrice);
}
