package com.jjxt.ssm.entity;

import java.util.Date;

public class SignExtTemplate {

	private int id;
	private String sign;
	private String ext;
	private int cmccChannelId;
	private int unicomChannelId;
	private int telecomChannelId;
	private String cmccChannelName;
	private String unicomChannelName;
	private String telecomChannelName;
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getCmccChannelId() {
		return cmccChannelId;
	}
	public void setCmccChannelId(int cmccChannelId) {
		this.cmccChannelId = cmccChannelId;
	}
	public int getUnicomChannelId() {
		return unicomChannelId;
	}
	public void setUnicomChannelId(int unicomChannelId) {
		this.unicomChannelId = unicomChannelId;
	}
	public int getTelecomChannelId() {
		return telecomChannelId;
	}
	public void setTelecomChannelId(int telecomChannelId) {
		this.telecomChannelId = telecomChannelId;
	}
	public String getCmccChannelName() {
		return cmccChannelName;
	}
	public void setCmccChannelName(String cmccChannelName) {
		this.cmccChannelName = cmccChannelName;
	}
	public String getUnicomChannelName() {
		return unicomChannelName;
	}
	public void setUnicomChannelName(String unicomChannelName) {
		this.unicomChannelName = unicomChannelName;
	}
	public String getTelecomChannelName() {
		return telecomChannelName;
	}
	public void setTelecomChannelName(String telecomChannelName) {
		this.telecomChannelName = telecomChannelName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
