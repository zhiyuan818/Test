package com.jjxt.ssm.entity;

public class ChanChangeNum {

	private Integer id;

	private Integer channelId;

	private String channelName;
	
	private Integer appId;
	
	private String appName;
	
	private String contentType;
	
	private String province;
	
	private int number;
	
	private int percent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "ChanChangeNum [id=" + id + ", channelId=" + channelId + ", appId=" + appId
				+ ", contentType=" + contentType + ", province=" + province + ", number=" + number+ ", percent=" + percent
				+ "]";
	}

}
