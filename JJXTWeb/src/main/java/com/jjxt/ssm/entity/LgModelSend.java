package com.jjxt.ssm.entity;

import java.util.Date;

public class LgModelSend {

	private Integer id;

	private Integer cmccChannelId;

	private String cmccChannelName;
	
	private Integer unicomChannelId;
	
	private String unicomChannelName;
	
	private Integer telecomChannelId;

	private String telecomChannelName;
	
	private String content;

	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCmccChannelId() {
		return cmccChannelId;
	}

	public void setCmccChannelId(Integer cmccChannelId) {
		this.cmccChannelId = cmccChannelId;
	}

	public Integer getUnicomChannelId() {
		return unicomChannelId;
	}

	public void setUnicomChannelId(Integer unicomChannelId) {
		this.unicomChannelId = unicomChannelId;
	}

	public Integer getTelecomChannelId() {
		return telecomChannelId;
	}

	public void setTelecomChannelId(Integer telecomChannelId) {
		this.telecomChannelId = telecomChannelId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCmccChannelName() {
		return cmccChannelName;
	}

	public void setCmccChannelName(String cmccChannelName) {
		this.cmccChannelName = cmccChannelName;
	}

	public String getUnicomChannelName() {
		return unicomChannelName;
	}

	public void setUnicomChannelName(String unicomChannelName) {
		this.unicomChannelName = unicomChannelName;
	}

	public String getTelecomChannelName() {
		return telecomChannelName;
	}

	public void setTelecomChannelName(String telecomChannelName) {
		this.telecomChannelName = telecomChannelName;
	}

	@Override
	public String toString() {
		return "LgModelSend [id=" + id + ", cmccChannelId=" + cmccChannelId + ", unicomChannelId=" + unicomChannelId
				+ ", telecomChannelId=" + telecomChannelId + ", content=" + content + ", createTime=" + createTime
				+ "]";
	}

}
