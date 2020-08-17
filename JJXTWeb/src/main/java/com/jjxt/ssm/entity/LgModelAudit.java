package com.jjxt.ssm.entity;

import java.util.Date;

public class LgModelAudit {

	private Integer id; // 唯一ID
	private String auditFlag; // 同步标识 yes需要 /no不需要
	private String content; // 模板内容
	private Date createTime; // 新建时间
	private String appId;
	private String appName;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Override
	public String toString() {
		return "LgModelAudit [id=" + id + ", auditFlag=" + auditFlag + ", content=" + content + ", createTime="
				+ createTime + ", appId=" + appId + ", appName=" + appName + "]";
	}
	
	
}
