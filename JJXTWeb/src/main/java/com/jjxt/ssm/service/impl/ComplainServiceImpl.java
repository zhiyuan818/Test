package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Complain;
import com.jjxt.ssm.mapper.ComplainMapper;
import com.jjxt.ssm.service.ComplainService;

@Service("ComplainService")
@Transactional
public class ComplainServiceImpl implements ComplainService {
	@Autowired
	private ComplainMapper complainMapper;

	@Override
	public List<Complain> findMtList(Map<String, Object> map) throws Exception {
		return complainMapper.findMtList(map);
	}
	
	@Override
	public int addToPreComplain(Map<String, Object> map) throws Exception {
		return complainMapper.addToPreComplain(map);
	}

	@Override
	public int addToBlackLevel1(String str) throws Exception {
		return complainMapper.addToBlackLevel1(str);
	}

	@Override
	public int findBlackLevel1(String str) throws Exception {
		return complainMapper.findBlackLevel1(str);
	}

	@Override
	public int deleteComplain(Map<String, String> map) throws Exception {
		return complainMapper.deleteComplain(map);
	}

	@Override
	public List<Complain> findNewMtList(Map<String, Object> map) throws Exception {
		return complainMapper.findNewMtList(map);
	}
	
	@Override
	public int findComplainTotal(Map<String, String> map) throws Exception {
		return complainMapper.findComplainTotal(map);
	}
	
	@Override
	public List<Complain> findComplainPageList(Map<String, Object> map) throws Exception {
		return complainMapper.findComplainPageList(map);
	}

}
