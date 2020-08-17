package com.jjxt.ssm.redis;

import java.util.Set;

/**
 * 
 * @author DoveDeng
 * @date 2017-04-11
 *
 */
public class JJMRedisConfig {
	private int maxTotal;
	private int maxIdle;
	private int maxWait;
	private int readTimeout;
	private int exceptionSleepTime;
	private Set<String> ips;
	private String userName;
	private String passwd;
	private String redisName;
	private boolean isSentinel;
	private boolean isPwd;
	
	public boolean isPwd() {
		return isPwd;
	}
	public void setPwd(boolean isPwd) {
		this.isPwd = isPwd;
	}
	public String getRedisName() {
		return redisName;
	}
	public void setRedisName(String redisName) {
		this.redisName = redisName;
	}
	public boolean isSentinel() {
		return isSentinel;
	}
	public void setSentinel(boolean isSentinel) {
		this.isSentinel = isSentinel;
	}
	public int getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public int getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	public int getExceptionSleepTime() {
		return exceptionSleepTime;
	}
	public void setExceptionSleepTime(int exceptionSleepTime) {
		this.exceptionSleepTime = exceptionSleepTime;
	}
	public Set<String> getIps() {
		return ips;
	}
	public void setIps(Set<String> ips) {
		this.ips = ips;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
