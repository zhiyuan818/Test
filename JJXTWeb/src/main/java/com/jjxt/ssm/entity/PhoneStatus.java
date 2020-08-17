package com.jjxt.ssm.entity;

import java.io.Serializable;

public class PhoneStatus implements Serializable {

	/**
	 * 手机状态
	 */
	private static final long serialVersionUID = -7948507545555877594L;
	private Integer id; // 主键
	private String phoneNumber;
	private String appId="";
	private String appName="";
	private String status;
	
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PhoneStatus [id=" + id + ", phoneNumber=" + phoneNumber + ", appId=" + appId + ", appName=" + appName
				+ ", status=" + status + "]";
	}
	
}
