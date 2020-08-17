package com.jjxt.ssm.entity;

import java.util.Date;

public class Application {
	private Integer id;
	private String appName;
	private String appPassword;
	private Integer appRoles;
	private Date createTime;
	private Long sentCount;
	private Long limitCount;
	private String rptSyncModel;
	private String rptSyncAddress;
	private String moSyncModel;
	private String moSyncAddress;
	private String fromFlag;
	private String authIp;
	private String appStatus;
	private Integer appShuntLevel;
	private Integer appShuntType;
	private String appIsCheck;
	private String priority;
	private Integer preBlackLevel;
	private String blackLevel;
	private String skipMustWords;
	private String appExtSrcCmcc;
	private String appExtSrcUnicom;
	private String appExtSrcTelecom;
	private String appExtSrc;
	private String serviceTimeBegin;
	private String serviceTimeEnd;
	private Integer priorityLevel;
	private String defaultSign;
	private String checkSendFlag;
	private Integer isExt;
	private String sourceSegment;
	private String isMinLimit;
	private Integer minLimitCount;
	private String isDayLimit;
	private Integer dayLimitCount;
	private String isDayContentLimit;
	private Integer dayLimitContentCount;
	private String checkWordsType;
	private Integer payCount;
	private Integer maxConnection;
	private Integer moFlag;
	private String isShuntPhone;
	private String isModel;
	private Integer productId;
	private String productName;
	private String testModel;
	private String isContAudit;
	private Integer contAuditCount;
	private String payment;
	private String chargeBy;
	/**ucenter_application_ext表字段**/
	private Integer companyId;//公司id
	private String companyName;
	private String companyKey;
	private String sales;
	private String saleAfter;
	private Integer appParentId;
	private String appParentName;
	private String description;
	private Double price;
	private Double priceCmcc;
	private Double priceUnicom;
	private Double priceTelecom;
	private Integer subNum;//子账户的数量
	private String isTemplate; //是否过策略模板
	//是否日限
	private String isMonthLimit;
	//是限条数
	private int monthLimitCount;
	//日限进审核
	private String isDayLimitCheck;
	//非默认签名及不带签名不能提交
	private String isDefaultSignSubmit;
	//上行码号
	private String moSourceSegment;
	//是否推送状态报告
	private String isSgip;
	//账户扩展参数
	private String appExtras;
	
	private String redisFlag;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppPassword() {
		return appPassword;
	}
	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAppRoles() {
		return appRoles;
	}
	public void setAppRoles(Integer appRoles) {
		this.appRoles = appRoles;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getSentCount() {
		return sentCount;
	}
	public void setSentCount(Long sentCount) {
		this.sentCount = sentCount;
	}
	public Long getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(Long limitCount) {
		this.limitCount = limitCount;
	}
	public String getRptSyncModel() {
		return rptSyncModel;
	}
	public void setRptSyncModel(String rptSyncModel) {
		this.rptSyncModel = rptSyncModel;
	}
	public String getRptSyncAddress() {
		return rptSyncAddress;
	}
	public void setRptSyncAddress(String rptSyncAddress) {
		this.rptSyncAddress = rptSyncAddress;
	}
	public String getMoSyncModel() {
		return moSyncModel;
	}
	public void setMoSyncModel(String moSyncModel) {
		this.moSyncModel = moSyncModel;
	}
	public String getMoSyncAddress() {
		return moSyncAddress;
	}
	public void setMoSyncAddress(String moSyncAddress) {
		this.moSyncAddress = moSyncAddress;
	}
	public String getFromFlag() {
		return fromFlag;
	}
	public void setFromFlag(String fromFlag) {
		this.fromFlag = fromFlag;
	}
	public String getAuthIp() {
		return authIp;
	}
	public void setAuthIp(String authIp) {
		this.authIp = authIp;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public Integer getAppShuntLevel() {
		return appShuntLevel;
	}
	public void setAppShuntLevel(Integer appShuntLevel) {
		this.appShuntLevel = appShuntLevel;
	}
	public Integer getAppShuntType() {
		return appShuntType;
	}
	public void setAppShuntType(Integer appShuntType) {
		this.appShuntType = appShuntType;
	}
	public String getAppIsCheck() {
		return appIsCheck;
	}
	public void setAppIsCheck(String appIsCheck) {
		this.appIsCheck = appIsCheck;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Integer getPreBlackLevel() {
		return preBlackLevel;
	}
	public void setPreBlackLevel(Integer preBlackLevel) {
		this.preBlackLevel = preBlackLevel;
	}
	public String getBlackLevel() {
		return blackLevel;
	}
	public void setBlackLevel(String blackLevel) {
		this.blackLevel = blackLevel;
	}
	public String getSkipMustWords() {
		return skipMustWords;
	}
	public void setSkipMustWords(String skipMustWords) {
		this.skipMustWords = skipMustWords;
	}
	public String getAppExtSrcCmcc() {
		return appExtSrcCmcc;
	}
	public void setAppExtSrcCmcc(String appExtSrcCmcc) {
		this.appExtSrcCmcc = appExtSrcCmcc;
	}
	public String getAppExtSrcUnicom() {
		return appExtSrcUnicom;
	}
	public void setAppExtSrcUnicom(String appExtSrcUnicom) {
		this.appExtSrcUnicom = appExtSrcUnicom;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getAppExtSrcTelecom() {
		return appExtSrcTelecom;
	}
	public void setAppExtSrcTelecom(String appExtSrcTelecom) {
		this.appExtSrcTelecom = appExtSrcTelecom;
	}
	public String getAppExtSrc() {
		return appExtSrc;
	}
	public void setAppExtSrc(String appExtSrc) {
		this.appExtSrc = appExtSrc;
	}
	public String getServiceTimeBegin() {
		return serviceTimeBegin;
	}
	public void setServiceTimeBegin(String serviceTimeBegin) {
		this.serviceTimeBegin = serviceTimeBegin;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public Integer getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public String getDefaultSign() {
		return defaultSign;
	}
	public void setDefaultSign(String defaultSign) {
		this.defaultSign = defaultSign;
	}
	public String getCheckSendFlag() {
		return checkSendFlag;
	}
	public void setCheckSendFlag(String checkSendFlag) {
		this.checkSendFlag = checkSendFlag;
	}
	public Integer getIsExt() {
		return isExt;
	}
	public void setIsExt(Integer isExt) {
		this.isExt = isExt;
	}
	public String getSourceSegment() {
		return sourceSegment;
	}
	public void setSourceSegment(String sourceSegment) {
		this.sourceSegment = sourceSegment;
	}
	public String getIsMinLimit() {
		return isMinLimit;
	}
	public void setIsMinLimit(String isMinLimit) {
		this.isMinLimit = isMinLimit;
	}
	public Integer getMinLimitCount() {
		return minLimitCount;
	}
	
	public Integer getAppParentId() {
		return appParentId;
	}
	public void setAppParentId(Integer appParentId) {
		this.appParentId = appParentId;
	}
	public void setMinLimitCount(Integer minLimitCount) {
		this.minLimitCount = minLimitCount;
	}
	public String getIsDayLimit() {
		return isDayLimit;
	}
	public void setIsDayLimit(String isDayLimit) {
		this.isDayLimit = isDayLimit;
	}
	public Integer getDayLimitCount() {
		return dayLimitCount;
	}
	public void setDayLimitCount(Integer dayLimitCount) {
		this.dayLimitCount = dayLimitCount;
	}
	public String getIsDayContentLimit() {
		return isDayContentLimit;
	}
	public void setIsDayContentLimit(String isDayContentLimit) {
		this.isDayContentLimit = isDayContentLimit;
	}
	public Integer getDayLimitContentCount() {
		return dayLimitContentCount;
	}
	public void setDayLimitContentCount(Integer dayLimitContentCount) {
		this.dayLimitContentCount = dayLimitContentCount;
	}

	public String getCheckWordsType() {
		return checkWordsType;
	}
	public void setCheckWordsType(String checkWordsType) {
		this.checkWordsType = checkWordsType;
	}
	public Integer getPayCount() {
		return payCount;
	}
	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}
	public Integer getMaxConnection() {
		return maxConnection;
	}
	public void setMaxConnection(Integer maxConnection) {
		this.maxConnection = maxConnection;
	}
	public Integer getMoFlag() {
		return moFlag;
	}
	public void setMoFlag(Integer moFlag) {
		this.moFlag = moFlag;
	}
	public String getIsShuntPhone() {
		return isShuntPhone;
	}
	public void setIsShuntPhone(String isShuntPhone) {
		this.isShuntPhone = isShuntPhone;
	}
	public String getIsModel() {
		return isModel;
	}
	public void setIsModel(String isModel) {
		this.isModel = isModel;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getTestModel() {
		return testModel;
	}
	public void setTestModel(String testModel) {
		this.testModel = testModel;
	}
	public String getIsContAudit() {
		return isContAudit;
	}
	public void setIsContAudit(String isContAudit) {
		this.isContAudit = isContAudit;
	}
	public Integer getContAuditCount() {
		return contAuditCount;
	}
	public void setContAuditCount(Integer contAuditCount) {
		this.contAuditCount = contAuditCount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPriceCmcc() {
		return priceCmcc;
	}
	public void setPriceCmcc(Double priceCmcc) {
		this.priceCmcc = priceCmcc;
	}
	public Double getPriceUnicom() {
		return priceUnicom;
	}
	public void setPriceUnicom(Double priceUnicom) {
		this.priceUnicom = priceUnicom;
	}
	public Double getPriceTelecom() {
		return priceTelecom;
	}
	public void setPriceTelecom(Double priceTelecom) {
		this.priceTelecom = priceTelecom;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyKey() {
		return companyKey;
	}
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}
	public String getAppParentName() {
		return appParentName;
	}
	public void setAppParentName(String appParentName) {
		this.appParentName = appParentName;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getChargeBy() {
		return chargeBy;
	}
	public void setChargeBy(String chargeBy) {
		this.chargeBy = chargeBy;
	}
	public Integer getSubNum() {
		return subNum;
	}
	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}
	
	public String getIsTemplate() {
		return isTemplate;
	}
	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}
	
	public String getIsMonthLimit() {
		return isMonthLimit;
	}
	public void setIsMonthLimit(String isMonthLimit) {
		this.isMonthLimit = isMonthLimit;
	}
	public int getMonthLimitCount() {
		return monthLimitCount;
	}
	public void setMonthLimitCount(int monthLimitCount) {
		this.monthLimitCount = monthLimitCount;
	}
	public String getIsDayLimitCheck() {
		return isDayLimitCheck;
	}
	public void setIsDayLimitCheck(String isDayLimitCheck) {
		this.isDayLimitCheck = isDayLimitCheck;
	}
	public String getIsDefaultSignSubmit() {
		return isDefaultSignSubmit;
	}
	public void setIsDefaultSignSubmit(String isDefaultSignSubmit) {
		this.isDefaultSignSubmit = isDefaultSignSubmit;
	}
	public String getMoSourceSegment() {
		return moSourceSegment;
	}
	public void setMoSourceSegment(String moSourceSegment) {
		this.moSourceSegment = moSourceSegment;
	}
	public String getIsSgip() {
		return isSgip;
	}
	public void setIsSgip(String isSgip) {
		this.isSgip = isSgip;
	}
	public String getAppExtras() {
		return appExtras;
	}
	public void setAppExtras(String appExtras) {
		this.appExtras = appExtras;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getSaleAfter() {
		return saleAfter;
	}
	public void setSaleAfter(String saleAfter) {
		this.saleAfter = saleAfter;
	}
	public String getRedisFlag() {
		return redisFlag;
	}
	public void setRedisFlag(String redisFlag) {
		this.redisFlag = redisFlag;
	}
	@Override
	public String toString() {
		return "Application [id=" + id + ", appName=" + appName + ", appPassword=" + appPassword + ", appRoles="
				+ appRoles + ", createTime=" + createTime + ", sentCount=" + sentCount + ", limitCount=" + limitCount
				+ ", rptSyncModel=" + rptSyncModel + ", rptSyncAddress=" + rptSyncAddress + ", moSyncModel="
				+ moSyncModel + ", moSyncAddress=" + moSyncAddress + ", fromFlag=" + fromFlag + ", authIp=" + authIp
				+ ", appStatus=" + appStatus + ", appShuntLevel=" + appShuntLevel + ", appShuntType=" + appShuntType
				+ ", appIsCheck=" + appIsCheck + ", priority=" + priority + ", preBlackLevel=" + preBlackLevel
				+ ", blackLevel=" + blackLevel + ", skipMustWords=" + skipMustWords + ", appExtSrcCmcc=" + appExtSrcCmcc
				+ ", appExtSrcUnicom=" + appExtSrcUnicom + ", appExtSrcTelecom=" + appExtSrcTelecom + ", appExtSrc="
				+ appExtSrc + ", serviceTimeBegin=" + serviceTimeBegin + ", serviceTimeEnd=" + serviceTimeEnd
				+ ", priorityLevel=" + priorityLevel + ", defaultSign=" + defaultSign + ", checkSendFlag="
				+ checkSendFlag + ", isExt=" + isExt + ", sourceSegment=" + sourceSegment + ", isMinLimit=" + isMinLimit
				+ ", minLimitCount=" + minLimitCount + ", isDayLimit=" + isDayLimit + ", dayLimitCount=" + dayLimitCount
				+ ", isDayContentLimit=" + isDayContentLimit + ", dayLimitContentCount=" + dayLimitContentCount
				+ ", checkWordsType=" + checkWordsType + ", payCount=" + payCount + ", maxConnection=" + maxConnection
				+ ", moFlag=" + moFlag + ", isShuntPhone=" + isShuntPhone + ", isModel=" + isModel + ", productId="
				+ productId + ", productName=" + productName + ", testModel=" + testModel + ", isContAudit="
				+ isContAudit + ", contAuditCount=" + contAuditCount + ", payment=" + payment + ", chargeBy=" + chargeBy
				+ ", companyId=" + companyId + ", companyName=" + companyName + ", companyKey=" + companyKey
				+ ", sales=" + sales + ", saleAfter=" + saleAfter + ", appParentId=" + appParentId + ", appParentName="
				+ appParentName + ", description=" + description + ", price=" + price + ", priceCmcc=" + priceCmcc
				+ ", priceUnicom=" + priceUnicom + ", priceTelecom=" + priceTelecom + ", subNum=" + subNum
				+ ", isTemplate=" + isTemplate + ", isMonthLimit=" + isMonthLimit + ", monthLimitCount="
				+ monthLimitCount + ", isDayLimitCheck=" + isDayLimitCheck + ", isDefaultSignSubmit="
				+ isDefaultSignSubmit + ", moSourceSegment=" + moSourceSegment + ", isSgip=" + isSgip + ", appExtras="
				+ appExtras + ", redisFlag=" + redisFlag + "]";
	}
	
}
