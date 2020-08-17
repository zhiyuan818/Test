package com.jjxt.ssm.entity;

import java.util.Date;

public class ReSendConfig {
	private int id;
	private String status;
	private String idType;
	private int appChannelId;
	private String appName;
	private int companyId;
	private String companyKey;
	private String channelName;
	private String msgType;
	private int toChannelId;
	private String toChannelName;
	private Date createTime;
	private String ignoreChanOrAcc;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public int getAppChannelId() {
		return appChannelId;
	}
	public void setAppChannelId(int appChannelId) {
		this.appChannelId = appChannelId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyKey() {
		return companyKey;
	}
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public int getToChannelId() {
		return toChannelId;
	}
	public void setToChannelId(int toChannelId) {
		this.toChannelId = toChannelId;
	}
	public String getToChannelName() {
		return toChannelName;
	}
	public void setToChannelName(String toChannelName) {
		this.toChannelName = toChannelName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIgnoreChanOrAcc() {
		return ignoreChanOrAcc;
	}
	public void setIgnoreChanOrAcc(String ignoreChanOrAcc) {
		this.ignoreChanOrAcc = ignoreChanOrAcc;
	}
	@Override
	public String toString() {
		return "ReSendConfig [id=" + id + ", status=" + status + ", idType=" + idType + ", appChannelId=" + appChannelId
				+ ", appName=" + appName + ", companyId=" + companyId + ", companyKey=" + companyKey + ", channelName="
				+ channelName + ", msgType=" + msgType + ", toChannelId=" + toChannelId + ", toChannelName="
				+ toChannelName + ", createTime=" + createTime + ", ignoreChanOrAcc=" + ignoreChanOrAcc + "]";
	}
	
}
