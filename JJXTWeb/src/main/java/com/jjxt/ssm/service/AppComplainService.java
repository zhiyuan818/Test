package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.AppComplain;
import com.jjxt.ssm.utils.DataSource;

public interface AppComplainService {
	@DataSource("master")
	public List<AppComplain> findAllList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<AppComplain> findListByName(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<String> findComplainNames() throws Exception;

}
