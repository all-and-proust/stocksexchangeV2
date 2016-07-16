package com.stocks.model;
import java.math.BigDecimal;
import java.util.Date;

public class Stock extends StockBase {
	private BigDecimal stockValue;
	private int frequency;
	private Date closingDate;
	private Date latestMostActive;
	private BigDecimal percentHowClose;
	private BigDecimal targetPrice;
	private double low;
	private double high;
	private BigDecimal totalStockValue;
	private int rank;

	public BigDecimal getStockValue() {
		return stockValue;
	}
	public void setStockValue(BigDecimal stockValue) {
		this.stockValue = stockValue;
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
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
