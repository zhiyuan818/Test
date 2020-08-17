package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.MsinStrategy;
import com.jjxt.ssm.mapper.MsinStrategyMapper;
import com.jjxt.ssm.service.MsinStrategyService;

@Service("MsinStrategyService")
@Transactional
public class MsinStrategyServiceImpl implements MsinStrategyService {

	@Autowired
	private MsinStrategyMapper msinMapper;

	@Override
	public List<MsinStrategy> findAllapp() throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.findAllapp();
	}

	@Override
	public List<MsinStrategy> findAlllevel() throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.findAlllevel();
	}

	@Override
	public List<MsinStrategy> findAllData() throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.findAllData();
	}

	@Override
	public int findTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.findTotal(map);
	}

	@Override
	public List<MsinStrategy> findMsinList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.findMsinList(map);
	}

	@Override
	public int delMsinStrategyById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.delMsinStrategyById(id);
	}

	@Override
	public int addMsinStrategy(MsinStrategy msin) throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.addMsinStrategy(msin);
	}

	@Override
	public int isExistConfig(MsinStrategy msin) throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.isExistConfig(msin);
	}

	@Override
	public List<MsinStrategy> findMsinByIds(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.findMsinByIds(ids);
	}

	@Override
	public int delMsinBatch(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		return msinMapper.delMsinBatch(ids);
	}

	@Override
	public int addMsinBatch(List<MsinStrategy> list) {
		// TODO Auto-generated method stub
		return msinMapper.addMsinBatch(list);
	}
}
