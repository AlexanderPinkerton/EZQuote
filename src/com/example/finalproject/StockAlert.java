package com.example.finalproject;

public class StockAlert {

	String oldPrice, targetPrice, stockSymbol;
	
	
	

	public StockAlert(String oldPrice, String targetPrice, String stockSymbol) {
		super();
		this.oldPrice = oldPrice;
		this.targetPrice = targetPrice;
		this.stockSymbol = stockSymbol;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(String targetPrice) {
		this.targetPrice = targetPrice;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	
	
	
}
