package com.stocks.model;

public class StockScore {
	private String symbol;
	private double costPerShare;
	private int numShare;
	private double totalCost;
	private double totalTargetCost;
	private double sellAt;
	private double totalProfit;
	private double low;
	private double high;
	private double howClose;
	public static final double ROI = 1.03;
	public static final double BUY_SELL_CHARGE = 50.0;

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getCostPerShare() {
		return costPerShare;
	}
	public void setCostPerShare(double costPerShare) {
		this.costPerShare = costPerShare;
	}
	public int getNumShare() {
		return numShare;
	}
	public void setNumShare(int numShare) {
		this.numShare = numShare;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getTotalTargetCost() {
		return totalTargetCost;
	}
	public void setTotalTargetCost(double totalTargetCost) {
		this.totalTargetCost = totalTargetCost;
	}
	public double getSellAt() {
		return sellAt;
	}
	public void setSellAt(double sellAt) {
		this.sellAt = sellAt;
	}
	public double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
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
	public double getHowClose() {
		return howClose;
	}
	public void setHowClose(double howClose) {
		this.howClose = howClose;
	}
	
	public StockScore computeMe(){
		this.totalCost = this.costPerShare * this.numShare;
		this.totalTargetCost = this.totalCost * ROI;
		this.sellAt = this.costPerShare * ROI;
		this.totalProfit = this.totalTargetCost - this.totalCost - BUY_SELL_CHARGE;
		this.howClose = ((this.sellAt-this.low)/(this.high-this.low)) * 100;
		
		//round off to 2 decimal places
		this.totalCost = Math.round(this.totalCost*100.0)/100.0;
		this.totalTargetCost = Math.round(this.totalTargetCost*100.0)/100.0;
		this.sellAt = Math.round(this.sellAt*100.0)/100.0;
		this.totalProfit = Math.round(this.totalProfit*100.0)/100.0;
		this.howClose = Math.round(this.howClose*100.0)/100.0;
		return this;
	}
	
}
