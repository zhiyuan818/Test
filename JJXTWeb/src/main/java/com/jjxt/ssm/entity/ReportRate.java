package com.jjxt.ssm.entity;

public class ReportRate {
	
	private String reportStatus;
	private String provider;
	private Integer number;
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "ReportRate [reportStatus=" + reportStatus + ", provider=" + provider + ", number=" + number + "]";
	}
	
	
	
	
	
}
