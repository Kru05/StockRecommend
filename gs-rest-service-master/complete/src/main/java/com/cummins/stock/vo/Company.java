package com.cummins.stock.vo;


public class Company {

private int id;
private String name;
private String code;
private String companyType;
private String activeStatus;
private String lastUpdated;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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
public String getActiveStatus() {
	return activeStatus;
}
public void setActiveStatus(String activeStatus) {
	this.activeStatus = activeStatus;
}
public String getLastUpdated() {
	return lastUpdated;
}
public void setLastUpdated(String lastUpdated) {
	this.lastUpdated = lastUpdated;
}
}
