package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.entity.LgProvincesSend;
import com.jjxt.ssm.mapper.LgProvinceSendMapper;
import com.jjxt.ssm.service.LgProvinceSendService;
@Service
public class LgProvinceSendServiceImpl implements LgProvinceSendService{
	@Autowired
	private LgProvinceSendMapper lgProvinceSendMapper;
	@Override
	public int findTotal(Map<String, Object> map) {
		return lgProvinceSendMapper.findTotal(map);
	}
	@Override
	public List<LgProvincesSend> findLgProvinceSendPageList(Map<String, Object> pageMap) {
		return lgProvinceSendMapper.findLgProvinceSendPageList(pageMap);
	}
	@Override
	public List<LgProvincesSend> findLgProvincesSendByIds(Integer[] ids) {
		return lgProvinceSendMapper.findLgProvincesSendByIds(ids);
	}
	@Override
	public int delLgProvincesSendBatch(Integer[] ids) {
		return lgProvinceSendMapper.delLgProvincesSendBatch(ids);
	}
	@Override
	public int delLgProvincesSend(Integer id) {
		// TODO Auto-generated method stub
		return lgProvinceSendMapper.delLgProvincesSend(id);
	}
	@Override
	public List<LgProvincesSend> findLgProvincesSendById(Integer id) {
		// TODO Auto-generated method stub
		return lgProvinceSendMapper.findLgProvincesSendById(id);
	}
	@Override
	public int pauseLgProvincesSendBatch(Integer[] ids) {
		// TODO Auto-generated method stub
		return lgProvinceSendMapper.pauseLgProvincesSendBatch(ids);
	}
	@Override
	public int startLgProvincesSendBatch(Integer[] ids) {
		// TODO Auto-generated method stub
		return lgProvinceSendMapper.startLgProvincesSendBatch(ids);
	}
	@Override
	public int pauseLgProvincesSend(Integer id) {
		// TODO Auto-generated method stub
		return lgProvinceSendMapper.pauseLgProvincesSend(id);
	}
	@Override
	public int startLgProvincesSend(Integer id) {
		// TODO Auto-generated method stub
		return lgProvinceSendMapper.startLgProvincesSend(id);
	}

}
