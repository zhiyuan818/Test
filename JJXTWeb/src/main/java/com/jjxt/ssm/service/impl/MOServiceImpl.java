package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.MO;
import com.jjxt.ssm.mapper.MOMapper;
import com.jjxt.ssm.service.MOService;

@Service("moService")
@Transactional
public class MOServiceImpl implements MOService{
	@Autowired
	private MOMapper moMapper;

	@Override
	public List<MO> findMO() throws Exception {
		return moMapper.findMO();
	}

	@Override
	public int findMOTotal(Map<String, Object> map) throws Exception {
		return moMapper.findMOTotal(map);
	}

	@Override
	public List<MO> findMOPageList(Map<String, Object> map) throws Exception {
		return moMapper.findMOPageList(map);
	}

	@Override
	public MO findMOById(Map<String, Object> map) throws Exception {
		return moMapper.findMOById(map);
	}

	@Override
	public int addToBlack(BlackLevel2 blackLevel2) throws Exception {
		return moMapper.addToBlack(blackLevel2);
	}

	@Override
	public int updateFlag(Map<String, Object> map) throws Exception {
		return moMapper.updateFlag(map);
	}

	@Override
	public int selectBlackLevel2(BlackLevel2 blackLevel2) throws Exception {
		return moMapper.selectBlackLevel2(blackLevel2);
	}

	@Override
	public int addToBlackLevel2Batch(Integer[] ids) throws Exception {
		return moMapper.addToBlackLevel2Batch(ids);
	}

	@Override
	public Integer findPutMoSum(Map<String, Object> map) throws Exception {
		return moMapper.findPutMoSum(map);
	}

}
