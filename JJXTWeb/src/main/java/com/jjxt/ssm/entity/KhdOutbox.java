package com.jjxt.ssm.entity;

import java.util.Date;

public class KhdOutbox {
	
	private Integer id;
	
	private Date dateTime;
	
	private String remarks;
	
	private Integer appId;
	
	private String appName;
	
	private String fileName;
	
	private String content;
	
	private String sendNum;
	
	private String sendSuccNum;
	
	private Integer sendFailNum;
	
	private String msgId;

	@Override
	public String toString() {
		return "KhdOutbox [id=" + id + ", dateTime=" + dateTime + ", remarks=" + remarks + ", appId=" + appId
				+ ", appName=" + appName + ", fileName=" + fileName + ", content=" + content + ", sendNum=" + sendNum
				+ ", sendSuccNum=" + sendSuccNum + ", sendFailNum=" + sendFailNum + ", msgId=" + msgId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public String getSendSuccNum() {
		return sendSuccNum;
	}

	public void setSendSuccNum(String sendSuccNum) {
		this.sendSuccNum = sendSuccNum;
	}

	public Integer getSendFailNum() {
		return sendFailNum;
	}

	public void setSendFailNum(Integer sendFailNum) {
		this.sendFailNum = sendFailNum;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	
}
