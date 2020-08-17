package com.jjxt.ssm.entity;

public class GlobalSetting {

	private int id;
	private String key;
	private String val;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setVal(String val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "GlobalSetting [id=" + id + ", key=" + key + ", val=" + val + ", remark=" + remark + "]";
	}
	

}
