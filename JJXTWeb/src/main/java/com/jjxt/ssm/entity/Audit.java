package com.jjxt.ssm.entity;

/**
 * 
 * @author yhhou
 *
 *         2018年5月14日上午11:24:23
 */
public class Audit {

	private String flag;// 标识

	private int appId;// 账户ID

	private String appName;// 账户名称

	private Integer channelId;// 通道ID

	private String channelName;// 通道名称

	private Long amount;// 数量

	private String content;// 内容

	private String auditResult;// 命中的必审词

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

}
