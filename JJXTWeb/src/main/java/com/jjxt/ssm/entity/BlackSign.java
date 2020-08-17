package com.jjxt.ssm.entity;

import java.util.Date;

public class BlackSign {
	private Integer id;

	private String sign;

	private Integer messageType;

	private Date dateBanned;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Date getDateBanned() {
		return dateBanned;
	}

	public void setDataBanned(Date dateBanned) {
		this.dateBanned = dateBanned;
	}

	@Override
	public String toString() {
		return "BlackSign [id=" + id + ", sign=" + sign + ", messageType=" + messageType + ", dateBanned=" + dateBanned
				+ "]";
	}
	
}
