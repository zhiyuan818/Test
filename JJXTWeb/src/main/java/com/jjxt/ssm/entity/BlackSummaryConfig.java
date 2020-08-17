package com.jjxt.ssm.entity;

public class BlackSummaryConfig {
	
	private Integer id;
	private Integer appId;
	private String appName;
	private String type;
	private Integer level;
	private String result;
	private String sendFlag;
	private Integer priority;
	private String status;
	private String remark;
	private String phone;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "BlackSummaryConfig [id=" + id + ", appId=" + appId + ", appName=" + appName + ", type=" + type
				+ ", level=" + level + ", result=" + result + ", sendFlag=" + sendFlag + ", priority=" + priority
				+ ", status=" + status + ", remark=" + remark + ", phone=" + phone + "]";
	}
	
	
}
