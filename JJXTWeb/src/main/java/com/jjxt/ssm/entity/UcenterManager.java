package com.jjxt.ssm.entity;

import java.io.Serializable;

/**
 * 后台用户表实体
 * 
 * @author taoliu 2018年2月7日15:53:09
 */

public class UcenterManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 428194884547171563L;
	private Integer id; // 用户id
	private Integer spId; // 企业服务id
	private String managerName; // 用户名
	private String managerPassword; // 密码
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

	public Integer getSpId() {
		return spId;
	}

	public void setSpId(Integer spId) {
		this.spId = spId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
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
		return "UcenterManager [id=" + id + ", spId=" + spId + ", managerName=" + managerName + ", managerPassword="
				+ managerPassword + ", chineseName=" + chineseName + ", title=" + title + ", isAllCustomer="
				+ isAllCustomer + ", isAllChannel=" + isAllChannel + "]";
	}

}
