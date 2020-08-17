package com.jjxt.ssm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.jjxt.ssm.utils.StringUtil;

/**
 * 数据功能操作记录日志
 * 
 * @author houyaohua
 *
 */
public class DataOperationLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2281709639151197108L;

	private Integer id;

	private Integer ucenterManagerId;

	private String ucenterManagerName;

	private String bussiness;

	private String operation;

	private Date dateTime;

	private String newData;

	private String oldData;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUcenterManagerId() {
		return ucenterManagerId;
	}

	public void setUcenterManagerId(Integer ucenterManagerId) {
		this.ucenterManagerId = ucenterManagerId;
	}

	public String getUcenterManagerName() {
		return ucenterManagerName;
	}

	public void setUcenterManagerName(String ucenterManagerName) {
		this.ucenterManagerName = ucenterManagerName;
	}

	public String getBussiness() {
		return bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}

	/**
	 * 设置请求参数
	 * 
	 * @param paramMap
	 */
	public void setMapToParams(Map<String, String[]> paramMap) {
		if (paramMap == null) {
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0
					? StringUtil.ArraySplicing(param.getValue()) : "");
			params.append(param.getKey().indexOf("password") == -1 ? paramValue : "");
		}
		this.newData = params.toString();
	}

	@Override
	public String toString() {
		return "DataOperationLog [id=" + id + ", ucenterManagerId=" + ucenterManagerId + ", ucenterManagerName="
				+ ucenterManagerName + ", bussiness=" + bussiness + ", operation=" + operation + ", dateTime="
				+ dateTime + ", newData=" + newData + ", oldData=" + oldData + "]";
	}

	public DataOperationLog(Integer id, Integer ucenterManagerId, String ucenterManagerName, String bussiness,
			String operation, Date dateTime, String newData, String oldData) {
		this.id = id;
		this.ucenterManagerId = ucenterManagerId;
		this.ucenterManagerName = ucenterManagerName;
		this.bussiness = bussiness;
		this.operation = operation;
		this.dateTime = dateTime;
		this.newData = newData;
		this.oldData = oldData;
	}

	public DataOperationLog() {
	}

}
