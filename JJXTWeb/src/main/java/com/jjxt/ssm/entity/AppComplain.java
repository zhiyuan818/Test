package com.jjxt.ssm.entity;

public class AppComplain {

	private String appName;
	private String companyName;
	private Double submitCount;
	private Double succeedCount;
	private Double complainCount;
	private Double complainRate;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getSubmitCount() {
		return submitCount;
	}
	public void setSubmitCount(Double submitCount) {
		this.submitCount = submitCount;
	}
	public Double getSucceedCount() {
		return succeedCount;
	}
	public void setSucceedCount(Double succeedCount) {
		this.succeedCount = succeedCount;
	}
	public Double getComplainCount() {
		return complainCount;
	}
	public void setComplainCount(Double complainCount) {
		this.complainCount = complainCount;
	}
	public Double getComplainRate() {
		return complainRate;
	}
	public void setComplainRate(Double complainRate) {
		this.complainRate = complainRate;
	}
	@Override
	public String toString() {
		return "AppComplain [appName=" + appName + ", companyName=" + companyName + ", submitCount=" + submitCount
				+ ", succeedCount=" + succeedCount + ", complainCount=" + complainCount + ", complainRate="
				+ complainRate + "]";
	}
	
	
}
