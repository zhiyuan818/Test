package com.jjxt.ssm.entity;

import java.util.Date;
import java.util.Map;

public class Statistics {
	private Integer id; // 自增id
	private Date logDate;// 日志日期
	private Integer spId; // 服务提供商id
	private Integer companyId; // 客户id
	private Integer appId;// 账户id
	private Integer channelId;// 通道id
	private String provider;// 运营商
	private String province;// 省份
	private String sendTotal;// 提交总数
	private String sendTotalCharge;// 提交总数 计费
	private String sendSuccess;// 提交成功
	private String sendSuccessCharge;// 提交成功 计费
	private String sendFail;// 提交失败 
	private String sendFailCharge;// 提交失败 计费 
	private String reject;// 驳回
	private String rejectCharge;// 驳回 计费
	private String reportDelivrd;// 状态成功
	private String reportDelivrdCharge;// 状态成功 计费
	private String reportUndeliv; //状态失败 
	private String reportUndelivCharge; //状态失败 计费
	private String reportBlack;	//黑名单
	private String reportBlackCharge;	//黑名单 计费 
	private String reportUnknown; //状态未知
	private String reportUnknownCharge; //状态未知 计费
	private Date updateTime;	//更新时间
	
	private String companyKey; // 客户key
	private String companyName;//客户名称
	private String sales; // 销售
	private String saleAfter;// 客服
	private String appName; // 客户名称
	private String delivrdRate; // 成功率
	private String channelName; // 通道名称
	private Map<String,Integer> errorStatus;
	private Integer supplierId;
	private String supplierKey;
	private String head;
	
	private Double appPrice;//账户价格
	private Double channelPrice;//通道价格
	private Double profit;//利润
	private Integer appReportDelivrdCharge;//账户提交总数
	private Integer channelReportDelivrdCharge;//通道提交总数
	private Double income;//收入
	private Double cost;//成本
	private Double profitMargin;//利润率
	
	private Integer shuntCharge;//优享计费数
	private String shuntRate;//优享率
	
	private String type;//类型
	private String content;//内容
	private String productId;//产品
	private String productName;//产品名称
	
	private String chineseName; // 备注（姓名）
	private String title; // 权限类型
	private String isAllCustomer; // 账户权限级别
	private String isAllChannel; // 通道权限级别
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
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
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
	public String getSendTotal() {
		return sendTotal;
	}
	public void setSendTotal(String sendTotal) {
		this.sendTotal = sendTotal;
	}
	public String getSendTotalCharge() {
		return sendTotalCharge;
	}
	public void setSendTotalCharge(String sendTotalCharge) {
		this.sendTotalCharge = sendTotalCharge;
	}
	public String getSendSuccess() {
		return sendSuccess;
	}
	public void setSendSuccess(String sendSuccess) {
		this.sendSuccess = sendSuccess;
	}
	public String getSendSuccessCharge() {
		return sendSuccessCharge;
	}
	public void setSendSuccessCharge(String sendSuccessCharge) {
		this.sendSuccessCharge = sendSuccessCharge;
	}
	public String getSendFail() {
		return sendFail;
	}
	public void setSendFail(String sendFail) {
		this.sendFail = sendFail;
	}
	public String getSendFailCharge() {
		return sendFailCharge;
	}
	public void setSendFailCharge(String sendFailCharge) {
		this.sendFailCharge = sendFailCharge;
	}
	public String getReject() {
		return reject;
	}
	public void setReject(String reject) {
		this.reject = reject;
	}
	public String getRejectCharge() {
		return rejectCharge;
	}
	public void setRejectCharge(String rejectCharge) {
		this.rejectCharge = rejectCharge;
	}
	public String getReportDelivrd() {
		return reportDelivrd;
	}
	public void setReportDelivrd(String reportDelivrd) {
		this.reportDelivrd = reportDelivrd;
	}
	public String getReportDelivrdCharge() {
		return reportDelivrdCharge;
	}
	public void setReportDelivrdCharge(String reportDelivrdCharge) {
		this.reportDelivrdCharge = reportDelivrdCharge;
	}
	public String getReportUndeliv() {
		return reportUndeliv;
	}
	public void setReportUndeliv(String reportUndeliv) {
		this.reportUndeliv = reportUndeliv;
	}
	public String getReportUndelivCharge() {
		return reportUndelivCharge;
	}
	public void setReportUndelivCharge(String reportUndelivCharge) {
		this.reportUndelivCharge = reportUndelivCharge;
	}
	public String getReportUnknown() {
		return reportUnknown;
	}
	public void setReportUnknown(String reportUnknown) {
		this.reportUnknown = reportUnknown;
	}
	public String getReportUnknownCharge() {
		return reportUnknownCharge;
	}
	public void setReportUnknownCharge(String reportUnknownCharge) {
		this.reportUnknownCharge = reportUnknownCharge;
	}
	public void setReportBlack(String reportBlack) {
		this.reportBlack = reportBlack;
	}
	public void setReportBlackCharge(String reportBlackCharge) {
		this.reportBlackCharge = reportBlackCharge;
	}
	public void setAppReportDelivrdCharge(Integer appReportDelivrdCharge) {
		this.appReportDelivrdCharge = appReportDelivrdCharge;
	}
	public void setChannelReportDelivrdCharge(Integer channelReportDelivrdCharge) {
		this.channelReportDelivrdCharge = channelReportDelivrdCharge;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCompanyKey() {
		return companyKey;
	}
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getDelivrdRate() {
		return delivrdRate;
	}
	public void setDelivrdRate(String delivrdRate) {
		this.delivrdRate = delivrdRate;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getReportBlack() {
		return reportBlack;
	}
	public String getReportBlackCharge() {
		return reportBlackCharge;
	}
	public Map<String, Integer> getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(Map<String, Integer> errorStatus) {
		this.errorStatus = errorStatus;
	}
	public Double getAppPrice() {
		return appPrice;
	}
	public void setAppPrice(Double appPrice) {
		this.appPrice = appPrice;
	}
	public Double getChannelPrice() {
		return channelPrice;
	}
	public void setChannelPrice(Double channelPrice) {
		this.channelPrice = channelPrice;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Integer getAppReportDelivrdCharge() {
		return appReportDelivrdCharge;
	}
	public Integer getChannelReportDelivrdCharge() {
		return channelReportDelivrdCharge;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getProfitMargin() {
		return profitMargin;
	}
	public void setProfitMargin(Double profitMargin) {
		this.profitMargin = profitMargin;
	}
	public Integer getShuntCharge() {
		return shuntCharge;
	}
	public void setShuntCharge(Integer shuntCharge) {
		this.shuntCharge = shuntCharge;
	}
	public String getShuntRate() {
		return shuntRate;
	}
	public void setShuntRate(String shuntRate) {
		this.shuntRate = shuntRate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSaleAfter() {
		return saleAfter;
	}
	public void setSaleAfter(String saleAfter) {
		this.saleAfter = saleAfter;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierKey() {
		return supplierKey;
	}
	public void setSupplierKey(String supplierKey) {
		this.supplierKey = supplierKey;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsAllCustomer() {
		return isAllCustomer;
	}
	public void setIsAllCustomer(String isAllCustomer) {
		this.isAllCustomer = isAllCustomer;
	}
	public String getIsAllChannel() {
		return isAllChannel;
	}
	public void setIsAllChannel(String isAllChannel) {
		this.isAllChannel = isAllChannel;
	}
	@Override
	public String toString() {
		return "Statistics [id=" + id + ", logDate=" + logDate + ", spId=" + spId + ", companyId=" + companyId
				+ ", appId=" + appId + ", channelId=" + channelId + ", provider=" + provider + ", province=" + province
				+ ", sendTotal=" + sendTotal + ", sendTotalCharge=" + sendTotalCharge + ", sendSuccess=" + sendSuccess
				+ ", sendSuccessCharge=" + sendSuccessCharge + ", sendFail=" + sendFail + ", sendFailCharge="
				+ sendFailCharge + ", reject=" + reject + ", rejectCharge=" + rejectCharge + ", reportDelivrd="
				+ reportDelivrd + ", reportDelivrdCharge=" + reportDelivrdCharge + ", reportUndeliv=" + reportUndeliv
				+ ", reportUndelivCharge=" + reportUndelivCharge + ", reportBlack=" + reportBlack
				+ ", reportBlackCharge=" + reportBlackCharge + ", reportUnknown=" + reportUnknown
				+ ", reportUnknownCharge=" + reportUnknownCharge + ", updateTime=" + updateTime + ", companyKey="
				+ companyKey + ", companyName=" + companyName + ", sales=" + sales + ", saleAfter=" + saleAfter
				+ ", appName=" + appName + ", delivrdRate=" + delivrdRate + ", channelName=" + channelName
				+ ", errorStatus=" + errorStatus + ", supplierId=" + supplierId + ", supplierKey=" + supplierKey
				+ ", head=" + head + ", appPrice=" + appPrice + ", channelPrice=" + channelPrice + ", profit=" + profit
				+ ", appReportDelivrdCharge=" + appReportDelivrdCharge + ", channelReportDelivrdCharge="
				+ channelReportDelivrdCharge + ", income=" + income + ", cost=" + cost + ", profitMargin="
				+ profitMargin + ", shuntCharge=" + shuntCharge + ", shuntRate=" + shuntRate + ", type=" + type
				+ ", content=" + content + ", productId=" + productId + ", productName=" + productName
				+ ", chineseName=" + chineseName + ", title=" + title + ", isAllCustomer=" + isAllCustomer
				+ ", isAllChannel=" + isAllChannel + "]";
	}
	
	
}

