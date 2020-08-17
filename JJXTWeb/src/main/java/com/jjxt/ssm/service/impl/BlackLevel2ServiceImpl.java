package com.jjxt.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.mapper.BlackLevel2Mapper;
import com.jjxt.ssm.service.BlackLevel2Service;

@Service("blackLevel2Service")
@Transactional
public class BlackLevel2ServiceImpl implements BlackLevel2Service {

	@Autowired
	private BlackLevel2Mapper blackLevel2Mapper;

	@Override
	public List<BlackLevel2> findByPhoneNumber(String phone) throws Exception {
		return blackLevel2Mapper.findByPhoneNumber(phone);
	}

}
