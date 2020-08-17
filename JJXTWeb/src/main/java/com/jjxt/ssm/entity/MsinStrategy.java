package com.jjxt.ssm.entity;

public class MsinStrategy {

	private Integer id;
	private Integer appId;
	private String appName;
	private Integer level;
	private String phoneNumber;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "MsinStrategy [id=" + id + ", appId=" + appId + ", appName=" + appName + ", level=" + level
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	
}
