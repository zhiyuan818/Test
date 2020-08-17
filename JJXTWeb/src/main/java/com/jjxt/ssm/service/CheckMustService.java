package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.CheckMust;
import com.jjxt.ssm.utils.DataSource;

public interface CheckMustService {
	@DataSource("master")
	public int addToCheckMust(String str) throws Exception;

	/* 按条件查询 */
	@DataSource("master")
	public List<CheckMust> findPageList(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int findTotal(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int delCheckMust(Integer id) throws Exception;

	@DataSource("master")
	public CheckMust findCheckMustById(Integer id) throws Exception;

	@DataSource("master")
	public int updateCheckMust(CheckMust checkmust) throws Exception;
	
	@DataSource("master")
	public int findByKeyWord(String str) throws Exception;

}
