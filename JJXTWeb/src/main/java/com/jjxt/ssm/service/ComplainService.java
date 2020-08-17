package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Complain;
import com.jjxt.ssm.utils.DataSource;

public interface ComplainService {
	
	@DataSource("slave")
	public List<Complain> findMtList(Map<String, Object> map) throws Exception;
	@DataSource("slave")
	public List<Complain> findNewMtList(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int addToPreComplain(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int addToBlackLevel1(String str) throws Exception;

	@DataSource("master")
	public int findBlackLevel1(String str) throws Exception;
	@DataSource("master")
	public int deleteComplain(Map<String, String> map) throws Exception;
	
	@DataSource("slave")
	public int findComplainTotal(Map<String, String> map) throws Exception;
	
	@DataSource("slave")
	public List<Complain> findComplainPageList(Map<String, Object> map) throws Exception;


}
