package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.AppShuntPolicies;

public interface AppShuntPoliciesMapper {

	public List<AppShuntPolicies> findAppShuntPoliciesPageList(Map<String, Object> map);
	
	public int findAppShuntPoliciesTotalByCondition(Map<String, String> paramMap);
	
	public int addAppShuntPolicies(AppShuntPolicies appShuntPolicies);
	
	public AppShuntPolicies findAppShuntPoliciesById(Integer id);
	
	public AppShuntPolicies findAppShuntPoliciesByAppId(Integer appId);
	
	public int updateAppShuntPolicies(AppShuntPolicies appShuntPolicies);
	
	public int deleteAppShuntPoliciesByAppId(Integer appId);
	
	
}
