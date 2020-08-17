package com.jjxt.ssm.entity;

public class ChannelComplain {

	private String appName;
	private String channelName;
	private Integer channelId;
	private Integer submitCount;
	private Integer succeedCount;
	private Integer complainCount;
	private Double complainRate;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getSubmitCount() {
		return submitCount;
	}
	public void setSubmitCount(Integer submitCount) {
		this.submitCount = submitCount;
	}
	public Integer getSucceedCount() {
		return succeedCount;
	}
	public void setSucceedCount(Integer succeedCount) {
		this.succeedCount = succeedCount;
	}
	public Integer getComplainCount() {
		return complainCount;
	}
	public void setComplainCount(Integer complainCount) {
		this.complainCount = complainCount;
	}
	public Double getComplainRate() {
		return complainRate;
	}
	public void setComplainRate(Double complainRate) {
		this.complainRate = complainRate;
	}
	@Override
	public String toString() {
		return "ChannelComplain [appName=" + appName + ", channelName=" + channelName + ", channelId=" + channelId
				+ ", submitCount=" + submitCount + ", succeedCount=" + succeedCount + ", complainCount=" + complainCount
				+ ", complainRate=" + complainRate + "]";
	}
	
	
}
