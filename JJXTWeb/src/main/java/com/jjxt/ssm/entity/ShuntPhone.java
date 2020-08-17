package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实号库
 */
public class ShuntPhone implements Serializable{

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
		return "ShuntPhone [id=" + id + ", phoneNumber=" + phoneNumber + ", addTime=" + addTime + "]";
	}
	
}
