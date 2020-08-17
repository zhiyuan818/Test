package com.jjxt.ssm.entity;

import java.util.Date;

public class KhdOperationLog {
	
	private Integer id;
	
	private String bussiness;
	
	private String operation;
	
	private Date dateTime;
	
	private String remarks;
	
	private Integer appId;
	
	private  String appName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBussiness() {
		return bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDataTime(Date dateTime) {
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	
}
