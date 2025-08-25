package com.tfm.tfm.response;

public class ConsumerResponse {
	
	private String type;

	public ConsumerResponse() {}
	
	public ConsumerResponse(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;	
	}
}
