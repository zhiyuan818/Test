package com.jjxt.ssm.entity;

public class LoadConfig {

	public int id;
	public String redisKey;
	public String redisType;
	public Integer minimum;
	public Integer maximum;
	public Integer timeLimit;
	public String printLog;
	public String variant;
	public String redisName;
	public String modelName;
	public String mapKey;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRedisKey() {
		return redisKey;
	}
	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}
	public String getRedisType() {
		return redisType;
	}
	public void setRedisType(String redisType) {
		this.redisType = redisType;
	}
	public Integer getMinimum() {
		return minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	public Integer getMaximum() {
		return maximum;
	}
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getPrintLog() {
		return printLog;
	}
	public void setPrintLog(String printLog) {
		this.printLog = printLog;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getRedisName() {
		return redisName;
	}
	public void setRedisName(String redisName) {
		this.redisName = redisName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getMapKey() {
		return mapKey;
	}
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}
	@Override
	public String toString() {
		return "LoadConfig [id=" + id + ", redisKey=" + redisKey + ", redisType=" + redisType + ", minimum=" + minimum
				+ ", maximum=" + maximum + ", timeLimit=" + timeLimit + ", printLog=" + printLog + ", variant="
				+ variant + ", redisName=" + redisName + ", modelName=" + modelName + ", mapKey=" + mapKey + "]";
	}
	
}
