package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.AppComplain;
import com.jjxt.ssm.mapper.AppComplainMapper;
import com.jjxt.ssm.service.AppComplainService;

@Service("AppComplainService")
@Transactional
public class AppComplainServiceImpl implements AppComplainService{
	@Autowired
	private AppComplainMapper appcomplainMapper;


	@Override
	public List<AppComplain> findAllList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return appcomplainMapper.findAllList(map);
	}

	@Override
	public List<AppComplain> findListByName(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return appcomplainMapper.findListByName(map);
	}

	@Override
	public List<String> findComplainNames() throws Exception {
		// TODO Auto-generated method stub
		return appcomplainMapper.findComplainNames();
	}


}
