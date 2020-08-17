package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;

public class BlackLevel2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7948507545555877594L;
	private Integer id;
	private Integer channelId;// 通道id
	private Integer appId;// 账号id
	private String phoneNumber;// 手机号
	private String appName;
	private Date addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "BlackLevel2 [id=" + id + ", channelId=" + channelId + ", appId=" + appId + ", phoneNumber="
				+ phoneNumber + ", addTime=" + addTime + "]";
	}

}
