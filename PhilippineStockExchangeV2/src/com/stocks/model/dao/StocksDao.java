package com.stocks.model.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.stocks.model.BuySellStock;
import com.stocks.model.Stock;

public interface StocksDao {
	public List<Stock> getHighFrequencyStocks(int lowRange, int highRange);
	public Map<String,Object> getPSEHistoricalData(String symbol, int lowRange, int highRange);
	public Map<String,Object> getHistoricalData(int lowRange, int highRange);
	public int importStocksData(String symbol, double lastPrice, String tradingDate);
	public int importLiveData(List<Map<String,String>> stocksList, int lowRange, int highRange);
	public List<Stock> getAllPSEStocks();
	public int removeStocksData(String tradingDate);
	public Map<String,BigDecimal> getBuySellStocks();
	public List<BuySellStock> getBuySellStocks(String isEnabled, String isDeleted);
	public void updateBuySellStocks(long id, String isEnabled, String isDeleted);
	public void addBuySellStock(String stockSymbol, double buyPrice, double sellPrice);
}
