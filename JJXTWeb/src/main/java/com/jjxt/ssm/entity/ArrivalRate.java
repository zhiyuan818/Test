package com.jjxt.ssm.entity;

public class ArrivalRate {

	private String hour;
	private Integer sendTotal;
	private Integer reportTotal;
	private String reportRate;
	private Integer five;
	private String fiveRate;
	private Integer ten;
	private String tenRate;
	private Integer fifteen;
	private String fifteenRate;
	private Integer twenty;
	private String twentyRate;
	private Integer more;
	private String moreRate;
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public Integer getSendTotal() {
		return sendTotal;
	}
	public void setSendTotal(Integer sendTotal) {
		this.sendTotal = sendTotal;
	}
	public Integer getReportTotal() {
		return reportTotal;
	}
	public void setReportTotal(Integer reportTotal) {
		this.reportTotal = reportTotal;
	}
	public String getReportRate() {
		return reportRate;
	}
	public void setReportRate(String reportRate) {
		this.reportRate = reportRate;
	}
	public Integer getFive() {
		return five;
	}
	public void setFive(Integer five) {
		this.five = five;
	}
	public String getFiveRate() {
		return fiveRate;
	}
	public void setFiveRate(String fiveRate) {
		this.fiveRate = fiveRate;
	}
	public Integer getTen() {
		return ten;
	}
	public void setTen(Integer ten) {
		this.ten = ten;
	}
	public String getTenRate() {
		return tenRate;
	}
	public void setTenRate(String tenRate) {
		this.tenRate = tenRate;
	}
	public Integer getFifteen() {
		return fifteen;
	}
	public void setFifteen(Integer fifteen) {
		this.fifteen = fifteen;
	}
	public String getFifteenRate() {
		return fifteenRate;
	}
	public void setFifteenRate(String fifteenRate) {
		this.fifteenRate = fifteenRate;
	}
	public Integer getTwenty() {
		return twenty;
	}
	public void setTwenty(Integer twenty) {
		this.twenty = twenty;
	}
	public String getTwentyRate() {
		return twentyRate;
	}
	public void setTwentyRate(String twentyRate) {
		this.twentyRate = twentyRate;
	}
	public Integer getMore() {
		return more;
	}
	public void setMore(Integer more) {
		this.more = more;
	}
	public String getMoreRate() {
		return moreRate;
	}
	public void setMoreRate(String moreRate) {
		this.moreRate = moreRate;
	}
	@Override
	public String toString() {
		return "ArrivalRate [hour=" + hour + ", sendTotal=" + sendTotal + ", reportTotal=" + reportTotal
				+ ", reportRate=" + reportRate + ", five=" + five + ", fiveRate=" + fiveRate + ", ten=" + ten
				+ ", tenRate=" + tenRate + ", fifteen=" + fifteen + ", fifteenRate=" + fifteenRate + ", twenty="
				+ twenty + ", twentyRate=" + twentyRate + ", more=" + more + ", moreRate=" + moreRate + "]";
	}
	
}
