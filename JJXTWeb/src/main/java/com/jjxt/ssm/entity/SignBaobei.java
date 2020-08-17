package com.jjxt.ssm.entity;

import java.util.Date;

public class SignBaobei {

	private Integer id;
	private Integer channelId;
	private String channelName;
	private String companyName;
	private String sign;
	private String extSrc;
	private String baobeiFlag;
	private Date activeTime;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getBaobeiFlag() {
		return baobeiFlag;
	}
	public void setBaobeiFlag(String baobeiFlag) {
		this.baobeiFlag = baobeiFlag;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@Override
	public String toString() {
		return "SignBaobei [id=" + id + ", channelId=" + channelId + ", channelName=" + channelName + ", companyName="
				+ companyName + ", sign=" + sign + ", extSrc=" + extSrc + ", baobeiFlag=" + baobeiFlag + ", activeTime="
				+ activeTime + "]";
	}
	
	
	
	
	
}
