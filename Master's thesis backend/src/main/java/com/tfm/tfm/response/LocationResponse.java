package com.tfm.tfm.response;

public class LocationResponse {
	
	private String name;
	private String province;
	private String autonomousCommunity;

	public LocationResponse() {}

	public LocationResponse(String name) {
		this.name = name;
	}
	
	public LocationResponse(String name, String province, String autonomousCommunity) {
		this.name = name;
		this.province = province;
		this.autonomousCommunity = autonomousCommunity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;	
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAutonomousCommunity() {
		return autonomousCommunity;
	}

	public void setAutonomousCommunity(String autonomousCommunity) {
		this.autonomousCommunity = autonomousCommunity;
	}
}
