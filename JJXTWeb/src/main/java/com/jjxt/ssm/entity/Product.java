package com.jjxt.ssm.entity;

import java.util.Date;

/**
 * 
 * @author yhhou
 *
 *         2018年6月7日上午11:04:27 产品管理
 */
public class Product {
	private Integer id; // 主键
	private String productType; // 产品类型
	private String productClass;// 产品类别
	private String productName;// 产品名称
	private String description;// 描述
	private Integer tacticsId;// 规则ID
	private Integer cmccChannelId;// 移动通道ID
	private String cmccChannelName;// 移动通道名称
	private Integer unicomChannelId;// 联通通道ID
	private String unicomChannelName;// 联通通道姓名
	private Integer telecomChannelId;// 电信通道ID
	private String telecomChannelName;// 电信通道姓名
	private Integer unknownChannelId;// 未知通道ID
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
	private String productStatus;// 产品状态
	private String isCheck;// 是否审核
	private String isSign;// 是否签名
	private String allowProvince;// 省份
	private Integer intlChannelId;// 国际通道ID
	private String intlChannelName;//国际短信名称
	private Integer appNumber;// 产品的账号使用数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductClass() {
		return productClass;
	}

	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTacticsId() {
		return tacticsId;
	}

	public void setTacticsId(Integer tacticsId) {
		this.tacticsId = tacticsId;
	}

	public Integer getCmccChannelId() {
		return cmccChannelId;
	}

	public void setCmccChannelId(Integer cmccChannelId) {
		this.cmccChannelId = cmccChannelId;
	}

	public Integer getUnicomChannelId() {
		return unicomChannelId;
	}

	public void setUnicomChannelId(Integer unicomChannelId) {
		this.unicomChannelId = unicomChannelId;
	}

	public Integer getTelecomChannelId() {
		return telecomChannelId;
	}

	public void setTelecomChannelId(Integer telecomChannelId) {
		this.telecomChannelId = telecomChannelId;
	}

	public Integer getUnknownChannelId() {
		return unknownChannelId;
	}

	public void setUnknownChannelId(Integer unknownChannelId) {
		this.unknownChannelId = unknownChannelId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getAllowProvince() {
		return allowProvince;
	}

	public void setAllowProvince(String allowProvince) {
		this.allowProvince = allowProvince;
	}

	public Integer getIntlChannelId() {
		return intlChannelId;
	}

	public void setIntlChannelId(Integer intlChannelId) {
		this.intlChannelId = intlChannelId;
	}

	public String getCmccChannelName() {
		return cmccChannelName;
	}

	public void setCmccChannelName(String cmccChannelName) {
		this.cmccChannelName = cmccChannelName;
	}

	public String getUnicomChannelName() {
		return unicomChannelName;
	}

	public void setUnicomChannelName(String unicomChannelName) {
		this.unicomChannelName = unicomChannelName;
	}

	public String getTelecomChannelName() {
		return telecomChannelName;
	}

	public void setTelecomChannelName(String telecomChannelName) {
		this.telecomChannelName = telecomChannelName;
	}

	public Integer getAppNumber() {
		return appNumber;
	}

	public void setAppNumber(Integer appNumber) {
		this.appNumber = appNumber;
	}

	public String getIntlChannelName() {
		return intlChannelName;
	}

	public void setIntlChannelName(String intlChannelName) {
		this.intlChannelName = intlChannelName;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productType=" + productType + ", productClass=" + productClass
				+ ", productName=" + productName + ", description=" + description + ", tacticsId=" + tacticsId
				+ ", cmccChannelId=" + cmccChannelId + ", cmccChannelName=" + cmccChannelName + ", unicomChannelId="
				+ unicomChannelId + ", unicomChannelName=" + unicomChannelName + ", telecomChannelId="
				+ telecomChannelId + ", telecomChannelName=" + telecomChannelName + ", unknownChannelId="
				+ unknownChannelId + ", createTime=" + createTime + ", updateTime=" + updateTime + ", productStatus="
				+ productStatus + ", isCheck=" + isCheck + ", isSign=" + isSign + ", allowProvince=" + allowProvince
				+ ", intlChannelId=" + intlChannelId + ", intlChannelName=" + intlChannelName + ", appNumber="
				+ appNumber + "]";
	}

	
}
