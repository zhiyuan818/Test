package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.LgModelSend;
import com.jjxt.ssm.mapper.LgModelSendMapper;
import com.jjxt.ssm.service.LgModelSendService;
@Service("/lgModelSendService")
@Transactional
public class LgModelSendSerivceImpl implements LgModelSendService{
	
	@Autowired
	private LgModelSendMapper lgModelSendMapper;
	@Override
	public int findTotal(Map<String, String> map) throws Exception {
		return lgModelSendMapper.findTotal(map);
	}

	@Override
	public List<LgModelSend> findLgModelSendList(Map<String, Object> map1) throws Exception {
		return lgModelSendMapper.findLgModelSendList(map1);
	}

	@Override
	public int addLgModelSend(LgModelSend lgModelSend) throws Exception {
		return lgModelSendMapper.addLgModelSend(lgModelSend);
	}

	@Override
	public List<LgModelSend> findAddEnd() throws Exception {
		return lgModelSendMapper.findAddEnd();
	}

	@Override
	public List<LgModelSend> findLgModelSendById(Integer id) throws Exception {
		return lgModelSendMapper.findLgModelSendById(id);
	}

	@Override
	public int deleteLgModelSend(Integer id) throws Exception {
		return lgModelSendMapper.deleteLgModelSend(id);
	}

	@Override
	public int updateLgModelSend(LgModelSend lgModelSend) throws Exception {
		// TODO Auto-generated method stub
		return lgModelSendMapper.updateLgModelSend(lgModelSend);
	}

	@Override
	public List<LgModelSend> findLgModelSendByIds(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		return lgModelSendMapper.findLgModelSendByIds(ids);
	}

	@Override
	public int delLgModelSendBatch(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		return lgModelSendMapper.delLgModelSendBatch(ids);
	}

}
