package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;

public class MT implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1715195188348225651L;
	private Integer appId;// 账户id
	private String appName;// 账户名称
	private String messageType;// 消息类型
	private String messageId;// 消息id
	private String uniqueId;// 唯一id
	private String batchId;// 批次号
	private String destNumber;// 目的号码
	private String provider;// 运营商
	private String province;// 省份
	private String city;// 地区
	private String srcNumber;// 源号码
	private String extSrc;// 扩展号码
	private Integer msgFormat;// 消息格式
	private String messageClass;// 消息类别
	private String sendFlag;// 发送标识
	private String productId;// 产品id
	private String content;//内容
	private String channelId;// 通道id
	private String channelName;// 通道名称
	private String channelMsgId;// 通道消息编号
	private Date submitTime;// 提交时间
	private String submitStatus;// 提交状态
	private Date reportTime;// 状态时间
	private String reportStatus;// 状态
	private Integer lmTotal;// 计费条数
	private Integer lmSubsidy;// 计费序号
	private Date logTime;// 入库时间
	private Date reportGetTime;
	private String realReportStatus;// 真实状态
	private Date realReportTime;// 真实状态报告时间
	private String logicPath;
	private String linkId;

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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

	public String getSrcNumber() {
		return srcNumber;
	}

	public void setSrcNumber(String srcNumber) {
		this.srcNumber = srcNumber;
	}

	public String getExtSrc() {
		return extSrc;
	}

	public void setExtSrc(String extSrc) {
		this.extSrc = extSrc;
	}

	public Integer getMsgFormat() {
		return msgFormat;
	}

	public void setMsgFormat(Integer msgFormat) {
		this.msgFormat = msgFormat;
	}

	public String getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannelMsgId() {
		return channelMsgId;
	}

	public void setChannelMsgId(String channelMsgId) {
		this.channelMsgId = channelMsgId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Integer getLmTotal() {
		return lmTotal;
	}

	public void setLmTotal(Integer lmTotal) {
		this.lmTotal = lmTotal;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public Date getReportGetTime() {
		return reportGetTime;
	}

	public void setReportGetTime(Date reportGetTime) {
		this.reportGetTime = reportGetTime;
	}

	public String getRealReportStatus() {
		return realReportStatus;
	}

	public void setRealReportStatus(String realReportStatus) {
		this.realReportStatus = realReportStatus;
	}

	public Date getRealReportTime() {
		return realReportTime;
	}

	public void setRealReportTime(Date realReportTime) {
		this.realReportTime = realReportTime;
	}

	public String getLogicPath() {
		return logicPath;
	}

	public void setLogicPath(String logicPath) {
		this.logicPath = logicPath;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public Integer getLmSubsidy() {
		return lmSubsidy;
	}

	public void setLmSubsidy(Integer lmSubsidy) {
		this.lmSubsidy = lmSubsidy;
	}

	@Override
	public String toString() {
		return "MT [appId=" + appId + ", appName=" + appName + ", messageType=" + messageType + ", messageId="
				+ messageId + ", uniqueId=" + uniqueId + ", batchId=" + batchId + ", destNumber=" + destNumber
				+ ", provider=" + provider + ", province=" + province + ", city=" + city + ", srcNumber=" + srcNumber
				+ ", extSrc=" + extSrc + ", msgFormat=" + msgFormat + ", messageClass=" + messageClass + ", sendFlag="
				+ sendFlag + ", productId=" + productId + ", content=" + content + ", channelId=" + channelId
				+ ", channelName=" + channelName + ", channelMsgId=" + channelMsgId + ", submitTime=" + submitTime
				+ ", submitStatus=" + submitStatus + ", reportTime=" + reportTime + ", reportStatus=" + reportStatus
				+ ", lmTotal=" + lmTotal + ", lmSubsidy=" + lmSubsidy + ", logTime=" + logTime + ", reportGetTime="
				+ reportGetTime + ", realReportStatus=" + realReportStatus + ", realReportTime=" + realReportTime
				+ ", logicPath=" + logicPath + ", linkId=" + linkId + "]";
	}
	
	
}
