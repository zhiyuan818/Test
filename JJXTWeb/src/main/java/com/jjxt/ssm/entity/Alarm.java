package com.jjxt.ssm.entity;

public class Alarm {

	private int userId;
	private String userName;
	private String describe;//用户真实名称
	private String phoneNumber;//手机号码
	private String email;//邮箱
	private String wechat;//微信
	private int modelId;
	private String modelName;//模块名称
	private String modelPwd;//模块密码
	private String sign;//签名
	private String authIp;//鉴权IP
	private int typeId;
	private String type;//报警方式
	private String typeModel;//报警内容模板
	private String isModel;//模板内容开关
	private String jsonParams;//json参数
	private String toparty;//微信组
	private String description;
	private String isPhone;
	private String isEmail;
	private String isWechat;
	private int level;
	private String cycle;//沉默期
	private String startTime;
	private String endTime;
	private String ignore;//忽略值
	private String strategy;//报警策略
	private String status;//报警策略状态
	private int sendId;
	private int configId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelPwd() {
		return modelPwd;
	}
	public void setModelPwd(String modelPwd) {
		this.modelPwd = modelPwd;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAuthIp() {
		return authIp;
	}
	public void setAuthIp(String authIp) {
		this.authIp = authIp;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeModel() {
		return typeModel;
	}
	public void setTypeModel(String typeModel) {
		this.typeModel = typeModel;
	}
	public String getIsModel() {
		return isModel;
	}
	public void setIsModel(String isModel) {
		this.isModel = isModel;
	}
	public String getJsonParams() {
		return jsonParams;
	}
	public void setJsonParams(String jsonParams) {
		this.jsonParams = jsonParams;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsPhone() {
		return isPhone;
	}
	public void setIsPhone(String isPhone) {
		this.isPhone = isPhone;
	}
	public String getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(String isEmail) {
		this.isEmail = isEmail;
	}
	public String getIsWechat() {
		return isWechat;
	}
	public void setIsWechat(String isWechat) {
		this.isWechat = isWechat;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIgnore() {
		return ignore;
	}
	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSendId() {
		return sendId;
	}
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}
	public int getConfigId() {
		return configId;
	}
	public void setConfigId(int configId) {
		this.configId = configId;
	}
	
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	@Override
	public String toString() {
		return "Alarm [userId=" + userId + ", userName=" + userName + ", describe=" + describe + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", wechat=" + wechat + ", modelId=" + modelId + ", modelName="
				+ modelName + ", modelPwd=" + modelPwd + ", sign=" + sign + ", authIp=" + authIp + ", typeId=" + typeId
				+ ", type=" + type + ", typeModel=" + typeModel + ", isModel=" + isModel + ", jsonParams=" + jsonParams
				+ ", toparty=" + toparty + ", description=" + description + ", isPhone=" + isPhone + ", isEmail="
				+ isEmail + ", isWechat=" + isWechat + ", level=" + level + ", cycle=" + cycle + ", startTime="
				+ startTime + ", endTime=" + endTime + ", ignore=" + ignore + ", strategy=" + strategy + ", status="
				+ status + ", sendId=" + sendId + ", configId=" + configId + "]";
	}
}
