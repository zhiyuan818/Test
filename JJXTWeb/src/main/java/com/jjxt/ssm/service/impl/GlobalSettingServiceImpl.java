package com.jjxt.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.GlobalSetting;
import com.jjxt.ssm.mapper.GlobalSettingMapper;
import com.jjxt.ssm.service.GlobalSettingService;

@Service("globalService")
@Transactional
public class GlobalSettingServiceImpl implements GlobalSettingService {

	@Autowired
	private GlobalSettingMapper globalMapper;

	@Override
	public List<GlobalSetting> findAll() throws Exception {
		return globalMapper.findAll();
	}

	@Override
	public int addToGlobalSetting(GlobalSetting global) throws Exception {
		return globalMapper.addToGlobalSetting(global);
	}

	@Override
	public int deleteGlobalById(Integer id) throws Exception {
		return globalMapper.deleteGlobalById(id);
	}

	@Override
	public GlobalSetting findGlobalSettingById(Integer id) throws Exception {
		return globalMapper.findGlobalSettingById(id);
	}

	@Override
	public int findGlobalByKey(String key) throws Exception {
		return globalMapper.findGlobalByKey(key);
	}

	@Override
	public int updateGlobal(GlobalSetting global) throws Exception {
		return globalMapper.updateGlobal(global);
	}

	@Override
	public String findGlobalValueByKey(String key) throws Exception {
		return globalMapper.findGlobalValueByKey(key);
	}

}
