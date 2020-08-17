package com.jjxt.ssm.entity;

import java.util.Date;

public class RandomExt {

	private int id;
	private String randomExt;
	private String msgext;
	private String appId;
	private String appName;
	private Date updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRandomExt() {
		return randomExt;
	}
	public void setRandomExt(String randomExt) {
		this.randomExt = randomExt;
	}
	public String getMsgext() {
		return msgext;
	}
	public void setMsgext(String msgext) {
		this.msgext = msgext;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "RandomExt [id=" + id + ", randomExt=" + randomExt + ", msgext=" + msgext + ", appId=" + appId
				+ ", appName=" + appName + ", updateTime=" + updateTime + "]";
	}
	
	
}
