package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.AppShuntPolicies;
import com.jjxt.ssm.utils.DataSource;

public interface AppShuntPoliciesService {
	
	@DataSource("master")
	public List<AppShuntPolicies> findAppShuntPoliciesPageList(Map<String, Object> map) throws Exception;
	
	@DataSource("master")
	public int findAppShuntPoliciesTotal(Map<String, String> paramMap) throws Exception;
	
	@DataSource("master")
	public int addAppShuntPolicies(AppShuntPolicies appShuntPolicies) throws Exception;
	
	@DataSource("master")
	public AppShuntPolicies findAppShuntPoliciesById(Integer id) throws Exception;
	
	@DataSource("master")
	public AppShuntPolicies findAppShuntPoliciesByAppId(Integer appId) throws Exception;
	
	@DataSource("master")
	public int updateAppShuntPolicies(AppShuntPolicies appShuntPolicies) throws Exception;
	
	@DataSource("master")
	public int deleteAppShuntPoliciesByAppId(Integer appId) throws Exception;

}
