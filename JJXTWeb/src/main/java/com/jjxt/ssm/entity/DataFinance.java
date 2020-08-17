package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shangcp
 *
 * @table data_finance
 */
public class DataFinance implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer companyId;
	private Integer appId;
	private String sales;
	private Date initTime;
	private String changeType;
	private Integer changeNum;
	private String payMode;
	private double price;
	private double rakeOff;
	private double revise;
	private double dueFrom;
	private String addName;
	private Date addTime;
	private String isBill;
	private String billName;
	private Date billTime;
	private String bankName;
	private String notice;
	
	
	/**以下是扩展字段**/
	private String appName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public Date getInitTime() {
		return initTime;
	}
	public void setInitTime(Date initTime) {
		this.initTime = initTime;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public Integer getChangeNum() {
		return changeNum;
	}
	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRakeOff() {
		return rakeOff;
	}
	public void setRakeOff(double rakeOff) {
		this.rakeOff = rakeOff;
	}
	public double getRevise() {
		return revise;
	}
	public void setRevise(double revise) {
		this.revise = revise;
	}
	public double getDueFrom() {
		return dueFrom;
	}
	public void setDueFrom(double dueFrom) {
		this.dueFrom = dueFrom;
	}
	public String getAddName() {
		return addName;
	}
	public void setAddName(String addName) {
		this.addName = addName;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getIsBill() {
		return isBill;
	}
	public void setIsBill(String isBill) {
		this.isBill = isBill;
	}
	public String getBillName() {
		return billName;
	}
	public void setBillName(String billName) {
		this.billName = billName;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Override
	public String toString() {
		return "DataFinance [id=" + id + ", companyId=" + companyId + ", appId=" + appId + ", sales=" + sales
				+ ", initTime=" + initTime + ", changeType=" + changeType + ", changeNum=" + changeNum + ", payMode="
				+ payMode + ", price=" + price + ", rakeOff=" + rakeOff + ", revise=" + revise + ", dueFrom=" + dueFrom
				+ ", addName=" + addName + ", addTime=" + addTime + ", isBill=" + isBill + ", billName=" + billName
				+ ", billTime=" + billTime + ", bankName=" + bankName + ", notice=" + notice + ", appName=" + appName
				+ "]";
	}
	
}
