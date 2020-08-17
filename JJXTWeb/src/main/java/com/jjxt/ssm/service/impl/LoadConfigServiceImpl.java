package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.LoadConfig;
import com.jjxt.ssm.mapper.LoadConfigMapper;
import com.jjxt.ssm.service.LoadConfigService;

@Service("LoadConfigService")
@Transactional
public class LoadConfigServiceImpl implements LoadConfigService {

	@Autowired
	private LoadConfigMapper loadconfigmapper;
	
	@Override
	public int addToLoadConfig(LoadConfig loadconfig) throws Exception {
		// TODO Auto-generated method stub
		return loadconfigmapper.addToLoadConfig(loadconfig);
	}

	@Override
	public List<LoadConfig> findPageList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return loadconfigmapper.findPageList(map);
	}

	@Override
	public int findTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return loadconfigmapper.findTotal(map);
	}

	@Override
	public int delLoadConfig(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return loadconfigmapper.delLoadConfig(id);
	}

	@Override
	public LoadConfig findLoadConfigById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return loadconfigmapper.findLoadConfigById(id);
	}

	@Override
	public int updateLoadConfig(LoadConfig loadconfig) throws Exception {
		// TODO Auto-generated method stub
		return loadconfigmapper.updateLoadConfig(loadconfig);
	}

}
