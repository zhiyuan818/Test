package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.CheckMust;
import com.jjxt.ssm.mapper.CheckMustMapper;
import com.jjxt.ssm.service.CheckMustService;

@Service("CheckMustService")
@Transactional
public class CheckMustServiceImpl implements CheckMustService {
	@Autowired
	private CheckMustMapper checkMustMapper;

	@Override
	public int addToCheckMust(String str) throws Exception {
		return checkMustMapper.addToCheckMust(str);
	}

	@Override
	public List<CheckMust> findPageList(Map<String, Object> map) throws Exception {
		return checkMustMapper.findPageList(map);
	}

	@Override
	public int findTotal(Map<String, Object> map) throws Exception {
		return checkMustMapper.findTotal(map);
	}

	@Override
	public int delCheckMust(Integer id) throws Exception {
		return checkMustMapper.delCheckMust(id);
	}

	@Override
	public CheckMust findCheckMustById(Integer id) throws Exception {
		return checkMustMapper.findCheckMustById(id);
	}

	@Override
	public int updateCheckMust(CheckMust checkmust) throws Exception {
		return checkMustMapper.updateCheckMust(checkmust);
	}

	@Override
	public int findByKeyWord(String str) throws Exception {
		// TODO Auto-generated method stub
		return checkMustMapper.findByKeyWord(str);
	}

}
