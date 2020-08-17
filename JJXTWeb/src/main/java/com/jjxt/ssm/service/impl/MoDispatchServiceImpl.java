package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Dispatch;
import com.jjxt.ssm.mapper.MoDispatchMapper;
import com.jjxt.ssm.service.MoDispatchService;

@Service("dispatchService")
@Transactional
public class MoDispatchServiceImpl implements MoDispatchService{

	@Autowired
	private MoDispatchMapper dispatchMapper;
	
	
	@Override
	public List<Dispatch> findAllList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.findAllList(map);
	}


	@Override
	public int addToDispatch(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.addToDispatch(map);
	}


	@Override
	public Map<String, Object> findTwoId(String str) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.findTwoId(str);
	}


	@Override
	public Map<String, Object> findListById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.findListById(id);
	}


	@Override
	public int delDispatchById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.delDispatchById(id);
	}


	@Override
	public int updateDispatch(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.updateDispatch(map);
	}


	@Override
	public Dispatch findMoDispatchById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.findMoDispatchById(id);
	}


	@Override
	public Map<String, Object> findIdsByName(String str) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.findIdsByName(str);
	}


	@Override
	public int findMoDispatchData(String str) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.findMoDispatchData(str);
	}


	@Override
	public int findTotal(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return dispatchMapper.findTotal(paramMap);
	}


	@Override
	public int deleteDispatch(Map<String, String> map) throws Exception {
		
		return dispatchMapper.deleteDispatch(map);
	}


	
}
