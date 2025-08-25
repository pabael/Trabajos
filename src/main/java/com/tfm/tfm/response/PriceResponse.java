package com.tfm.tfm.response;

public class PriceResponse {
	
	private int priceRange;

	public PriceResponse() {}
	
	public PriceResponse(int priceRange) {
		this.priceRange = priceRange;
	}

	public int getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(int priceRange) {
		this.priceRange = priceRange;	
	}
}
