package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.ReSendConfig;
import com.jjxt.ssm.mapper.ReSendConfigMapper;
import com.jjxt.ssm.service.ReSendConfigService;
@Service("reSendConfigService")
@Transactional
public class ReSendConfigServiceImpl implements ReSendConfigService{
	@Autowired
	private ReSendConfigMapper reSendConfigMapper;

	@Override
	public List<ReSendConfig> findReSendConfigByAppPageList(Map<String, Object> paramMap) {
		return reSendConfigMapper.findReSendConfigByAppPageList(paramMap);
	}

	@Override
	public int findTotalByApp(Map<String, String> map) {
		return reSendConfigMapper.findTotalByApp(map);
	}
	
	@Override
	public List<ReSendConfig> findReSendConfigByChannelPageList(Map<String, Object> paramMap) {
		return reSendConfigMapper.findReSendConfigByChannelPageList(paramMap);
	}

	@Override
	public int findTotalByChannel(Map<String, String> map) {
		return reSendConfigMapper.findTotalByChannel(map);
	}

	@Override
	public ReSendConfig findReSendConfigByAppId(Integer id) {
		// TODO Auto-generated method stub
		return reSendConfigMapper.findReSendConfigByAppId(id);
	}
	
	@Override
	public ReSendConfig findReSendConfigByChannelId(Integer id) {
		// TODO Auto-generated method stub
		return reSendConfigMapper.findReSendConfigByChannelId(id);
	}

	@Override
	public int updateReSendConfigByAppId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reSendConfigMapper.updateReSendConfigByAppId(map);
	}
	
	@Override
	public int updateReSendConfigByChannelId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reSendConfigMapper.updateReSendConfigByChannelId(map);
	}
	
	@Override
	public int findReSendConfigIsRepeat(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reSendConfigMapper.findReSendConfigIsRepeat(map);
	}
	
	@Override
	public int addReSendConfig(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reSendConfigMapper.addReSendConfig(map);
	}
	
	
	@Override
	public ReSendConfig findEndAddReSendConfig(String idType) {
		// TODO Auto-generated method stub
		return reSendConfigMapper.findEndAddReSendConfig(idType);
	}
	
	@Override
	public int delReSendConfig(Integer id) {
		return reSendConfigMapper.delReSendConfig(id);
	}

	@Override
	public List<ReSendConfig> findResendConfigByIds(Integer[] ids) {
		return reSendConfigMapper.findResendConfigByIds(ids);
	}

	@Override
	public int delResendConfigByIdBatch(Integer[] ids) {
		return reSendConfigMapper.delResendConfigByIdBatch(ids);
	}

}
