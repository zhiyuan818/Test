package com.jjxt.ssm.entity;

public class AppShuntPolicies {

	
	private Integer id;
	private Integer appId;
	private String appName;
	private String companyName;
	private String ignoredProvinces;		//忽略省份
	private String ignoredCarriers;		//忽略运营商
	private Integer ignoredPackMin;		//包基数
	private Integer ignoredPackHead;	//前基数
	private Integer ignoredPackTail;		//后基数
	private Integer appShuntLevel;
	private String isSyncSubApp;		//是否同步到子账户
	private String content;				// 优享内容
	private Integer percent;			// 省份/内容比例
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIgnoredProvinces() {
		return ignoredProvinces;
	}
	public void setIgnoredProvinces(String ignoredProvinces) {
		this.ignoredProvinces = ignoredProvinces;
	}
	public String getIgnoredCarriers() {
		return ignoredCarriers;
	}
	public void setIgnoredCarriers(String ignoredCarriers) {
		this.ignoredCarriers = ignoredCarriers;
	}
	public Integer getIgnoredPackMin() {
		return ignoredPackMin;
	}
	public void setIgnoredPackMin(Integer ignoredPackMin) {
		this.ignoredPackMin = ignoredPackMin;
	}
	public Integer getIgnoredPackHead() {
		return ignoredPackHead;
	}
	public void setIgnoredPackHead(Integer ignoredPackHead) {
		this.ignoredPackHead = ignoredPackHead;
	}
	public Integer getIgnoredPackTail() {
		return ignoredPackTail;
	}
	public void setIgnoredPackTail(Integer ignoredPackTail) {
		this.ignoredPackTail = ignoredPackTail;
	}
	
	public String getIsSyncSubApp() {
		return isSyncSubApp;
	}
	public void setIsSyncSubApp(String isSyncSubApp) {
		this.isSyncSubApp = isSyncSubApp;
	}
	
	public Integer getAppShuntLevel() {
		return appShuntLevel;
	}
	public void setAppShuntLevel(Integer appShuntLevel) {
		this.appShuntLevel = appShuntLevel;
	}	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPercent() {
		return percent;
	}
	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	@Override
	public String toString() {
		return "AppShuntPolicies [id=" + id + ", appId=" + appId + ", appName=" + appName + ", companyName="
				+ companyName + ", ignoredProvinces=" + ignoredProvinces + ", ignoredCarriers=" + ignoredCarriers
				+ ", ignoredPackMin=" + ignoredPackMin + ", ignoredPackHead=" + ignoredPackHead + ", ignoredPackTail="
				+ ignoredPackTail + ", appShuntLevel=" + appShuntLevel + ", isSyncSubApp=" + isSyncSubApp + ", content="
				+ content + ", percent=" + percent + "]";
	}
	
	
	
	
	
}
