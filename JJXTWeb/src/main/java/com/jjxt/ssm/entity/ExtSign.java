package com.jjxt.ssm.entity;

import java.util.Date;

public class ExtSign {

	private Integer id;
	private Integer appId;
	private String appName;
	private String sign;
	private String extSrc;
	private Date activeTime;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getExtSrc() {
		return extSrc;
	}
	public void setExtSrc(String extSrc) {
		this.extSrc = extSrc;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	@Override
	public String toString() {
		return "ExtSign [id=" + id + ", appId=" + appId + ", appName=" + appName + ", sign=" + sign + ", extSrc="
				+ extSrc + ", activeTime=" + activeTime + "]";
	}
	
	
	
}
