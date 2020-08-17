package com.jjxt.ssm.entity;

public class SubmitToLogicPath {
	private String key;

	private Integer codeNumber;

	private Integer industryNumber;

	private Integer marketNumber;

	private Integer urgentNumber;

	private Integer channelId;

	private String channelName;

	private Integer appId;

	private String appName;

	private String priority;
	
	private double gwSubmitSpeed;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(Integer codeNumber) {
		this.codeNumber = codeNumber;
	}

	public Integer getIndustryNumber() {
		return industryNumber;
	}

	public void setIndustryNumber(Integer industryNumber) {
		this.industryNumber = industryNumber;
	}

	public Integer getMarketNumber() {
		return marketNumber;
	}

	public void setMarketNumber(Integer marketNumber) {
		this.marketNumber = marketNumber;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Integer getUrgentNumber() {
		return urgentNumber;
	}

	public void setUrgentNumber(Integer urgentNumber) {
		this.urgentNumber = urgentNumber;
	}

	public double getGwSubmitSpeed() {
		return gwSubmitSpeed;
	}

	public void setGwSubmitSpeed(double gwSubmitSpeed) {
		this.gwSubmitSpeed = gwSubmitSpeed;
	}

}
