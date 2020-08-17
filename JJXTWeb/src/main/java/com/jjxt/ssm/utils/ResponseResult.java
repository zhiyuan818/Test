package com.jjxt.ssm.utils;

import java.io.Serializable;

public class ResponseResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8930746382858706925L;

	private Integer result;

	private Object newData;

	private Object oldData;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Object getNewData() {
		return newData;
	}

	public void setNewData(Object newData) {
		this.newData = newData;
	}

	public Object getOldData() {
		return oldData;
	}

	public void setOldData(Object oldData) {
		this.oldData = oldData;
	}

	@Override
	public String toString() {
		return "ResponseResult [result=" + result + ", newData=" + newData + ", oldData=" + oldData + "]";
	}

	public ResponseResult(Integer result, Object newData, Object oldData) {
		this.result = result;
		this.newData = newData;
		this.oldData = oldData;
	}

	public ResponseResult() {
	}

}
