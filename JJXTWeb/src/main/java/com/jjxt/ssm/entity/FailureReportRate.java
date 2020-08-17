package com.jjxt.ssm.entity;

public class FailureReportRate {
	
	private String reportStatus;
	private String provider;
	private Integer number;
	private String percent;
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
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	@Override
	public String toString() {
		return "FailureReportRate [reportStatus=" + reportStatus + ", provider=" + provider + ", number=" + number
				+ ", percent=" + percent + "]";
	}
	
	
	
}
