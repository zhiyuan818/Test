package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.ChannelComplain;
import com.jjxt.ssm.mapper.ChannelComplainMapper;
import com.jjxt.ssm.service.ChannelComplainService;

@Service("ChannelComplainService")
@Transactional
public class ChannelComplainServiceImpl implements ChannelComplainService{
	@Autowired
	private ChannelComplainMapper chaComplainMapper;
	
	@Override
	public List<ChannelComplain> findAllLists(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return chaComplainMapper.findAllLists(map);
	}

	@Override
	public List<ChannelComplain> findListByChannelId(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return chaComplainMapper.findListByChannelId(map);
	}

	@Override
	public List<Integer> findListChannelId() throws Exception {
		// TODO Auto-generated method stub
		return chaComplainMapper.findListChannelId();
	}

}
