package com.jjxt.ssm.entity;

/**
 * 模块
 * 
 * @author yhhou
 *
 *         2018年6月22日下午2:00:05
 */
public class Model {

	private Integer id;

	private String name;

	private String type;
	private String value;
	private String isDefault;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}
