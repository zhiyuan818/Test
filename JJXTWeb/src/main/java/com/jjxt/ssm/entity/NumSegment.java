package com.jjxt.ssm.entity;

public class NumSegment {

	private int id;
	private String seg;
	private String carrier;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeg() {
		return seg;
	}
	public void setSeg(String seg) {
		this.seg = seg;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	@Override
	public String toString() {
		return "NumSegment [id=" + id + ", seg=" + seg + ", carrier=" + carrier + "]";
	}
	
	
	
}
