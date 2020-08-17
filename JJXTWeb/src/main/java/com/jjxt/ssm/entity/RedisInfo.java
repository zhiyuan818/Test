package com.jjxt.ssm.entity;

/**
 * @author yhhou
 *
 * 2018年8月20日下午1:39:28
 */
public class RedisInfo {
	
	private Integer id;
	
	private String name;
	
	private String remark;
	
	private String port;
	
	private String key;
	
	private String keyDetail;
	
	private String valueDetail;
	
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyDetail() {
		return keyDetail;
	}

	public void setKeyDetail(String keyDetail) {
		this.keyDetail = keyDetail;
	}

	public String getValueDetail() {
		return valueDetail;
	}

	public void setValueDetail(String valueDetail) {
		this.valueDetail = valueDetail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RedisInfo [id=" + id + ", name=" + name + ", remark=" + remark + ", port=" + port + ", key=" + key
				+ ", keyDetail=" + keyDetail + ", valueDetail=" + valueDetail + ", type=" + type + "]";
	}
	
}
