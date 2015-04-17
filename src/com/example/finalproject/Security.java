package com.example.finalproject;



public class Security {
	
	String companyName, symbol,marketCapitalization,changePercentage;
	public String getMarketCapitalization() {
		return marketCapitalization;
	}

	public void setMarketCapitalization(String marketCapitalization) {
		this.marketCapitalization = marketCapitalization;
	}

	public String getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(String changePercentage) {
		this.changePercentage = changePercentage;
	}



	double askPrice, openPrice, closePrice, change, volume;
	
	int id;
	
	
	public Security(){};
	
	public Security(String companyName, String symbol, float currentPrice,
			float openPrice, float closePrice, float volume, int id) {
		super();
		this.companyName = companyName;
		this.symbol = symbol;
		this.askPrice = currentPrice;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.id = id;
	}
	
	
	
	public double getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getCurrentPrice() {
		return askPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.askPrice = currentPrice;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	
	@Override
	public String toString() {
		return "Security [companyName=" + companyName + ", symbol=" + symbol
				+ ", askPrice=" + askPrice + ", change=" + change + "]";
	}

	
	

	
	
	

	
	
	

}
