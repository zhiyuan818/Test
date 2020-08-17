package com.jjxt.ssm.entity;

import java.util.Date;

public class AccountIgnoreBlackShunt {
	
	private int id;
	private int appId;
	private String content;
	private String appName;
	private Date addTime;
	private String level;
	private String levelName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	@Override
	public String toString() {
		return "AccountIgnoreBlackShunt [id=" + id + ", appId=" + appId + ", content=" + content + ", appName="
				+ appName + ", addTime=" + addTime + ", level=" + level + ", levelName=" + levelName + "]";
	}
	
	
}
