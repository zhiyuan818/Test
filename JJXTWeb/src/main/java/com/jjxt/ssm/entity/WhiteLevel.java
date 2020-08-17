package com.jjxt.ssm.entity;

import java.io.Serializable;

public class WhiteLevel implements Serializable{
	
	/**
	 * 白名单
	 */
	private static final long serialVersionUID = -7948507545555877594L;
	private Integer id;
	private Integer spId;//服务提供商id
	private Integer appId;//账号id
	private String phoneNumber;//手机号
	private String content;//内容
	
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getSpId() {
		return spId;
	}
	public void setSpId(Integer spId) {
		this.spId = spId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "WhiteLevel [id=" + id + ", spId=" + spId + ", appId=" + appId + ", phoneNumber=" + phoneNumber
				+ ", content=" + content + "]";
	}

}
