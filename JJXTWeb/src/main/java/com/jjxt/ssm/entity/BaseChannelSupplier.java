package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;


public class BaseChannelSupplier implements Serializable {


	private static final long serialVersionUID = -763353171797556295L;
	private Integer id; // 自增id
	private String head; // 负责人
	private String supplierName;// 供应商名称
	private String supplierKey;// 检索关键词
	private String supplierType;// 客户类型：代理商/直客
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private Integer count;//关联通道数

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

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierKey() {
		return supplierKey;
	}

	public void setSupplierKey(String supplierKey) {
		this.supplierKey = supplierKey;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	

}
