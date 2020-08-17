package com.jjxt.ssm.entity;

import java.io.Serializable;

/**
 * @author yhhou
 *
 *         2018年6月14日上午11:11:09
 */
public class Channel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6706381549774854222L;

	private Integer id;
	private Integer spId;
	private Integer channelId;
	private String name;
	private String notice;// 备注
	private String sourceSegment;// 源号码段
	private String callOut;// 剔除号码
	private Integer sourceFullLength;// 源号码长度
	private String sourceType;// 源号码类型
	private String toCmcc;// 支持移动
	private String toUnicom;// 支持联通
	private String toTelecom;// 支持电信
	private String toUnknown;// 支持未知
	private String msgidType;// 通道消息ID类型
	private String haveReport;// 是否有状态报告
	private String haveMo;// 是否有上行
	private Integer successRate;// 估算成功率
	private Integer sizeMax;// 信息最大长度
	private Integer sizeFirst;// 信息首条计费长度
	private Integer sizeCharge;// 信息计费长度
	private Integer coverKey;// 屏蔽词库
	private String submitBegin;// 提交起始时间
	private String submitEnd;// 提交结束时间
	private String autoExtSrc;// 是否启用自动扩展
	private String userExtSrc;// 是否允许用户扩展
	private String channelStatus;// 通道状态 启用，停用
	private String baobeiModel;// 报备模式：先报备，后报备，先报被破解
	private String filterGlobalBlack;// 过滤公共黑名单
	private String baobeiBeforePjExtSrc;// 先报备破解的扩展号
	private String toIntl;// 支持国际
	private String gwType;// 网关类型
	private String svcAddr;// 网关地址
	private Integer svcPort;// 网关端口
	private String account;// 登录网关的账号
	private String password;// 登录网关的密码
	private Integer linkMax;// 最大连接数
	private Integer linkSpeed;// 每连接最大发送速度（条/秒）
	private Integer firstMsgChargeLen;// 首条计费长度
	private Integer subseqMsgChargeLen;// 长短信第二条开始的计费长度
	private String serviceCode;// 服务代码
	private String enterpriseCode;// 企业代码
	private String extras;// 通道特定参数
	private String shortNum;// 通道分配的短号码
	private String variant;// 通道变体标识
	private Integer backupChannelId;
	private String autoExtractSigns;
	private Double channelPrice;// 通道单价
	private String model;// 每个模块对应线程,存放形式:模块号:线程数,模块号:线程数,...
	private String alarmCode;// 通道报警码
	private String submitAlarmCode;// 提交状态报警码
	private String logicModel;// logic模块的线程数
	private String moMatch;// 上行匹配方式：扩展，手机号
	private String isMonthLimit;//是否开启通道日限
	private String monthLimitCount;//通道日限条数
	private Integer productNumber;// 通道的产品使用数
	
	private String type;//类型
	private String consumerId;//消费者Id
	private String consumerName;//消费者名称
	private String provider;
	private String consumerStatus;
	
	private String platformFlag;
	private String allPlatformUsed;
	
	private Integer supplierId;
	private String supplierKey;
	private String head;
	
	private String redisFlag;
	
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

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getSourceSegment() {
		return sourceSegment;
	}

	public void setSourceSegment(String sourceSegment) {
		this.sourceSegment = sourceSegment;
	}

	public String getCallOut() {
		return callOut;
	}

	public void setCallOut(String callOut) {
		this.callOut = callOut;
	}

	public Integer getSourceFullLength() {
		return sourceFullLength;
	}

	public void setSourceFullLength(Integer sourceFullLength) {
		this.sourceFullLength = sourceFullLength;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getToCmcc() {
		return toCmcc;
	}

	public void setToCmcc(String toCmcc) {
		this.toCmcc = toCmcc;
	}

	public String getToUnicom() {
		return toUnicom;
	}

	public void setToUnicom(String toUnicom) {
		this.toUnicom = toUnicom;
	}

	public String getToTelecom() {
		return toTelecom;
	}

	public void setToTelecom(String toTelecom) {
		this.toTelecom = toTelecom;
	}

	public String getToUnknown() {
		return toUnknown;
	}

	public void setToUnknown(String toUnknown) {
		this.toUnknown = toUnknown;
	}

	public String getMsgidType() {
		return msgidType;
	}

	public void setMsgidType(String msgidType) {
		this.msgidType = msgidType;
	}

	public String getHaveReport() {
		return haveReport;
	}

	public void setHaveReport(String haveReport) {
		this.haveReport = haveReport;
	}

	public String getHaveMo() {
		return haveMo;
	}

	public void setHaveMo(String haveMo) {
		this.haveMo = haveMo;
	}

	public Integer getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(Integer successRate) {
		this.successRate = successRate;
	}

	public Integer getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(Integer sizeMax) {
		this.sizeMax = sizeMax;
	}

	public Integer getSizeFirst() {
		return sizeFirst;
	}

	public void setSizeFirst(Integer sizeFirst) {
		this.sizeFirst = sizeFirst;
	}

	public Integer getSizeCharge() {
		return sizeCharge;
	}

	public void setSizeCharge(Integer sizeCharge) {
		this.sizeCharge = sizeCharge;
	}

	public Integer getCoverKey() {
		return coverKey;
	}

	public void setCoverKey(Integer coverKey) {
		this.coverKey = coverKey;
	}

	public String getSubmitBegin() {
		return submitBegin;
	}

	public void setSubmitBegin(String submitBegin) {
		this.submitBegin = submitBegin;
	}

	public String getSubmitEnd() {
		return submitEnd;
	}

	public void setSubmitEnd(String submitEnd) {
		this.submitEnd = submitEnd;
	}

	public String getAutoExtSrc() {
		return autoExtSrc;
	}

	public void setAutoExtSrc(String autoExtSrc) {
		this.autoExtSrc = autoExtSrc;
	}

	public String getUserExtSrc() {
		return userExtSrc;
	}

	public void setUserExtSrc(String userExtSrc) {
		this.userExtSrc = userExtSrc;
	}

	public String getChannelStatus() {
		return channelStatus;
	}

	public void setChannelStatus(String channelStatus) {
		this.channelStatus = channelStatus;
	}

	public String getBaobeiModel() {
		return baobeiModel;
	}

	public void setBaobeiModel(String baobeiModel) {
		this.baobeiModel = baobeiModel;
	}

	public String getFilterGlobalBlack() {
		return filterGlobalBlack;
	}

	public void setFilterGlobalBlack(String filterGlobalBlack) {
		this.filterGlobalBlack = filterGlobalBlack;
	}

	public String getBaobeiBeforePjExtSrc() {
		return baobeiBeforePjExtSrc;
	}

	public void setBaobeiBeforePjExtSrc(String baobeiBeforePjExtSrc) {
		this.baobeiBeforePjExtSrc = baobeiBeforePjExtSrc;
	}

	public String getToIntl() {
		return toIntl;
	}

	public void setToIntl(String toIntl) {
		this.toIntl = toIntl;
	}

	public String getGwType() {
		return gwType;
	}

	public void setGwType(String gwType) {
		this.gwType = gwType;
	}

	public String getSvcAddr() {
		return svcAddr;
	}

	public void setSvcAddr(String svcAddr) {
		this.svcAddr = svcAddr;
	}

	public Integer getSvcPort() {
		return svcPort;
	}

	public void setSvcPort(Integer svcPort) {
		this.svcPort = svcPort;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLinkMax() {
		return linkMax;
	}

	public void setLinkMax(Integer linkMax) {
		this.linkMax = linkMax;
	}

	public Integer getLinkSpeed() {
		return linkSpeed;
	}

	public void setLinkSpeed(Integer linkSpeed) {
		this.linkSpeed = linkSpeed;
	}

	public Integer getFirstMsgChargeLen() {
		return firstMsgChargeLen;
	}

	public void setFirstMsgChargeLen(Integer firstMsgChargeLen) {
		this.firstMsgChargeLen = firstMsgChargeLen;
	}

	public Integer getSubseqMsgChargeLen() {
		return subseqMsgChargeLen;
	}

	public void setSubseqMsgChargeLen(Integer subseqMsgChargeLen) {
		this.subseqMsgChargeLen = subseqMsgChargeLen;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public String getShortNum() {
		return shortNum;
	}

	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public Integer getBackupChannelId() {
		return backupChannelId;
	}

	public void setBackupChannelId(Integer backupChannelId) {
		this.backupChannelId = backupChannelId;
	}

	public String getAutoExtractSigns() {
		return autoExtractSigns;
	}

	public void setAutoExtractSigns(String autoExtractSigns) {
		this.autoExtractSigns = autoExtractSigns;
	}

	public Double getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(Double channelPrice) {
		this.channelPrice = channelPrice;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}
	
	public String getSubmitAlarmCode() {
		return submitAlarmCode;
	}

	public void setSubmitAlarmCode(String submitAlarmCode) {
		this.submitAlarmCode = submitAlarmCode;
	}

	public String getLogicModel() {
		return logicModel;
	}

	public void setLogicModel(String logicModel) {
		this.logicModel = logicModel;
	}

	public String getMoMatch() {
		return moMatch;
	}

	public void setMoMatch(String moMatch) {
		this.moMatch = moMatch;
	}
	
	public String getIsMonthLimit() {
		return isMonthLimit;
	}

	public void setIsMonthLimit(String isMonthLimit) {
		this.isMonthLimit = isMonthLimit;
	}

	public String getMonthLimitCount() {
		return monthLimitCount;
	}

	public void setMonthLimitCount(String monthLimitCount) {
		this.monthLimitCount = monthLimitCount;
	}
	
	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getConsumerStatus() {
		return consumerStatus;
	}
	public void setConsumerStatus(String consumerStatus) {
		this.consumerStatus = consumerStatus;
	}

	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public String getAllPlatformUsed() {
		return allPlatformUsed;
	}

	public void setAllPlatformUsed(String allPlatformUsed) {
		this.allPlatformUsed = allPlatformUsed;
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

	public String getRedisFlag() {
		return redisFlag;
	}

	public void setRedisFlag(String redisFlag) {
		this.redisFlag = redisFlag;
	}

	@Override
	public String toString() {
		return "Channel [id=" + id + ", spId=" + spId + ", channelId=" + channelId + ", name=" + name + ", notice="
				+ notice + ", sourceSegment=" + sourceSegment + ", callOut=" + callOut + ", sourceFullLength="
				+ sourceFullLength + ", sourceType=" + sourceType + ", toCmcc=" + toCmcc + ", toUnicom=" + toUnicom
				+ ", toTelecom=" + toTelecom + ", toUnknown=" + toUnknown + ", msgidType=" + msgidType + ", haveReport="
				+ haveReport + ", haveMo=" + haveMo + ", successRate=" + successRate + ", sizeMax=" + sizeMax
				+ ", sizeFirst=" + sizeFirst + ", sizeCharge=" + sizeCharge + ", coverKey=" + coverKey
				+ ", submitBegin=" + submitBegin + ", submitEnd=" + submitEnd + ", autoExtSrc=" + autoExtSrc
				+ ", userExtSrc=" + userExtSrc + ", channelStatus=" + channelStatus + ", baobeiModel=" + baobeiModel
				+ ", filterGlobalBlack=" + filterGlobalBlack + ", baobeiBeforePjExtSrc=" + baobeiBeforePjExtSrc
				+ ", toIntl=" + toIntl + ", gwType=" + gwType + ", svcAddr=" + svcAddr + ", svcPort=" + svcPort
				+ ", account=" + account + ", password=" + password + ", linkMax=" + linkMax + ", linkSpeed="
				+ linkSpeed + ", firstMsgChargeLen=" + firstMsgChargeLen + ", subseqMsgChargeLen=" + subseqMsgChargeLen
				+ ", serviceCode=" + serviceCode + ", enterpriseCode=" + enterpriseCode + ", extras=" + extras
				+ ", shortNum=" + shortNum + ", variant=" + variant + ", backupChannelId=" + backupChannelId
				+ ", autoExtractSigns=" + autoExtractSigns + ", channelPrice=" + channelPrice + ", model=" + model
				+ ", alarmCode=" + alarmCode + ", submitAlarmCode=" + submitAlarmCode + ", logicModel=" + logicModel
				+ ", moMatch=" + moMatch + ", isMonthLimit=" + isMonthLimit + ", monthLimitCount=" + monthLimitCount
				+ ", productNumber=" + productNumber + ", type=" + type + ", consumerId=" + consumerId
				+ ", consumerName=" + consumerName + ", provider=" + provider + ", consumerStatus=" + consumerStatus
				+ ", platformFlag=" + platformFlag + ", allPlatformUsed=" + allPlatformUsed + ", supplierId="
				+ supplierId + ", supplierKey=" + supplierKey + ", head=" + head + ", redisFlag=" + redisFlag + "]";
	}
	 
}
