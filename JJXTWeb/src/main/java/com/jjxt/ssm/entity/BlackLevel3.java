package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;

public class BlackLevel3 implements Serializable{
	
	/**
	 * 普通黑名单3
	 */
	private static final long serialVersionUID = -7948507545555877594L;
	private Integer id;
	private String phoneNumber;//手机号
	private Date addTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "BlackLevel13 [id=" + id + ", phoneNumber=" + phoneNumber + ", addTime=" + addTime + "]";
	}
	
}
