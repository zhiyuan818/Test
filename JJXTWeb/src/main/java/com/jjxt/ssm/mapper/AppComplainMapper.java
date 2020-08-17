package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.AppComplain;

public interface AppComplainMapper {

	public List<AppComplain> findAllList(Map<String, Object> map);
	
	public List<AppComplain> findListByName(Map<String, Object> map);
	
	public List<String> findComplainNames();
}
