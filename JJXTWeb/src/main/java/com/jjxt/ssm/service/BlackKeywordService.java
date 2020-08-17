package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackKeyword;
import com.jjxt.ssm.utils.DataSource;

public interface BlackKeywordService {
	@DataSource("master")
	public List<BlackKeyword> findAllList() throws Exception;
	@DataSource("master")
	public int addToBlackKeyword(String str) throws Exception;
	/* 按条件查询 */
	@DataSource("master")
	public List<BlackKeyword> findPageList(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int findTotal(Map<String, Object> map) throws Exception;

	@DataSource("master")
	public int delBlackKeyword(Integer id) throws Exception;

	@DataSource("master")
	public BlackKeyword findBlackKeywordById(Integer id) throws Exception;

	@DataSource("master")
	public int updateBlackKeyword(BlackKeyword blackKeyword) throws Exception;
	
	@DataSource("master")
	public int findByKeyWord(String str) throws Exception;

}
