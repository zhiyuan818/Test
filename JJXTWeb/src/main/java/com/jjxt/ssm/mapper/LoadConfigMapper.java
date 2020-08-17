package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.LoadConfig;

public interface LoadConfigMapper {

	public int addToLoadConfig(LoadConfig loadconfig);
	/* 按条件查询 */
	public List<LoadConfig> findPageList(Map<String, Object> map);

	public int findTotal(Map<String, Object> map);

	public int delLoadConfig(Integer id);

	public LoadConfig findLoadConfigById(Integer id);

	public int updateLoadConfig(LoadConfig loadconfig);
	

}
