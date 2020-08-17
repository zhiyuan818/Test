package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;

public class MO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5918221715446330199L;
	private Integer id;// 自增id
	private Integer spId;// 服务提供者id
	private Integer companyId;// 公司id
	private Integer appId;// 账号id
	private String destNumber;// 目的号码
	private String srcNumber;// 源号码
	private String province;// 省份
	private String city;// 地区
	private String content;// 内容
	private Integer channelId;// 通道id
	private String channelMsgId;// 通道msgId
	private String messageId;// 消息id
	private Date receiveTime;// 上行接收时间
	private String blackFlag;// 加黑标记
	private String logicPath;// 模块号
	private String channelName;// 通道名称
	private String appName;// 账户名称

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSpId() {
		return spId;
	}

	public void setSpId(Integer spId) {
		this.spId = spId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public String getSrcNumber() {
		return srcNumber;
	}

	public void setSrcNumber(String srcNumber) {
		this.srcNumber = srcNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelMsgId() {
		return channelMsgId;
	}

	public void setChannelMsgId(String channelMsgId) {
		this.channelMsgId = channelMsgId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getBlackFlag() {
		return blackFlag;
	}

	public void String(String blackFlag) {
		this.blackFlag = blackFlag;
	}

	public String getLogicPath() {
		return logicPath;
	}

	public void setLogicPath(String logicPath) {
		this.logicPath = logicPath;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "MO [id=" + id + ", spId=" + spId + ", companyId=" + companyId + ", appId=" + appId + ", destNumber="
				+ destNumber + ", srcNumber=" + srcNumber + ", province=" + province + ", city=" + city + ", content="
				+ content + ", channelId=" + channelId + ", channelMsgId=" + channelMsgId + ", messageId=" + messageId
				+ ", receiveTime=" + receiveTime + ", blackFlag=" + blackFlag + ", logicPath=" + logicPath
				+ ", channelName=" + channelName + ", appName=" + appName + "]";
	}

}
