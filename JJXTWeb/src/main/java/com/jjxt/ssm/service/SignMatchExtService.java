package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.SignMatchExt;
import com.jjxt.ssm.utils.DataSource;

public interface SignMatchExtService {
	@DataSource("master")
	public List<SignMatchExt> findPageList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Integer findTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Integer findMaxExtByAppIdAndExtLength(SignMatchExt signMExt) throws Exception;
	@DataSource("master")
	public Integer addSignMatchExt(SignMatchExt signMExt) throws Exception;
	@DataSource("master")
	public int deleteSignMatchExt(String id) throws Exception;
	@DataSource("master")
	public SignMatchExt findListById(String id) throws Exception;
	@DataSource("master")
	public List<SignMatchExt> findAllList(Map<String, Object> map) throws Exception;
}
