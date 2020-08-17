package com.jjxt.ssm.entity;

import java.util.Date;

public class BlackKeyword {

	private Integer id;
	private String keyWord;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "BlackKeyword [id=" + id + ", keyWord=" + keyWord + ", createTime=" + createTime + "]";
	}

}
