package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.AppShuntPolicies;
import com.jjxt.ssm.mapper.AppShuntPoliciesMapper;
import com.jjxt.ssm.service.AppShuntPoliciesService;

@Service("AppShuntPoliciesService")
@Transactional
public class AppShuntPoliciesServiceImpl implements AppShuntPoliciesService{
	@Autowired
	private AppShuntPoliciesMapper appShuntPoliciesMapper;

	@Override
	public List<AppShuntPolicies> findAppShuntPoliciesPageList(Map<String, Object> map) throws Exception {
		return appShuntPoliciesMapper.findAppShuntPoliciesPageList(map);
	}
	
	@Override
	public int findAppShuntPoliciesTotal(Map<String, String> paramMap) throws Exception {
		return appShuntPoliciesMapper.findAppShuntPoliciesTotalByCondition(paramMap);
	}

	@Override
	public int addAppShuntPolicies(AppShuntPolicies appShuntPolicies) throws Exception {
		return appShuntPoliciesMapper.addAppShuntPolicies(appShuntPolicies);
	}

	@Override
	public AppShuntPolicies findAppShuntPoliciesById(Integer id) throws Exception {
		return appShuntPoliciesMapper.findAppShuntPoliciesById(id);
	}

	@Override
	public int updateAppShuntPolicies(AppShuntPolicies appShuntPolicies) throws Exception {
		return appShuntPoliciesMapper.updateAppShuntPolicies(appShuntPolicies);
	}

	@Override
	public AppShuntPolicies findAppShuntPoliciesByAppId(Integer appId) throws Exception {
		return appShuntPoliciesMapper.findAppShuntPoliciesByAppId(appId);
	}

	@Override
	public int deleteAppShuntPoliciesByAppId(Integer appId) throws Exception {
		return appShuntPoliciesMapper.deleteAppShuntPoliciesByAppId(appId);
	}

}
