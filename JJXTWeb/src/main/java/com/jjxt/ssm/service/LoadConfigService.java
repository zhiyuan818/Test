package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.LoadConfig;
import com.jjxt.ssm.utils.DataSource;

public interface LoadConfigService {
	
	@DataSource("master")
	public int addToLoadConfig(LoadConfig loadconfig) throws Exception;
	/* 按条件查询 */
	@DataSource("master")
	public List<LoadConfig> findPageList(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int findTotal(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int delLoadConfig(Integer id) throws Exception;

	@DataSource("master")
	public LoadConfig findLoadConfigById(Integer id) throws Exception;

	@DataSource("master")
	public int updateLoadConfig(LoadConfig loadconfig) throws Exception;
	

}
