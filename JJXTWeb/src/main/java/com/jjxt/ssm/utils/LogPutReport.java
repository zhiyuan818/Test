package com.jjxt.ssm.utils;

/**
 * @author yhhou
 *
 *         2018年6月6日下午2:59:05
 */
public class LogPutReport {

	private String logDate;
	private String appId;
	private String provider;
	private String beginTime;
	private String endTime;
	private String sendFlag;
	private String uniqueId;
	private String channelMsgId;
	private String channelId;
	private String destNumber;
	private String content;
	private String destSendFlag;
	private String number;
	private String seconds;
	private String reportStatus;
	private String result;

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getChannelMsgId() {
		return channelMsgId;
	}

	public void setChannelMsgId(String channelMsgId) {
		this.channelMsgId = channelMsgId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getDestNumber() {
		return destNumber;
	}

	public void setDestNumber(String destNumber) {
		this.destNumber = destNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDestSendFlag() {
		return destSendFlag;
	}

	public void setDestSendFlag(String destSendFlag) {
		this.destSendFlag = destSendFlag;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LogPutReport(String logDate, String appId, String provider, String beginTime, String endTime,
			String sendFlag, String uniqueId, String channelMsgId, String channelId, String destNumber, String content,
			String destSendFlag, String number, String seconds, String reportStatus, String result) {
		super();
		this.logDate = logDate;
		this.appId = appId;
		this.provider = provider;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.sendFlag = sendFlag;
		this.uniqueId = uniqueId;
		this.channelMsgId = channelMsgId;
		this.channelId = channelId;
		this.destNumber = destNumber;
		this.content = content;
		this.destSendFlag = destSendFlag;
		this.number = number;
		this.seconds = seconds;
		this.reportStatus = reportStatus;
		this.result = result;
	}

	public LogPutReport() {
		super();
	}

}
