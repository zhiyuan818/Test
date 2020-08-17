package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Dispatch;
import com.jjxt.ssm.utils.DataSource;

public interface MoDispatchService {

	@DataSource("master")
	public List<Dispatch> findAllList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int addToDispatch(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Map<String, Object> findTwoId(String str) throws Exception;
	@DataSource("master")
	public Map<String, Object> findListById(Integer id) throws Exception;
	@DataSource("master")
	public int delDispatchById(Integer id) throws Exception;
	@DataSource("master")
	public int updateDispatch(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Dispatch findMoDispatchById(Integer id) throws Exception;
	@DataSource("master")
	public Map<String, Object> findIdsByName(String str) throws Exception;
	@DataSource("master")
	public int findMoDispatchData(String str) throws Exception;
	@DataSource("master")
	public int findTotal(Map<String, String> paramMap) throws Exception;
	@DataSource("master")
	public int deleteDispatch(Map<String, String> map)throws Exception;
}
