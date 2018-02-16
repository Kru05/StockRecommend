package com.cummins.stock.vo;

public class Recommend {
	private int id;
	private String name;
	private String code;
	private String companyType;
	private Float Close;
	private float profit;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getProfit() {
		return profit;
	}
	public void setProfit(float profit) {
		this.profit = profit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public Float getClose() {
		return Close;
	}
	public void setClose(Float close) {
		Close = close;
	}
}
