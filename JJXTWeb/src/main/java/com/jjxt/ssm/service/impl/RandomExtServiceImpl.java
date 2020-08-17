package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.RandomExt;
import com.jjxt.ssm.mapper.RandomExtMapper;
import com.jjxt.ssm.service.RandomExtService;

@Service("RandomExtService")
@Transactional
public class RandomExtServiceImpl implements RandomExtService {
	
	@Autowired
	private RandomExtMapper randomExtMapper;

	@Override
	public int findRandomExtTotal(Map<String, Object> paramMap) throws Exception {
		return randomExtMapper.findRandomExtTotal(paramMap);
	}

	@Override
	public List<RandomExt> findRandomExtPageList(Map<String, Object> paramMap) throws Exception {
		return randomExtMapper.findRandomExtPageList(paramMap);
	}

	@Override
	public RandomExt findRandomExtById(Integer id) throws Exception {
		return randomExtMapper.findRandomExtById(id);
	}

	@Override
	public int deleteRandomExtById(Integer id) throws Exception {
		return randomExtMapper.deleteRandomExtById(id);
	}

	@Override
	public List<RandomExt> findRandomExtByIds(Integer[] ids) throws Exception {
		return randomExtMapper.findRandomExtByIds(ids);
	}

	@Override
	public int delRandomExtBatch(Integer[] ids) throws Exception {
		return randomExtMapper.delRandomExtBatch(ids);
	}
	
}
