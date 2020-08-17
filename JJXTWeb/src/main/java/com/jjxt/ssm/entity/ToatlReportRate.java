package com.jjxt.ssm.entity;

public class ToatlReportRate {

	private String sendTotalCharge;
	private String totalDelivrdCharge;
	private String totalNullCharge;
	private String totalFailCharge;
	public String getSendTotalCharge() {
		return sendTotalCharge;
	}
	public void setSendTotalCharge(String sendTotalCharge) {
		this.sendTotalCharge = sendTotalCharge;
	}
	public String getTotalDelivrdCharge() {
		return totalDelivrdCharge;
	}
	public void setTotalDelivrdCharge(String totalDelivrdCharge) {
		this.totalDelivrdCharge = totalDelivrdCharge;
	}
	public String getTotalNullCharge() {
		return totalNullCharge;
	}
	public void setTotalNullCharge(String totalNullCharge) {
		this.totalNullCharge = totalNullCharge;
	}
	public String getTotalFailCharge() {
		return totalFailCharge;
	}
	public void setTotalFailCharge(String totalFailCharge) {
		this.totalFailCharge = totalFailCharge;
	}
	@Override
	public String toString() {
		return "ToatlReportRate [sendTotalCharge=" + sendTotalCharge + ", totalDelivrdCharge=" + totalDelivrdCharge
				+ ", totalNullCharge=" + totalNullCharge + ", totalFailCharge=" + totalFailCharge + "]";
	}

	
}
