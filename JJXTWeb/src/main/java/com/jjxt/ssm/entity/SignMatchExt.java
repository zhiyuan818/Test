package com.jjxt.ssm.entity;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class SignMatchExt {

	private int id;
	private String sign;
	private String ext;
	private int extLength;
	private int appId;
	private String isSync;
	private Date addTime;
	private String appName;
	
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
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getExtLength() {
		return extLength;
	}
	public void setExtLength(int extLength) {
		this.extLength = extLength;
	}
	public String getIsSync() {
		return isSync;
	}
	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	@Override
	public String toString() {
		return "SignMatchExt [id=" + id + ", sign=" + sign + ", appId=" + appId + ", appName=" + appName + ", ext="
				+ ext + ", extLength=" + extLength + ", isSync=" + isSync + ", addTime=" + addTime + "]";
	}
	
	
}
