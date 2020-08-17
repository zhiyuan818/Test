package com.jjxt.ssm.entity;

public class Template {
	
	private Integer id;
	
	private String templateInfo;
	
	private String strategy;
	
	private String result;
	
	private Integer appId;
	
	private String appName;
	
	private String keyWord;
	
	private String rule;
	
	private Integer ruleIndex;
	
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTemplateInfo() {
		return templateInfo;
	}

	public void setTemplateInfo(String templateInfo) {
		this.templateInfo = templateInfo;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getRuleIndex() {
		return ruleIndex;
	}

	public void setRuleIndex(Integer ruleIndex) {
		this.ruleIndex = ruleIndex;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Template [id=" + id + ", templateInfo=" + templateInfo + ", strategy=" + strategy + ", result=" + result
				+ ", appId=" + appId + ", appName=" + appName + ", keyWord=" + keyWord + ", rule=" + rule
				+ ", ruleIndex=" + ruleIndex + ", status=" + status + "]";
	}

	
}
