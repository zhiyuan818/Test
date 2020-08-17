package com.jjxt.ssm.entity;

import java.util.Date;

public class Complain {

	private Integer id;
	private Integer appId;
	private Integer channelId;
	private String appName;
	private String destNumber;
	private String srcNumber;
	private Date submitTime;
	private String content;
	private String sendFlag;
	private String reportStatus;
	private Date addTime;
	private String channelName;
	
	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

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

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public String getSrcNumber() {
		return srcNumber;
	}

	public void setSrcNumber(String srcNumber) {
		this.srcNumber = srcNumber;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public String toString() {
		return "Complain [id=" + id + ", appId=" + appId + ", channelId=" + channelId + ", appName=" + appName
				+ ", destNumber=" + destNumber + ", srcNumber=" + srcNumber + ", submitTime=" + submitTime
				+ ", content=" + content + ", sendFlag=" + sendFlag + ", reportStatus=" + reportStatus + ", addTime="
				+ addTime + ", channelName=" + channelName + "]";
	}

}
