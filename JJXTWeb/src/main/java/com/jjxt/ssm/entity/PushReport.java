package com.jjxt.ssm.entity;

/**
 * @author yhhou
 *
 *         2018年6月1日上午10:35:49
 */
public class PushReport {
	private String key;

	private Integer appId;

	private String appName;

	private String companyName;

	private Integer adNumber;

	private Integer moadNumber;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Integer getAdNumber() {
		return adNumber;
	}

	public void setAdNumber(Integer adNumber) {
		this.adNumber = adNumber;
	}

	public Integer getMoadNumber() {
		return moadNumber;
	}

	public void setMoadNumber(Integer moadNumber) {
		this.moadNumber = moadNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
