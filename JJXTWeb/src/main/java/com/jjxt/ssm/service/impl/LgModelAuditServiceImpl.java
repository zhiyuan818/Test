package com.jjxt.ssm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgModelAudit;
import com.jjxt.ssm.mapper.LgModelAuditMapper;
import com.jjxt.ssm.service.LgModelAuditService;

@Service("lgModelAuditService")
@Transactional
public class LgModelAuditServiceImpl implements LgModelAuditService {
	@Autowired
	private LgModelAuditMapper lgModelAuditMapper;

	@Override
	public int addLgModelAudit(LgModelAudit lgModelAudit) throws Exception {
		return lgModelAuditMapper.addLgModelAudit(lgModelAudit);
	}

	@Override
	public LgModelAudit findAddEnd() throws Exception {
		return lgModelAuditMapper.findAddEnd();
	}

	@Override
	public LgModelAudit findByContent(String content) throws Exception {
		return lgModelAuditMapper.findByContent(content);
	}

	@Override
	public List<LgModelAudit> findLgModleAuditPageList(Map<String, Object> map) throws Exception {
		return lgModelAuditMapper.findLgModleAuditPageList(map);
	}

	@Override
	public List<LgModelAudit> findLgModelAuditById(Integer id) throws Exception {
		return lgModelAuditMapper.findLgModelAuditById(id);
	}

	@Override
	public int deleteLgModelAudit(Integer id) throws Exception {
		return lgModelAuditMapper.deleteLgModelAudit(id);
	}

	@Override
	public List<LgModelAudit> findLgModelAuditByIds(Integer[] ids) throws Exception {
		return lgModelAuditMapper.findLgModelAuditByIds(ids);
	}

	@Override
	public int deleteLgModelAuditBatch(Integer[] ids) throws Exception {
		return lgModelAuditMapper.deleteLgModelAuditBatch(ids);
	}

	@Override
	public int updateLgModelAudit(LgModelAudit lgModelAudit) throws Exception {
		return lgModelAuditMapper.updateLgModelAudit(lgModelAudit);
	}

	@Override
	public Application findApplicationById(int id) throws Exception {
		return lgModelAuditMapper.findApplicationByAppId(id);
	}

	@Override
	public String findCompanyNameById(int id) throws Exception {
		return lgModelAuditMapper.findCompanyNameById(id);
	}

	@Override
	public int findTotal(Map<String, String> param) throws Exception {
		return lgModelAuditMapper.findTotal(param);
	}

	@Override
	public LgModelAudit findByContent(String content, String string) throws Exception {
		Map<String, String> map=new HashMap<String,String>();
		map.put("content", content);
		map.put("appId", string);
		return lgModelAuditMapper.findByConMap(map);
	}

}
