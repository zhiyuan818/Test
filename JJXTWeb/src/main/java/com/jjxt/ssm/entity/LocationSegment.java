package com.jjxt.ssm.entity;

public class LocationSegment {

	private int id;
	private String segment;
	private String province;
	private String city;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "LocationSegment [id=" + id + ", segment=" + segment + ", province=" + province + ", city=" + city + "]";
	}
	
	
}
