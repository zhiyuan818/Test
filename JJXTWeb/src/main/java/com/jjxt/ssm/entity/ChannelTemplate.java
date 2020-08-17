package com.jjxt.ssm.entity;

public class ChannelTemplate {
	
	private Integer id;
	private Integer channelId;
	private String channelName;
	private String channelAppId;
	private String channelTemplateId;
	private String channelTemplate;
	private String template;
	private String extras;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChannelId() {
		return channelId;
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
	public String getChannelAppId() {
		return channelAppId;
	}
	public void setChannelAppId(String channelAppId) {
		this.channelAppId = channelAppId;
	}
	public String getChannelTemplateId() {
		return channelTemplateId;
	}
	public void setChannelTemplateId(String channelTemplateId) {
		this.channelTemplateId = channelTemplateId;
	}
	public String getChannelTemplate() {
		return channelTemplate;
	}
	public void setChannelTemplate(String channelTemplate) {
		this.channelTemplate = channelTemplate;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	@Override
	public String toString() {
		return "ChannelTemplate [id=" + id + ", channelId=" + channelId + ", channelName=" + channelName
				+ ", channelAppId=" + channelAppId + ", channelTemplateId=" + channelTemplateId + ", channelTemplate="
				+ channelTemplate + ", template=" + template + ", extras=" + extras + "]";
	}
	
	
}
