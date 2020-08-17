package com.jjxt.ssm.entity;

import java.util.Map;

public class SubmitToGW {

	private String key;
	
	private String codeKey;

	private Integer channelId;

	private String channelName;

	private Integer codeAmount;
	
	private Integer amount;
	
	private boolean flag;
	
	private Map<String, String> carriers;

	public Integer getChannelId() {
		return channelId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getCodeAmount() {
		return codeAmount;
	}

	public void setCodeAmount(Integer codeAmount) {
		this.codeAmount = codeAmount;
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Map<String, String> getCarriers() {
		return carriers;
	}

	public void setCarriers(Map<String, String> carriers) {
		this.carriers = carriers;
	}
	
	
}
