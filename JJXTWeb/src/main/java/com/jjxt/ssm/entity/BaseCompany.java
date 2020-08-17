package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * base_company库实体类
 * 
 * @author taoliu
 */
public class BaseCompany implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -763353171797556295L;
	private Integer id; // 自增id
	private Integer spId; // 服务提供商id
	private String sales; // 销售
	private String companyType;// 客户类型：代理商/直客
	private String companyName;// 客户名称
	private String companyKey;// 检索关键词
	private String companyDomain;// 客户域名
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private String saleAfter;// 客服
	private Integer count;//关联账户数

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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

	public String getCompanyDomain() {
		return companyDomain;
	}

	public void setCompanyDomain(String companyDomain) {
		this.companyDomain = companyDomain;
	}

	public String getSaleAfter() {
		return saleAfter;
	}

	public void setSaleAfter(String saleAfter) {
		this.saleAfter = saleAfter;
	}

}
