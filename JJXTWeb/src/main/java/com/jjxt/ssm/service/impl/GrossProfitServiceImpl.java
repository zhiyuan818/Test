package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgModelSend;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.Template;
import com.jjxt.ssm.mapper.GrossProfitMapper;
import com.jjxt.ssm.service.GrossProfitService;

@Service("GrossProfitService")
@Transactional
public class GrossProfitServiceImpl implements GrossProfitService{
	@Autowired
	private GrossProfitMapper grossProfitMapper;

	@Override
	public List<Statistics> findAccountProfitList(Map<String, Object> map) throws Exception {
		return grossProfitMapper.findAccountProfitList(map);
	}
	
	@Override
	public List<Statistics> findChannelProfitList(Map<String, Object> map) throws Exception {
		return grossProfitMapper.findChannelProfitList(map);
	}


	@Override
	public List<Application> findApplication(Map<String, Object> map) throws Exception {
		return grossProfitMapper.findApplication(map);
	}
	
	@Override
	public List<LgModelSend> findLgModelSendList(Map<String, Object> map) throws Exception {
		return grossProfitMapper.findLgModelSendList(map);
	}

	@Override
	public List<Template> findTemplatePageListByIds(Integer[] ids) throws Exception {
		return grossProfitMapper.findTemplatePageListByIds(ids);
	}

	@Override
	public List<Template> findAllTemplate() throws Exception {
		return grossProfitMapper.findAllTemplate();
	}


}
