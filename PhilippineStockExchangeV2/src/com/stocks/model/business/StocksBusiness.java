package com.stocks.model.business;

import java.util.List;
import java.util.Map;

import com.stocks.model.BuySellStock;
import com.stocks.model.Stock;
import com.stocks.model.StockBase;
import com.stocks.model.StockScore;

public interface StocksBusiness {
	public List<Stock> getAllStocks();
	public Map<String,Object> importData();
	public int importData(List<Map<String,String>> stocksList, int lowRange, int highRange);
	public int removeData(String tradingDate);
	public Map<String,Object> getHistoricalData(String symbol, int lowRange, int highRange);
	public void updatePriceAlert(long id, String isEnabled, String isDeleted);
	public List<BuySellStock> getPriceAlert(String isEnabled, String isDeleted);
	public void addPriceAlert(String stockSymbol, double buyPrice, double sellPrice);
	public StockScore getStockScore(String symbol, int numShare, double costPerShare, double low, double high);
	public List<Map<String,String>> viewDataFromPSE();
	public void sendStocksUpdates(String tradingDate);
	public Map<String,List<StockBase>> adviseBuySell(List<Map<String,String>> stocksList);
	public List<Stock> getHighFrequencyStocks(boolean isFullData, int lowRange, int highRange);
	public void refreshMostActiveStocks(List<Stock> mostActiveStocks, List<Map<String,String>> freshStocks);
}
