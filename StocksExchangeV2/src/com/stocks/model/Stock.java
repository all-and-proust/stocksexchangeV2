package com.stocks.model;
import java.math.BigDecimal;
import java.util.Date;

public class Stock {
	private String stockSymbol;
	private BigDecimal stockValue;
	private double lastPrice;
	private int frequency;
	private Date closingDate;
	private Date latestMostActive;
	private BigDecimal percentHowClose;
	private BigDecimal targetPrice;
	private double low;
	private double high;
	private BigDecimal totalStockValue;

	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public BigDecimal getStockValue() {
		return stockValue;
	}
	public void setStockValue(BigDecimal stockValue) {
		this.stockValue = stockValue;
	}
	public double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public int getFrequency(){
		return frequency;
	}
	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	public Date getClosingDate(){
		return closingDate;
	}
	public void setClosingDate(Date closingDate){
		this.closingDate = closingDate;
	}
	public Date getLatestMostActive() {
		return latestMostActive;
	}
	public void setLatestMostActive(Date latestMostActive) {
		this.latestMostActive = latestMostActive;
	}
	public BigDecimal getPercentHowClose() {
		return percentHowClose;
	}
	public void setPercentHowClose(BigDecimal percentHowClose) {
		this.percentHowClose = percentHowClose;
	}
	public BigDecimal getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public BigDecimal getTotalStockValue() {
		return totalStockValue;
	}
	public void setTotalStockValue(BigDecimal totalStockValue) {
		this.totalStockValue = totalStockValue;
	}
	
}
