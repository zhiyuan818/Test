package com.jjxt.ssm.entity;

public class Dispatch {

	private Integer id;
	private String appName;
	private String longNum;
	private String companyName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getLongNum() {
		return longNum;
	}
	public void setLongNum(String longNum) {
		this.longNum = longNum;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "Dispatch [id=" + id + ", appName=" + appName + ", longNum=" + longNum + ", companyName=" + companyName
				+ "]";
	}
	
	
	
	
	
}
